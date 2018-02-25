package com.bzh.dytt.di;


import android.app.Application;
import android.arch.persistence.room.Room;

import com.bzh.dytt.data.source.AppDatabase;
import com.bzh.dytt.data.source.DyttService;
import com.bzh.dytt.data.source.HomeAreaDao;
import com.bzh.dytt.data.source.HomeItemDao;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

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

    @Singleton
    @Provides
    DyttService provideDyttService(Application app){
        int cacheSize = 10 * 1024 * 1024;

        String cachePath = app.getCacheDir().getAbsolutePath() + File.separator + "cache.dytt";

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

    @Singleton
    @Provides
    AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, AppDatabase.DATABASE_NAME).build();
    }

    @Singleton
    @Provides
    HomeAreaDao provideHomeAreaDao(AppDatabase db) {
        return db.homeAreaDAO();
    }

    @Singleton
    @Provides
    HomeItemDao provideHomeItemDao(AppDatabase db) {
        return db.homeItemDao();
    }
}
