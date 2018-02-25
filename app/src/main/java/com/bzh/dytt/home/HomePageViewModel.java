package com.bzh.dytt.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.HomeArea;
import com.bzh.dytt.data.HomeItem;
import com.bzh.dytt.data.source.Resource;

import java.util.List;

import javax.inject.Inject;

public class HomePageViewModel extends AndroidViewModel {

    private final DataRepository mRepository;

    @Inject
    public HomePageViewModel(@NonNull Application application, DataRepository repository) {
        super(application);
        mRepository = repository;
    }

    public LiveData<Resource<List<HomeArea>>> getHomeArea() {
        return mRepository.getHomeAreas();
    }

    public LiveData<Resource<List<HomeItem>>> getHomeItems(int type) {
        return mRepository.getHomeItems(type);
    }
}
