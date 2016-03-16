package com.bzh.data.repository.network;

import com.bzh.data.net.RetrofitManager;

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
public class HomePageNetWorkDataStore implements HtmlDataStore {

    private final RetrofitManager retrofitManager;

    public HomePageNetWorkDataStore(RetrofitManager retrofitManager) {
        this.retrofitManager = retrofitManager;
    }

    public Observable<String> getHomePage() {
        return retrofitManager.getHomePageService().getHomePage().map(transformCharset);
    }
}
