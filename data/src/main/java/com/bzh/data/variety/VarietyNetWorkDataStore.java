package com.bzh.data.variety;

import android.support.annotation.IntRange;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.basic.DataStoreController;

import java.util.ArrayList;

import rx.Observable;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-27<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class VarietyNetWorkDataStore implements IVarietyDataStore {

    private IVarietyService iVarietyService;

    public VarietyNetWorkDataStore(IVarietyService iVarietyService) {
        this.iVarietyService = iVarietyService;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013NewestChineseVariety(@IntRange(from = 1, to = 98) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iVarietyService.get2013NewestChineseVariety(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013ChineseVariety(@IntRange(from = 1, to = 46) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iVarietyService.get2013ChineseVariety(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013HKTVariety(@IntRange(from = 1, to = 44) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iVarietyService.get2013HKTVariety(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2013OtherVariety(@IntRange(from = 1, to = 8) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iVarietyService.get2013OtherVariety(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> get2009ChineseVariety(@IntRange(from = 1, to = 69) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iVarietyService.get2009ChineseVariety(index));
    }
}
