package com.bzh.data.repository;

import android.support.annotation.IntRange;

import com.bzh.data.film.datasource.FilmNetWorkDataStore;
import com.bzh.data.film.datasource.IFilmDataStore;
import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.service.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

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

    private static Repository repository;

    public static Repository getInstance() {

        if (repository == null) {
            repository = new Repository();
        }
        return repository;
    }

    private Repository() {

    }

    private FilmNetWorkDataStore filmNetWorkDataStore;

    @Override
    public Observable<ArrayList<FilmEntity>> getNewest(@IntRange(from = 1, to = 131) int index) {
        return getFilmDataStore().getNewest(index);
    }

    @Override
    public Observable<FilmDetailEntity> getFilmDetail(String filmStr) {
        return getFilmDataStore().getFilmDetail(filmStr);
    }

    public IFilmDataStore getFilmDataStore() {
        if (filmNetWorkDataStore == null) {
            filmNetWorkDataStore = new FilmNetWorkDataStore(RetrofitManager.getInstance());
        }
        return filmNetWorkDataStore;
    }
}
