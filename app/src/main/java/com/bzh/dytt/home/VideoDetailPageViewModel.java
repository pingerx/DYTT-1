package com.bzh.dytt.home;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.entity.VideoDetail;
import com.bzh.dytt.data.Resource;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class VideoDetailPageViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    @Inject
    public VideoDetailPageViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        mRepository = repository;
    }

    public LiveData<Resource<List<VideoDetail>>> getVideoDetail(String detailLink) {
        return mRepository.getVideoDetails(Collections.singletonList(detailLink));
    }

}
