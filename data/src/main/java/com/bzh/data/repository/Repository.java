package com.bzh.data.repository;

import android.support.annotation.IntRange;

import com.bzh.data.film.datasource.FilmNetWorkDataStore;
import com.bzh.data.film.datasource.IFilmDataStore;
import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.service.RetrofitManager;

import java.util.ArrayList;

import rx.Observable;

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
public class Repository implements IFilmDataStore {

    private static final String TAG = "Repository";

    private static volatile Repository repository;

    public static Repository getInstance() {
        Repository tmp = repository;
        if (tmp == null) {
            synchronized (Repository.class) {
                tmp = repository;
                if (tmp == null) {
                    tmp = new Repository();
                    repository = tmp;
                }
            }
        }
        return tmp;
    }

    private Repository() {

    }

    private FilmNetWorkDataStore filmNetWorkDataStore;


    @Override
    public Observable<ArrayList<FilmEntity>> getDomestic(@IntRange(from = 1, to = 87) int index) {
        return getFilmDataStore()
                .getDomestic(index);
    }

    @Override
    public Observable<ArrayList<FilmEntity>> getNewest(@IntRange(from = 1, to = 131) int index) {
        return getFilmDataStore()
                .getNewest(index);
    }

    @Override
    public Observable<ArrayList<FilmEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) int index) {
        return getFilmDataStore().getEuropeAmerica(index);
    }

    @Override
    public Observable<ArrayList<FilmEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) int index) {
        return getFilmDataStore().getJapanSouthKorea(index);
    }

    @Override
    public Observable<FilmDetailEntity> getFilmDetail(String filmStr) {
        return getFilmDataStore()
                .getFilmDetail(filmStr);
    }

    private IFilmDataStore getFilmDataStore() {
        if (filmNetWorkDataStore == null) {
            filmNetWorkDataStore = new FilmNetWorkDataStore(RetrofitManager.getInstance());
        }
        return filmNetWorkDataStore;
    }
}
