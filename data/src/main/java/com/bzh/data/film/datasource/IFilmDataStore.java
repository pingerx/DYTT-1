package com.bzh.data.film.datasource;

import android.support.annotation.IntRange;

import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.repository.IHtmlDataStore;

import java.util.ArrayList;

import retrofit2.Call;
import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-18<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IFilmDataStore extends IHtmlDataStore {

    Observable<ArrayList<FilmEntity>> getNewest(@IntRange(from = 1, to = 131) final int index);

    Observable<ArrayList<FilmEntity>> getDomestic(@IntRange(from = 1, to = 87) final int index);

    Observable<ArrayList<FilmEntity>> getEuropeAmerica(@IntRange(from = 1, to = 147) final int index);

    Observable<ArrayList<FilmEntity>> getJapanSouthKorea(@IntRange(from = 1, to = 25) final int index);

    Observable<FilmDetailEntity> getFilmDetail(final String filmStr);
}
