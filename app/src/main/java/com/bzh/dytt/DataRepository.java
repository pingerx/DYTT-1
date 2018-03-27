package com.bzh.dytt;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.CategoryPage;
import com.bzh.dytt.data.TypeConsts;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.db.AppDatabase;
import com.bzh.dytt.data.db.DatabaseResource;
import com.bzh.dytt.data.network.ApiResponse;
import com.bzh.dytt.data.network.DyttService;
import com.bzh.dytt.data.network.NetworkBoundResource;
import com.bzh.dytt.data.network.Resource;
import com.bzh.dytt.task.FetchVideoDetailTask;
import com.bzh.dytt.util.HomePageParser;
import com.bzh.dytt.util.LoadableMovieParser;
import com.bzh.dytt.util.RateLimiter;
import com.bzh.dytt.util.VideoDetailPageParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Response;

@Singleton
public class DataRepository {

    private static final String TAG = "DataRepository";

    private AppExecutors mAppExecutors;

    private DyttService mService;

    private AppDatabase mAppDatabase;

    private HomePageParser mHomePageParser;

    private LoadableMovieParser mLoadableMovieParser;

    private VideoDetailPageParser mVideoDetailPageParser;

    private RateLimiter<String> mRepoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);

    @Inject
    DataRepository(AppExecutors appExecutors, DyttService service, AppDatabase appDatabase, HomePageParser
            homePageParser, VideoDetailPageParser videoDetailPageParser, LoadableMovieParser loadableMovieParser) {
        mAppExecutors = appExecutors;
        mService = service;
        mAppDatabase = appDatabase;
        mHomePageParser = homePageParser;
        mLoadableMovieParser = loadableMovieParser;
        mVideoDetailPageParser = videoDetailPageParser;
    }

    public LiveData<Resource<List<CategoryMap>>> getLatestMovie() {
        return new NetworkBoundResource<List<CategoryMap>, ResponseBody>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody responseBody) {
                try {

                    String item = new String(responseBody.bytes(), "GB2312");
                    List<CategoryMap> categoryMaps = mHomePageParser.parseLatestMovieCategoryMap(item);
                    mAppDatabase.categoryMapDAO().insertCategoryMapList(categoryMaps);

                    List<VideoDetail> details = new ArrayList<>();
                    for (int i = categoryMaps.size() - 1; i >= 0; i--) {
                        VideoDetail videoDetail = new VideoDetail();
                        CategoryMap category = categoryMaps.get(i);
                        videoDetail.setDetailLink(category.getLink());
                        details.add(videoDetail);
                    }
                    mAppDatabase.videoDetailDAO().insertVideoDetailList(details);

                    for (int i = categoryMaps.size() - 1; i >= 0; i--) {
                        CategoryMap category = categoryMaps.get(i);
                        boolean isParsed = mAppDatabase.categoryMapDAO().IsParsed(category.getLink());
                        if (!isParsed) {
                            getVideoDetailNew(category);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryMap> data) {
                return data == null || data.isEmpty() || mRepoListRateLimit.shouldFetch("LATEST_MOVIE");
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryMap>> loadFromDb() {
                return mAppDatabase.categoryMapDAO().getMovieLinksByCategory(TypeConsts.MovieCategory
                        .HOME_LATEST_MOVIE.ordinal());

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return mService.getHomePage();
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<CategoryMap>>> getMovieListByCategory(final TypeConsts.MovieCategory category) {
        return new NetworkBoundResource<List<CategoryMap>, ResponseBody>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody responseBody) {
                try {
                    String item = new String(responseBody.bytes(), "GB2312");
                    List<CategoryMap> categoryMaps = mLoadableMovieParser.getMovieList(item, category);
                    mAppDatabase.categoryMapDAO().insertCategoryMapList(categoryMaps);

                    List<VideoDetail> details = new ArrayList<>();
                    for (int i = categoryMaps.size() - 1; i >= 0; i--) {
                        VideoDetail videoDetail = new VideoDetail();
                        CategoryMap category = categoryMaps.get(i);
                        videoDetail.setDetailLink(category.getLink());
                        details.add(videoDetail);
                    }
                    mAppDatabase.videoDetailDAO().insertVideoDetailList(details);

                    for (int i = categoryMaps.size() - 1; i >= 0; i--) {
                        CategoryMap category = categoryMaps.get(i);
                        boolean isParsed = mAppDatabase.categoryMapDAO().IsParsed(category.getLink());
                        if (!isParsed) {
                            getVideoDetailNew(category);
                        }
                    }

                    mAppDatabase.categoryPageDAO().insertPage(category.getDefaultPage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryMap> data) {
                return data == null || data.isEmpty() || mRepoListRateLimit.shouldFetch("MOVIE_LIST_" + category.getTitle());
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryMap>> loadFromDb() {
                return mAppDatabase.categoryMapDAO().getMovieLinksByCategory(category.ordinal());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return mService.getMovieListByCategory(category.getDefaultUrl());
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<List<CategoryMap>>> getNextMovieListByCategory(final TypeConsts.MovieCategory category) {
        final MutableLiveData<Resource<List<CategoryMap>>> liveData = new MutableLiveData<>();
        mAppExecutors.networkIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    CategoryPage nextPage = mAppDatabase.categoryPageDAO().nextPage(category.ordinal());
                    Response<ResponseBody> response = mService.getMovieListByCategory2(category.getNextPageUrl(nextPage)).execute();
                    ApiResponse<ResponseBody> apiResponse = new ApiResponse<>(response);
                    if (apiResponse.isSuccessful()) {

                        String item = new String(apiResponse.body.bytes(), "GB2312");
                        List<CategoryMap> categoryMaps = mLoadableMovieParser.getMovieList(item, category);
                        mAppDatabase.categoryMapDAO().insertCategoryMapList(categoryMaps);

                        List<VideoDetail> details = new ArrayList<>();
                        for (int i = categoryMaps.size() - 1; i >= 0; i--) {
                            VideoDetail videoDetail = new VideoDetail();
                            CategoryMap category = categoryMaps.get(i);
                            videoDetail.setDetailLink(category.getLink());
                            details.add(videoDetail);
                        }
                        mAppDatabase.videoDetailDAO().insertVideoDetailList(details);

                        for (int i = categoryMaps.size() - 1; i >= 0; i--) {
                            CategoryMap category = categoryMaps.get(i);
                            boolean isParsed = mAppDatabase.categoryMapDAO().IsParsed(category.getLink());
                            if (!isParsed) {
                                getVideoDetailNew(category);
                            }
                        }

                        mAppDatabase.categoryPageDAO().updatePage(nextPage);

                        liveData.postValue(Resource.success(categoryMaps));
                    } else {
                        liveData.postValue(Resource.<List<CategoryMap>>error(apiResponse.errorMessage, null));
                    }
                } catch (Exception e) {
                    liveData.postValue(Resource.<List<CategoryMap>>error(e.getMessage(), null));
                }
            }
        });
        return liveData;
    }

    public LiveData<Resource<List<VideoDetail>>> getVideoDetails(final List<String> detailLinks) {
        return new DatabaseResource<List<VideoDetail>>(mAppExecutors) {
            @NonNull
            @Override
            protected LiveData<List<VideoDetail>> loadFromDb() {
                return mAppDatabase.videoDetailDAO().getVideoDetails(detailLinks.toArray(new String[]{}));

            }
        }.getAsLiveData();
    }

    public void getVideoDetailNew(CategoryMap categoryMap) {
        FetchVideoDetailTask task = new FetchVideoDetailTask(categoryMap, mAppDatabase, mService, mVideoDetailPageParser);
        mAppExecutors.networkIO().execute(task);
    }
}
