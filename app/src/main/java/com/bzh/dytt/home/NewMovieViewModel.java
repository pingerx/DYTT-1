package com.bzh.dytt.home;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.bzh.dytt.BaseViewModel;
import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.CategoryMap;
import com.bzh.dytt.data.VideoDetail;
import com.bzh.dytt.data.network.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class NewMovieViewModel extends BaseViewModel {

    private LiveData<Resource<List<VideoDetail>>> videoList;

    @Inject
    NewMovieViewModel(DataRepository repository) {
        super(repository);
        createVideoList();
    }

    LiveData<Resource<List<VideoDetail>>> getNewMovieList(boolean refresh) {
        if (refresh) {
            createVideoList();
        }
        return videoList;
    }

    LiveData<Resource<List<VideoDetail>>> getNewMovieList() {
        return getNewMovieList(false);
    }

    private void createVideoList() {
        videoList = Transformations.switchMap(mDataRepository.getLatestMovie(), categoryMaps -> {
            List<String> linkList = new ArrayList<>();
            if (categoryMaps != null && categoryMaps.data != null) {
                for (CategoryMap categoryMap : categoryMaps.data) {
                    linkList.add(categoryMap.getLink());
                }
            }
            return mDataRepository.getVideoDetails(linkList);
        });
    }

}
