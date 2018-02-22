package com.bzh.dytt.util;


import android.content.Context;
import android.support.annotation.NonNull;

import com.bzh.dytt.data.source.DyttService;
import com.bzh.dytt.data.source.HomePageRepository;
import com.bzh.dytt.data.source.MyDatabase;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Injection {

    public static HomePageRepository provideHomePageRepository(@NonNull Context context) {

        MyDatabase database = MyDatabase.getInstance(context);

        DyttService dyttService = getDyttService(context);

        return HomePageRepository.getInstance(dyttService, database.homeAreaDAO(), database.homeItemDao());
    }

    private static DyttService getDyttService(@NonNull Context context) {

        int cacheSize = 10 * 1024 * 1024;

        String cachePath = context.getCacheDir().getAbsolutePath() + File.separator + "cache.dytt";

        Cache cache = new Cache(new File(cachePath), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(INTERCEPTOR)
                .build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://www.dytt8.net")
                .client(okHttpClient)
                .build();

        return retrofit.create(DyttService.class);
    }

    private static Interceptor INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=60")
                    .removeHeader("Pragma")
                    .build();
        }
    };
}
