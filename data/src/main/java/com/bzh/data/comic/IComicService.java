package com.bzh.data.comic;

import android.support.annotation.IntRange;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
public interface IComicService {

    /**
     * 动漫
     */
    @GET("/html/dongman/list_16_{index}.html")
    Observable<ResponseBody> getComic(@Path("index") @IntRange(from = 1, to = 45) int index);

    /**
     * 新番
     */
    @GET("/html/dongman/new/list_79_{index}.html")
    Observable<ResponseBody> getNewComic(@Path("index") @IntRange(from = 1, to = 8) int index);

    /**
     * 死神专区
     */
    @GET("/html/dongman/ss/list_67_{index}.html")
    Observable<ResponseBody> getSSComic(@Path("index") @IntRange(from = 1, to = 5) int index);

    /**
     * 其他动漫
     */
    @GET("/html/dongman/qitadongman/list_70_{index}.html")
    Observable<ResponseBody> getOtherComic(@Path("index") @IntRange(from = 1, to = 3) int index);

    /**
     * 国产动漫
     */
    @GET("/html/dongman/gcdh/list_62_{index}.html")
    Observable<ResponseBody> getGCComic(@Path("index") @IntRange(from = 1, to = 2) int index);

    /**
     * 海贼王
     */
    @GET("/html/dongman/haizeiwangqu/list_69_{index}.html")
    Observable<ResponseBody> getHZWComic(@Path("index") @IntRange(from = 1, to = 5) int index);

    /**
     * 火影
     */
    @GET("/html/dongman/hy/list_66_{index}.html")
    Observable<ResponseBody> getHYComic(@Path("index") @IntRange(from = 1, to = 5) int index);

}
