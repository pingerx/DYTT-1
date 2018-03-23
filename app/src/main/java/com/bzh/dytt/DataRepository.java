package com.bzh.dytt;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.HomeArea;
import com.bzh.dytt.data.HomeItem;
import com.bzh.dytt.data.HomeType;
import com.bzh.dytt.data.TypeConsts;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.db.DatabaseResource;
import com.bzh.dytt.data.network.ApiResponse;
import com.bzh.dytt.data.db.AppDatabase;
import com.bzh.dytt.data.network.DyttService;
import com.bzh.dytt.task.FetchVideoDetailTask;
import com.bzh.dytt.util.HomePageParser;
import com.bzh.dytt.data.network.NetworkBoundResource;
import com.bzh.dytt.data.network.Resource;
import com.bzh.dytt.util.LoadableMovieParser;
import com.bzh.dytt.util.VideoDetailPageParser;
import com.bzh.dytt.util.RateLimiter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;

@Singleton
public class DataRepository {

    private AppExecutors mAppExecutors;
    private DyttService mService;
    private AppDatabase mAppDatabase;
    private HomePageParser mHomePageParser;
    private LoadableMovieParser mLoadableMovieParser;
    private VideoDetailPageParser mVideoDetailPageParser;

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

    private RateLimiter<String> mRepoListRateLimit = new RateLimiter<>(10, TimeUnit.MINUTES);


    public LiveData<Resource<List<CategoryMap>>> getLatestMovie() {
        return new NetworkBoundResource<List<CategoryMap>, ResponseBody>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody responseBody) {
                try {
                    String item = new String(responseBody.bytes(), "GB2312");
                    List<CategoryMap> categoryMaps = mHomePageParser.parseLatestMovieCategoryMap(item);
                    for (int i = categoryMaps.size() - 1; i>=0;i--) {
                        VideoDetail videoDetail = new VideoDetail();
                        videoDetail.setDetailLink(categoryMaps.get(i).getLink());
                        mAppDatabase.videoDetailDAO().insertVideoDetail(videoDetail);
                        getVideoDetailNew(categoryMaps.get(i).getLink());
                    }
                    mAppDatabase.categoryMapDAO().insertCategoryMapList(categoryMaps);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryMap> data) {
                return true;
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
                return mService.getHomePage2("http://192.168.31.52:3000/dytt1.htm");
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<CategoryMap>>> getMovieListByCategory(TypeConsts.MovieCategory category) {
        return new NetworkBoundResource<List<CategoryMap>, ResponseBody>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody responseBody) {
                try {
                    String item = new String(responseBody.bytes(), "GB2312");
                    List<CategoryMap> categoryMaps = mLoadableMovieParser.getMovieList(item, category);
                    for (int i = categoryMaps.size() - 1; i>=0;i--) {
                        VideoDetail videoDetail = new VideoDetail();
                        videoDetail.setDetailLink(categoryMaps.get(i).getLink());
                        mAppDatabase.videoDetailDAO().insertVideoDetail(videoDetail);
                        getVideoDetailNew(categoryMaps.get(i).getLink());
                    }
                    mAppDatabase.categoryMapDAO().insertCategoryMapList(categoryMaps);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CategoryMap> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CategoryMap>> loadFromDb() {
                return mAppDatabase.categoryMapDAO().getMovieLinksByCategory(category.ordinal());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                String categoryString = "dyzz/list_23_1.html";
                switch (category) {
                    case CHINA_MOVIE:
                        categoryString = "china/list_4_1.html";
                        break;
                    case RIHAN_MOVIE:
                        categoryString = "rihan/list_6_1.html";
                        break;
                    case OUMEI_MOVIE:
                        categoryString = "oumei/list_7_1.html";
                        break;
                    case NEW_MOVIE:
                        categoryString = "dyzz/list_23_1.html";
                        break;
                    default:
                        categoryString = "dyzz/list_23_1.html";
                }

                return mService.getMovieListByCategory(categoryString);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<VideoDetail>>> getVideoDetails(List<String> detailLinks) {
        return new DatabaseResource<List<VideoDetail>>(mAppExecutors) {
            @NonNull
            @Override
            protected LiveData<List<VideoDetail>> loadFromDb() {
                return mAppDatabase.videoDetailDAO().getVideoDetails(detailLinks.toArray(new String[]{}));

            }
        }.getAsLiveData();
    }

    public void getVideoDetailNew(String link) {
        FetchVideoDetailTask task = new FetchVideoDetailTask(link, mAppDatabase, mService, mVideoDetailPageParser);
        mAppExecutors.networkIO().execute(task);
    }

}
