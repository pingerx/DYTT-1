package com.bzh.data.game;

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
 * <b>描述</b>：　　　游戏<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IGameService {

    @GET("/html/game/list_19_{index}.html")
    Observable<ResponseBody> getGame(@Path("index") @IntRange(from = 1, to = 369) int index);

    @GET("/html/game/remenyouxi/list_98_{index}.html")
    Observable<ResponseBody> getHotGame(@Path("index") @IntRange(from = 1, to = 8) int index);

    @GET("/html/game/jingdianyouxifabu/list_87_{index}.html")
    Observable<ResponseBody> getClassicGame(@Path("index") @IntRange(from = 1, to = 199) int index);

    @GET("/html/game/zuixinyouxifabu/list_86_{index}.html")
    Observable<ResponseBody> getNewestGame(@Path("index") @IntRange(from = 1, to = 146) int index);
}
