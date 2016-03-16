package com.bzh.data.net;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class RetrofitManager {

    private DyttService dyttService;

    private static RetrofitManager retrofitManager;

    private RetrofitManager(Context context) {

        int cacheSize = 10 * 1024 * 1024;

        String cachePath;
        if (null == context) {
            cachePath = "cache.dytt";
        } else {
            cachePath = context.getCacheDir().getAbsolutePath() + File.separator + "cache.dytt";
        }
        Cache cache = new Cache(new File(cachePath), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(3, TimeUnit.SECONDS)

                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ygdy8.net")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        dyttService = retrofit.create(DyttService.class);
    }

    public static RetrofitManager getInstance(Context context) {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager(context);
        }
        return retrofitManager;
    }

    public DyttService getDyttService() {
        return dyttService;
    }
}


