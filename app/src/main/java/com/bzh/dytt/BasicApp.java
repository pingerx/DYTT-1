package com.bzh.dytt;


import android.app.Activity;
import android.app.Application;

import com.bzh.dytt.data.source.AppDatabase;
import com.bzh.dytt.data.source.DyttService;
import com.bzh.dytt.di.AppInjector;
import com.bzh.dytt.util.NetworkServices;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class BasicApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        AppInjector.init(this);
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this);
    }

    public DyttService getDyttService() {
        return NetworkServices.getInstance(this);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDyttService(), getDatabase());
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
