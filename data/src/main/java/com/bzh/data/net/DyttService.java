package com.bzh.data.net;

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
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface DyttService {

    @GET("/")
    Observable<ResponseBody> getHomePage();

    // 获取最新电影
    @GET("/html/gndy/dyzz/list_23_{index}.html")
    Observable<ResponseBody> getNewest(@Path("index") @IntRange(from = 1, to = 131) int index);

    @GET("{filmDetailUrl}")
    Observable<ResponseBody> getFilmDetail(@Path("filmDetailUrl") String filmDetailUrl);
}
