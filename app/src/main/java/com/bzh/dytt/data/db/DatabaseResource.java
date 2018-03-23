package com.bzh.dytt.data.db;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.bzh.dytt.AppExecutors;
import com.bzh.dytt.data.network.ApiResponse;
import com.bzh.dytt.data.network.Resource;

public abstract class DatabaseResource<ResultType> {
    private final AppExecutors mAppExecutors;

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public DatabaseResource(AppExecutors appExecutors) {
        mAppExecutors = appExecutors;

        result.setValue(Resource.loading(null));

        final LiveData<ResultType> dbSource = loadFromDb();

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
//                result.removeSource(dbSource);
//                result.addSource(loadFromDb(), new Observer<ResultType>() {
//                    @Override
//                    public void onChanged(@Nullable ResultType newData) {
//                        Log.d("Yifan", "newData : " + newData);
//                        result.setValue(Resource.success(newData));
//                    }
//                });
                result.setValue(Resource.success(newData));

            }
        });

    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // returns a LiveData that represents the resource, implemented
    // in the base class.
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
