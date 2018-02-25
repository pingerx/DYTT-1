package com.bzh.dytt.util;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.bzh.dytt.DataRepository;
import com.bzh.dytt.home.HomePageViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Application mApplication;

    private final DataRepository mRepository;

    @Inject
    public ViewModelFactory(Application application, DataRepository repository) {
        mApplication = application;
        mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomePageViewModel.class)) {
            //noinspection unchecked
            return (T) new HomePageViewModel(mApplication, mRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
