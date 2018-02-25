package com.bzh.dytt.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.VisibleForTesting;
import android.test.mock.MockApplication;

import com.bzh.dytt.BasicApp;
import com.bzh.dytt.DataRepository;
import com.bzh.dytt.data.source.AppDatabase;
import com.bzh.dytt.home.HomePageViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static volatile ViewModelFactory sInstance;

    private final Application mApplication;

    private final DataRepository mRepository;

    public static ViewModelFactory getInstance(Application application) {

        if (sInstance == null) {
            synchronized (ViewModelFactory.class) {
                if (sInstance == null) {
                    sInstance = new ViewModelFactory(application, ((BasicApp) application).getRepository());
                }
            }
        }
        return sInstance;
    }

    @VisibleForTesting
    public static void destroyInstance() {
        sInstance = null;
    }

    private ViewModelFactory(Application application, DataRepository repository) {
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
