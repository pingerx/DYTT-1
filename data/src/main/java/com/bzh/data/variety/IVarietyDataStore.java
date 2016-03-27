package com.bzh.data.variety;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;

import java.util.ArrayList;

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
public interface IVarietyDataStore {

    /**
     * 最新大陆综艺
     */
    Observable<ArrayList<BaseInfoEntity>> get2013NewestChineseVariety(@IntRange(from = 1, to = 98) int index);

    /**
     * 2013大陆综艺
     */
    Observable<ArrayList<BaseInfoEntity>> get2013ChineseVariety(@IntRange(from = 1, to = 46) int index);

    /**
     * 2013港台综艺
     */
    Observable<ArrayList<BaseInfoEntity>> get2013HKTVariety(@IntRange(from = 1, to = 44) int index);

    /**
     * 2013其他综艺
     */
    Observable<ArrayList<BaseInfoEntity>> get2013OtherVariety(@IntRange(from = 1, to = 8) int index);

    /**
     * 旧版大陆综艺
     */
    Observable<ArrayList<BaseInfoEntity>> get2009ChineseVariety(@IntRange(from = 1, to = 69) int index);
}
