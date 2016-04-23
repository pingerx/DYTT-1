package com.bzh.data.tv;

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
 * <b>描述</b>：　　　电视<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface ITvService {

    /**
     * 国产合拍电视剧
     */
    @GET("/html/tv/hepai/list_10_{index}.html")
    Observable<ResponseBody> getChineseDomesticTv(@Path("index") @IntRange(from = 1, to = 31) int index);

    /**
     * 港台电视剧
     */
    @GET("/html/tv/gangtai/list_3_{index}.html")
    Observable<ResponseBody> getHKTTv(@Path("index") @IntRange(from = 1, to = 5) int index);

    /**
     * 华语电视
     */
    @GET("/html/tv/hytv/list_71_{index}.html")
    Observable<ResponseBody> getChineseTv(@Path("index") @IntRange(from = 1, to = 33) int index);

    /**
     * 日韩电视
     */
    @GET("/html/tv/rihantv/list_8_{index}.html")
    Observable<ResponseBody> getJapanSouthKoreaTV(@Path("index") @IntRange(from = 1, to = 45) int index);

    /**
     * 欧美电视
     */
    @GET("/html/tv/oumeitv/list_9_{index}.html")
    Observable<ResponseBody> getEuropeAmericaTV(@Path("index") @IntRange(from = 1, to = 22) int index);

}
