package com.bzh.domain.repository;

import android.support.annotation.IntRange;

import com.bzh.data.film.entity.FilmDetailEntity;
import com.bzh.data.film.entity.FilmEntity;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-19<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IRepository {
    Observable<ArrayList<FilmEntity>> getNewest(@IntRange(from = 1, to = 131) final int index);

    Observable<FilmDetailEntity> getFilmDetail(final String filmStr);
}
