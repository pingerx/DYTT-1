package com.bzh.data.tv;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.basic.DataStoreController;
import com.bzh.data.film.DetailEntity;

import java.util.ArrayList;

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
public class TvNetWorkDataStore implements ITvDataStore {

    private ITvService iTvService;

    public TvNetWorkDataStore(ITvService iTvService) {
        this.iTvService = iTvService;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv(@IntRange(from = 1, to = 2) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iTvService.getChineseDomesticTv(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHKTTv(@IntRange(from = 1, to = 5) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iTvService.getHKTTv(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseTv(@IntRange(from = 1, to = 33) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iTvService.getChineseTv(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKoreaTV(@IntRange(from = 1, to = 45) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iTvService.getJapanSouthKoreaTV(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmericaTV(@IntRange(from = 1, to = 22) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iTvService.getEuropeAmericaTV(index));
    }

}
