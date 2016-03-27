package com.bzh.data.comic;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-13<br>
 * <b>描述</b>：　　　动漫 <br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IComicDataStore {

    /**
     * 动漫
     */
    Observable<ArrayList<BaseInfoEntity>> getComic(@IntRange(from = 1, to = 45) int index);

    /**
     * 新番
     */
    Observable<ArrayList<BaseInfoEntity>> getNewComic( @IntRange(from = 1, to = 8) int index);

    /**
     * 死神专区
     */
    Observable<ArrayList<BaseInfoEntity>> getSSComic( @IntRange(from = 1, to = 5) int index);

    /**
     * 其他动漫
     */
    Observable<ArrayList<BaseInfoEntity>> getOtherComic( @IntRange(from = 1, to = 3) int index);

    /**
     * 国产动漫
     */
    Observable<ArrayList<BaseInfoEntity>> getGCComic( @IntRange(from = 1, to = 2) int index);

    /**
     * 海贼王
     */
    Observable<ArrayList<BaseInfoEntity>> getHZWComic( @IntRange(from = 1, to = 5) int index);

    /**
     * 海贼王
     */
    Observable<ArrayList<BaseInfoEntity>> getHYComic( @IntRange(from = 1, to = 5) int index);

}
