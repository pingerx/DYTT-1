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
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.network.Resource;
import com.bzh.dytt.data.network.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewMovieViewModel extends BaseViewModel {

    private final CategoryHandler mCategoryHandler;

    private LiveData<Resource<List<VideoDetail>>> mVideoList;

    @Inject
    NewMovieViewModel(DataRepository repository) {
        super(repository);
        mCategoryHandler = new CategoryHandler(mDataRepository);
        mVideoList = Transformations.switchMap(mCategoryHandler.getCategoryMap(), new Function<Resource<List<CategoryMap>>, LiveData<Resource<List<VideoDetail>>>>() {
            @Override
            public LiveData<Resource<List<VideoDetail>>> apply(Resource<List<CategoryMap>> input) {
                List<String> linkList = new ArrayList<>();
                if (input.data != null) {
                    for (CategoryMap categoryMap : input.data) {
                        linkList.add(categoryMap.getLink());
                        mDataRepository.getVideoDetailNew(categoryMap.getLink());
                    }
                }
                return mDataRepository.getVideoDetails(linkList);
            }
        });

        mCategoryHandler.refresh();
    }

    void refresh() {
        mCategoryHandler.refresh();
    }

    LiveData<Resource<List<VideoDetail>>> getNewMovieList() {
        return mVideoList;
    }

    static class CategoryHandler implements Observer<Resource<List<CategoryMap>>> {

        private MutableLiveData<Resource<List<CategoryMap>>> mCategoryMap = new MutableLiveData<>();

        private LiveData<Resource<List<CategoryMap>>> mLiveData;

        private DataRepository mRepository;

        CategoryHandler(DataRepository repository) {
            mRepository = repository;
        }

        @Override
        public void onChanged(@Nullable Resource<List<CategoryMap>> result) {
            if (result == null) {
                unregister();
            } else {
                mCategoryMap.setValue(result);
                if (result.status == Status.SUCCESS || result.status == Status.ERROR) {
                    unregister();
                }
            }
        }

        private void unregister() {
            if (mLiveData != null) {
                mLiveData.removeObserver(this);
                mLiveData = null;
            }
        }

        void refresh() {
            unregister();
            mLiveData = mRepository.getLatestMovie();
            mLiveData.observeForever(this);
        }


        MutableLiveData<Resource<List<CategoryMap>>> getCategoryMap() {
            return mCategoryMap;
        }
    }
}
