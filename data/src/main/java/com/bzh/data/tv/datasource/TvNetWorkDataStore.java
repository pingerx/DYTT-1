package com.bzh.data.tv.datasource;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.bzh.common.utils.SystemUtils;
import com.bzh.data.exception.TaskException;
import com.bzh.data.film.entity.BaseInfoEntity;
import com.bzh.data.tv.service.ITvService;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

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


    @NonNull
    private Observable<ArrayList<BaseInfoEntity>> getNewWorkObservable(final Observable<ResponseBody> observable) {
        return Observable.create(new Observable.OnSubscribe<ArrayList<BaseInfoEntity>>() {
            @Override
            public void call(Subscriber<? super ArrayList<BaseInfoEntity>> subscriber) {
                if (SystemUtils.getNetworkType() == SystemUtils.NETWORK_TYPE_NONE) {
                    subscriber.onError(new TaskException(TaskException.ERROR_NONE_NETWORK));
                } else {
                    try {
                        observable.map(transformCharset)
                                .map(transformEntity)
                                .subscribe(subscriber);
                    } catch (TaskException e) {
                        subscriber.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv(@IntRange(from = 1, to = 2) int index) {
        return getNewWorkObservable(iTvService.getChineseDomesticTv(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv_1(@IntRange(from = 1, to = 25) int index) {
        return getNewWorkObservable(iTvService.getChineseDomesticTv_1(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseDomesticTv_2(@IntRange(from = 1, to = 7) int index) {
        return getNewWorkObservable(iTvService.getChineseDomesticTv_2(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHKTTv(@IntRange(from = 1, to = 5) int index) {
        return getNewWorkObservable(iTvService.getHKTTv(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getChineseTv(@IntRange(from = 1, to = 33) int index) {
        return getNewWorkObservable(iTvService.getChineseTv(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getJapanSouthKoreaTV(@IntRange(from = 1, to = 45) int index) {
        return getNewWorkObservable(iTvService.getJapanSouthKoreaTV(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getEuropeAmericaTV(@IntRange(from = 1, to = 22) int index) {
        return getNewWorkObservable(iTvService.getEuropeAmericaTV(index));
    }
}
