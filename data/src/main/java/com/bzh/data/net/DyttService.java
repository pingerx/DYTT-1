package com.bzh.data.net;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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
//    @Headers({"Content-Type: text/html; charset=gb2312"
//            , "Content-Language: en,zh", "Content-Encoding: gzip"})
    Observable<String> getHomePage();
}
