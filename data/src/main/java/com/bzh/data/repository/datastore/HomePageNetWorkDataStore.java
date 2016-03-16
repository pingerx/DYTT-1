package com.bzh.data.repository.datastore;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.bzh.data.entity.FilmDetailEntity;
import com.bzh.data.net.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

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
