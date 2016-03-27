package com.bzh.data.comic;

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
 * <b>描述</b>：　　　动漫<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class ComicNetWorkData implements IComicDataStore {

    private IComicService iComicService;

    public ComicNetWorkData(IComicService iComicService) {
        this.iComicService = iComicService;
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getComic(@IntRange(from = 1, to = 45) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getComic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getNewComic(@IntRange(from = 1, to = 8) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getNewComic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getSSComic(@IntRange(from = 1, to = 5) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getSSComic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getOtherComic(@IntRange(from = 1, to = 3) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getOtherComic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getGCComic(@IntRange(from = 1, to = 2) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getGCComic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHZWComic(@IntRange(from = 1, to = 5) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getHZWComic(index));
    }

    @Override
    public Observable<ArrayList<BaseInfoEntity>> getHYComic(@IntRange(from = 1, to = 5) int index) {
        return DataStoreController.getInstance().getNewWorkObservable(iComicService.getHYComic(index));
    }
}
