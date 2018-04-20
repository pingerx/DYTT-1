package com.bzh.dytt.viewmodel;


import android.arch.core.util.Function;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.Transformations;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.bzh.dytt.BaseViewModel;
import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.ExceptionType;
import com.bzh.dytt.data.entity.CategoryMap;
import com.bzh.dytt.data.entity.MovieCategory;
import com.bzh.dytt.data.entity.VideoDetail;
import com.bzh.dytt.data.Resource;
import com.bzh.dytt.data.Status;

import java.util.List;

import javax.inject.Inject;

public class NewMovieViewModel extends BaseViewModel implements LifecycleObserver {

    private final CategoryHandler mCategoryHandler;
    private LiveData<Resource<List<VideoDetail>>> mVideoList;
    private final VideoDetailHandle mVideoDetailHandle;

    @Inject
    NewMovieViewModel(DataRepository repository) {
        super(repository);
        mVideoDetailHandle = new VideoDetailHandle(mDataRepository);
        mCategoryHandler = new CategoryHandler(mDataRepository, MovieCategory.HOME_LATEST_MOVIE);
        mVideoList = Transformations.switchMap(mCategoryHandler.getCategoryMap(), new Function<Resource<List<CategoryMap>>, LiveData<Resource<List<VideoDetail>>>>() {
            @Override
            public LiveData<Resource<List<VideoDetail>>> apply(Resource<List<CategoryMap>> result) {
                return mDataRepository.getVideoDetailsByCategory(MovieCategory.HOME_LATEST_MOVIE);
            }
        });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void active() {
        if (mVideoDetailHandle != null) {
            mVideoDetailHandle.register();
        }
        if (mCategoryHandler != null) {
            mCategoryHandler.refresh();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void inactive() {
        if (mVideoDetailHandle != null) {
            mVideoDetailHandle.unregister();
        }
        if (mCategoryHandler != null) {
            mCategoryHandler.unregister();
        }
    }

    public void refresh() {
        mCategoryHandler.refresh();
    }

    public LiveData<Resource<List<VideoDetail>>> getNewMovieList() {
        return mVideoList;
    }

    public LiveData<Resource<ExceptionType>> getFetchVideoDetailState() {
        return mDataRepository.getFetchVideoDetailState();
    }

    public MutableLiveData<VideoDetail> getVideoDetailLiveData() {
        return mVideoDetailHandle.getVideoDetail();
    }
}
