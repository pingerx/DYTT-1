package com.bzh.data.repository;

import android.support.annotation.IntRange;

import com.bzh.data.film.datasource.FilmNetWorkDataStore;
import com.bzh.data.film.datasource.IFilmDataStore;
import com.bzh.data.film.entity.BaseInfoEntity;
import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.service.IFilmService;
import com.bzh.data.service.RetrofitManager;
import com.bzh.data.tv.datasource.ITvDataStore;
import com.bzh.data.tv.datasource.TvNetWorkDataStore;
import com.bzh.data.tv.service.ITvService;

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
public class Repository implements IFilmDataStore, ITvDataStore {

    private static final String TAG = "Repository";
    private static volatile Repository repository;
    private static volatile IFilmService filmService;
    private static volatile ITvService tvService;

    public static Repository getInstance() {
        Repository tmp = repository;
        if (tmp == null) {
            synchronized (Repository.class) {
                tmp = repository;
                if (tmp == null) {
                    tmp = new Repository();
                    repository = tmp;
                    filmService = RetrofitManager.getInstance().getFilmService();
                    tvService = RetrofitManager.getInstance().getTvService();
                }
            }
        }
        return tmp;
    }

    private Repository() {

    }

    private FilmNetWorkDataStore filmNetWorkDataStore;
    private TvNetWorkDataStore tvNetWorkDataStore;

    private IFilmDataStore getFilmDataStore() {
        if (filmNetWorkDataStore == null) {
            filmNetWorkDataStore = new FilmNetWorkDataStore(filmService);
        }
        return filmNetWorkDataStore;
    }

    private ITvDataStore getTvDataStore() {
        if (tvNetWorkDataStore == null) {
            tvNetWorkDataStore = new TvNetWorkDataStore(tvService);
        }
        return tvNetWorkDataStore;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getDomestic(@IntRange(from = 1, to = 87) int index) {
        return getFilmDataStore().getDomestic(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewest(@IntRange(from = 1, to = 131) int index) {
        return getFilmDataStore().getNewest(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) int index) {
        return getFilmDataStore().getEuropeAmerica(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) int index) {
        return getFilmDataStore().getJapanSouthKorea(index);
    }

    @Override
    public Observable<FilmDetailEntity> getFilmDetail(String filmStr) {
        return getFilmDataStore().getFilmDetail(filmStr);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv(@IntRange(from = 1, to = 31) int index) {
        return getTvDataStore().getChineseDomesticTv(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv_1(@IntRange(from = 1, to = 25) int index) {
        return getTvDataStore().getChineseDomesticTv_1(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv_2(@IntRange(from = 1, to = 7) int index) {
        return getTvDataStore().getChineseDomesticTv_2(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHKTTv(@IntRange(from = 1, to = 5) int index) {
        return getTvDataStore().getHKTTv(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseTv(@IntRange(from = 1, to = 33) int index) {
        return getTvDataStore().getChineseTv(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKoreaTV(@IntRange(from = 1, to = 45) int index) {
        return getTvDataStore().getJapanSouthKoreaTV(index);
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmericaTV(@IntRange(from = 1, to = 22) int index) {
        return getTvDataStore().getEuropeAmericaTV(index);
    }
}
