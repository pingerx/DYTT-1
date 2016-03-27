package com.bzh.data.variety;

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
 * <b>描述</b>：　　　综艺节目<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IVarietyService {

    // 最新大陆综艺
    @GET("/html/zongyi2013/list_99_{index}.html")
    Observable<ResponseBody> get2013NewestChineseVariety(@Path("index") @IntRange(from = 1, to = 98) int index);

    /**
     * 2013大陆综艺
     */
    @GET("/html/zongyi2013/daluzongyi/list_101_{index}.html")
    Observable<ResponseBody> get2013ChineseVariety(@Path("index") @IntRange(from = 1, to = 46) int index);

    /**
     * 2013港台综艺
     */
    @GET("/html/zongyi2013/taiwanzongyi/list_100_{index}.html")
    Observable<ResponseBody> get2013HKTVariety(@Path("index") @IntRange(from = 1, to = 44) int index);

    /**
     * 2013其他综艺
     */
    @GET("/html/zongyi2013/qitazongyi/list_103_{index}.html")
    Observable<ResponseBody> get2013OtherVariety(@Path("index") @IntRange(from = 1, to = 8) int index);


    // 旧版大陆综艺
    @GET("/html/2009zongyi/list_89_{index}.html")
    Observable<ResponseBody> get2009ChineseVariety(@Path("index") @IntRange(from = 1, to = 69) int index);
}
