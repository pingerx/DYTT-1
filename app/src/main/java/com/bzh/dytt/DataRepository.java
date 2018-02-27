package com.bzh.dytt;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bzh.dytt.data.HomeArea;
import com.bzh.dytt.data.HomeItem;
import com.bzh.dytt.data.source.ApiResponse;
import com.bzh.dytt.data.source.AppDatabase;
import com.bzh.dytt.data.source.DyttService;
import com.bzh.dytt.data.source.NetworkBoundResource;
import com.bzh.dytt.data.source.Resource;
import com.bzh.dytt.util.HomeParse;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;

@Singleton
public class DataRepository {

    private AppExecutors mAppExecutors;
    private DyttService mService;
    private AppDatabase mAppDatabase;
    private HomeParse mHomeParse;

    @Inject
    DataRepository(AppExecutors appExecutors, DyttService service, AppDatabase appDatabase, HomeParse homeParse) {
        mAppExecutors = appExecutors;
        mService = service;
        mAppDatabase = appDatabase;
        mHomeParse = homeParse;
    }

    public LiveData<Resource<List<HomeArea>>> getHomeAreas() {
        return new NetworkBoundResource<List<HomeArea>, ResponseBody>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody responseBody) {
                try {
                    String item = new String(responseBody.bytes(), "GB2312");
                    List<HomeArea> homeAreas = mHomeParse.parseAreas(item);
                    mAppDatabase.homeAreaDAO().insertAreas(homeAreas);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<HomeArea> data) {
                if (data == null || data.size() == 0) {
                    return true;
                }
                boolean isFresh = System.currentTimeMillis() - data.get(data.size() - 1).getLastUpdateTime() < 30 * 12 * 60 * 60 * 1000;
                return !isFresh;
            }

            @NonNull
            @Override
            protected LiveData<List<HomeArea>> loadFromDb() {
                return mAppDatabase.homeAreaDAO().getAreas();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return mService.getHomePage();
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<List<HomeItem>>> getHomeItems(final int type) {
        return new NetworkBoundResource<List<HomeItem>, ResponseBody>(mAppExecutors) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody responseBody) {
                try {
                    String item = new String(responseBody.bytes(), "GB2312");
                    List<HomeItem> homeItems = mHomeParse.parseItems(item);
                    mAppDatabase.homeItemDao().insertItems(homeItems);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<HomeItem> data) {
                if (data == null || data.size() == 0) {
                    return true;
                }
                boolean isFresh = System.currentTimeMillis() - data.get(data.size() - 1).getLastUpdateTime() < 60 * 1000;
                return !isFresh;
            }

            @NonNull
            @Override
            protected LiveData<List<HomeItem>> loadFromDb() {
                return mAppDatabase.homeItemDao().getItemsByType(type);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return mService.getHomePage();

            }
        }.getAsLiveData();
    }
}
