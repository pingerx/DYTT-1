package com.bzh.dytt;


import android.app.Application;

import com.bzh.dytt.data.source.AppDatabase;
import com.bzh.dytt.data.source.DyttService;
import com.bzh.dytt.util.NetworkServices;

/**
 * Android Application class.
 * Used for accessing singletons.
 */
public class BasicApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
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
}
