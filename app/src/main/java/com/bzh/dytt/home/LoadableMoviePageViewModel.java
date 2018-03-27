package com.bzh.dytt.home;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

import com.bzh.dytt.BaseViewModel;
import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.TypeConsts;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.network.Resource;
import com.bzh.dytt.data.network.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LoadableMoviePageViewModel extends BaseViewModel {

    private LiveData<Resource<List<VideoDetail>>> mVideoList;
    private LiveData<Resource<List<VideoDetail>>> mLoadMoreList;
    private TypeConsts.MovieCategory mCategory;
    private CategoryHandler mFirstPageHandler;
    private CategoryHandler mNextPageHandler;
    private Function<Resource<List<CategoryMap>>, LiveData<Resource<List<VideoDetail>>>> mVideoDetailFunction = new Function<Resource<List<CategoryMap>>, LiveData<Resource<List<VideoDetail>>>>() {
        @Override
        public LiveData<Resource<List<VideoDetail>>> apply(Resource<List<CategoryMap>> categoryMaps) {
            List<String> linkList = new ArrayList<>();
            if (categoryMaps != null && categoryMaps.data != null) {
                for (CategoryMap categoryMap : categoryMaps.data) {
                    linkList.add(categoryMap.getLink());
                }
            }
            return mDataRepository.getVideoDetails(linkList);
        }
    };

    @Inject
    LoadableMoviePageViewModel(DataRepository repository) {
        super(repository);
    }

    public void setCategory(TypeConsts.MovieCategory category) {
        mCategory = category;

        mFirstPageHandler = new CategoryHandler(mDataRepository, mCategory);
        mVideoList = Transformations.switchMap(mFirstPageHandler.getCategoryMap(), mVideoDetailFunction);

        mNextPageHandler = new CategoryHandler(mDataRepository, mCategory);
        mLoadMoreList = Transformations.switchMap(mNextPageHandler.getCategoryMap(), mVideoDetailFunction);

        mFirstPageHandler.refreshPage();
    }

    void refresh() {
        mFirstPageHandler.refreshPage();
    }

    void loadNextPage() {
        mNextPageHandler.nextPage();
    }

    LiveData<Resource<List<VideoDetail>>> getMovieList() {
        return mVideoList;
    }

    LiveData<Resource<List<VideoDetail>>> getLoadMoreList() {
        return mLoadMoreList;
    }

    static class CategoryHandler implements Observer<Resource<List<CategoryMap>>> {

        private MutableLiveData<Resource<List<CategoryMap>>> mCategoryMap = new MutableLiveData<>();

        private LiveData<Resource<List<CategoryMap>>> mLiveData;

        private DataRepository mRepository;

        private TypeConsts.MovieCategory mMovieCategory;

        private boolean mIsRunning;

        CategoryHandler(DataRepository repository, TypeConsts.MovieCategory movieCategory) {
            mRepository = repository;
            mMovieCategory = movieCategory;
        }

        @Override
        public void onChanged(@Nullable Resource<List<CategoryMap>> result) {
            if (result == null) {
                unregister();
            } else {
                if (result.status == Status.SUCCESS || result.status == Status.ERROR) {
                    mCategoryMap.setValue(result);
                    unregister();
                }
            }
        }

        private void unregister() {
            if (mLiveData != null) {
                mLiveData.removeObserver(this);
                mIsRunning = false;
                mLiveData = null;
            }
        }

        void refreshPage() {
            if (mIsRunning) {
                return;
            }
            unregister();
            mLiveData = mRepository.getMovieListByCategory(mMovieCategory);
            mLiveData.observeForever(this);
        }

        void nextPage() {
            if (mIsRunning) {
                return;
            }
            mIsRunning = true;
            unregister();
            mLiveData = mRepository.getNextMovieListByCategory(mMovieCategory);
            mLiveData.observeForever(this);
        }

        MutableLiveData<Resource<List<CategoryMap>>> getCategoryMap() {
            return mCategoryMap;
        }

    }
}
