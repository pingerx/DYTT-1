package com.bzh.data.repository;

import android.content.Context;

import com.bzh.common.context.GlobalContext;
import com.bzh.data.film.IFilmService;
import com.bzh.data.comic.IComicService;
import com.bzh.data.game.IGameService;
import com.bzh.data.meizi.IMeiZiService;
import com.bzh.data.tv.ITvService;
import com.bzh.data.variety.IVarietyService;

import java.io.File;
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

    private final IComicService comicService;
    private final IFilmService filmService;
    private final IGameService gameService;
    private final ITvService tvService;
    private final IVarietyService varietyService;

    private static RetrofitManager retrofitManager;
    private final IMeiZiService iMeiZiService;

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
//                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.ygdy8.net")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();


        Retrofit meiZiRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.mzitu.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();


        comicService = retrofit.create(IComicService.class);
        filmService = retrofit.create(IFilmService.class);
        gameService = retrofit.create(IGameService.class);
        tvService = retrofit.create(ITvService.class);
        varietyService = retrofit.create(IVarietyService.class);

        iMeiZiService = meiZiRetrofit.create(IMeiZiService.class);
    }

    public static RetrofitManager getInstance() {
        RetrofitManager tmp = retrofitManager;
        if (tmp == null) {
            synchronized (RetrofitManager.class) {
                tmp = retrofitManager;
                if (tmp == null) {
                    if (GlobalContext.getInstance() != null) {
                        tmp = new RetrofitManager(GlobalContext.getInstance());
                    } else {
                        tmp = new RetrofitManager(null);
                    }
                    retrofitManager = tmp;
                }
            }
        }
        return tmp;
    }

    public IComicService getComicService() {
        return comicService;
    }

    public IFilmService getFilmService() {
        return filmService;
    }

    public IGameService getGameService() {
        return gameService;
    }

    public ITvService getTvService() {
        return tvService;
    }

    public IVarietyService getVarietyService() {
        return varietyService;
    }

    public IMeiZiService getiMeiZiService() {
        return iMeiZiService;
    }
}


