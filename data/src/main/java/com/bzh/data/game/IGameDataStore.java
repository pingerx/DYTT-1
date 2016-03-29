package com.bzh.data.game;

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
 * <b>创建日期</b>：　16-3-29<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public interface IGameDataStore {

    Observable<ArrayList<BaseInfoEntity>> getGame(@IntRange(from = 1, to = 369) int index);

    Observable<ArrayList<BaseInfoEntity>> getHotGame(@IntRange(from = 1, to = 8) int index);

    Observable<ArrayList<BaseInfoEntity>> getClassicGame(@IntRange(from = 1, to = 199) int index);

    Observable<ArrayList<BaseInfoEntity>> getNewestGame(@IntRange(from = 1, to = 146) int index);
}
