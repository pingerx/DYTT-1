package com.bzh.dytt;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bzh.dytt.data.HomeArea;
import com.bzh.dytt.data.HomeItem;
import com.bzh.dytt.data.source.AppDatabase;
import com.bzh.dytt.data.source.DyttService;
import com.bzh.dytt.data.source.HomeItemParseUtil;
import com.bzh.dytt.data.source.NetworkBoundResource;
import com.bzh.dytt.data.source.Resource;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class DataRepository {

    private AppExecutors appExecutors;
    private DyttService mService;
    private AppDatabase mAppDatabase;

    @Inject
    DataRepository(AppExecutors appExecutors, DyttService service, AppDatabase appDatabase) {
        this.appExecutors = appExecutors;
        mService = service;
        mAppDatabase = appDatabase;
    }

    public LiveData<Resource<List<HomeArea>>> getHomeAreas() {
        return new NetworkBoundResource<List<HomeArea>, String>() {

            @Override
            protected void saveCallResult(@NonNull String item) {

                List<HomeArea> homeAreas = HomeItemParseUtil.getInstance().parseAreas(item);

                mAppDatabase.homeAreaDAO().insertAreas(homeAreas);
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
            protected LiveData<Resource<String>> createCall() {

                final MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();
                mService.getHomePage().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            liveData.setValue(Resource.success(new String(response.body().bytes(), "GB2312")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        liveData.setValue(Resource.<String>error(t.getMessage(), null));
                    }
                });
                return liveData;
            }
        }.getAsLiveData();
    }


    public LiveData<Resource<List<HomeItem>>> getHomeItems(final int type) {
        return new NetworkBoundResource<List<HomeItem>, String>() {

            @Override
            protected void saveCallResult(@NonNull String item) {

                List<HomeItem> homeItems = HomeItemParseUtil.getInstance().parseItems(item);

                mAppDatabase.homeItemDao().insertItems(homeItems);

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
            protected LiveData<Resource<String>> createCall() {

                final MutableLiveData<Resource<String>> liveData = new MutableLiveData<>();

                mService.getHomePage().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            liveData.setValue(Resource.success(new String(response.body().bytes(), "GB2312")));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        liveData.setValue(Resource.<String>error(t.getMessage(), null));
                    }
                });
                return liveData;

            }
        }.getAsLiveData();
    }
}
