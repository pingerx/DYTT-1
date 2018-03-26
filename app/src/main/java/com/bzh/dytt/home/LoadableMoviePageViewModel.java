package com.bzh.dytt.home;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.bzh.dytt.BaseViewModel;
import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.TypeConsts;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.network.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LoadableMoviePageViewModel extends BaseViewModel {

    private LiveData<Resource<List<VideoDetail>>> videoList;
    private TypeConsts.MovieCategory mCategory;

    @Inject
    LoadableMoviePageViewModel(DataRepository repository) {
        super(repository);
    }

    public void setCategory(TypeConsts.MovieCategory category) {
        mCategory = category;
        createMovieList();
    }

    LiveData<Resource<List<VideoDetail>>> getMovieList() {
        return getMovieList(false);
    }

    LiveData<Resource<List<VideoDetail>>> getMovieList(boolean refresh) {
        if (refresh) {
            createMovieList();
        }
        return videoList;
    }

    private void createMovieList() {
        videoList = Transformations.switchMap(mDataRepository.getMovieListByCategory(mCategory), new Function<Resource<List<CategoryMap>>, LiveData<Resource<List<VideoDetail>>>>() {
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
        });
    }

}
