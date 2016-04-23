package com.bzh.data.tv;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.film.DetailEntity;

import java.util.ArrayList;

import retrofit2.http.GET;
import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-26<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface ITvDataStore {

    /**
     * 国产合拍电视剧
     */
    Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv(@IntRange(from = 1, to = 31) final int index);

    /**
     * 港台电视剧
     */
    Observable<ArrayList<BaseInfoEntity>> getHKTTv(@IntRange(from = 1, to = 5) final int index);

    /**
     * 华语电视
     */
    Observable<ArrayList<BaseInfoEntity>> getChineseTv(@IntRange(from = 1, to = 33) final int index);

    /**
     * 日韩电视
     */
    Observable<ArrayList<BaseInfoEntity>> getJapanSouthKoreaTV(@IntRange(from = 1, to = 45) final int index);

    /**
     * 欧美电视
     */
    Observable<ArrayList<BaseInfoEntity>> getEuropeAmericaTV(@IntRange(from = 1, to = 22) final int index);

}
