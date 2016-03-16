package com.bzh.data.repository.datastore;

import android.support.annotation.IntRange;

import com.bzh.data.net.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

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
public class NetWorkDataStore implements HtmlDataStore {

    private static final String TO_CHARSET_NAME = "GB2312";
    private final RetrofitManager retrofitManager;

    /**
     * 将html解析成字符串
     */
    private final Func1<ResponseBody, String> transformCharset = new Func1<ResponseBody, String>() {
        @Override
        public String call(ResponseBody responseBody) {
            try {
                return new String(responseBody.bytes(), TO_CHARSET_NAME);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    };

    private Func1<String, Observable<Element>> hrefTags = new Func1<String, Observable<Element>>() {
        @Override
        public Observable<Element> call(String s) {
            Document document = Jsoup.parse(s);
            Elements elements = document.select("div.co_content8").select("ul");
            return Observable.from(elements.select("a[href]"));
        }
    };

    /**
     * 获取href的值
     */
    private Func1<Element, String> hrefTagValue = new Func1<Element, String>() {
        @Override
        public String call(Element element) {
            return element.attr("href");
        }
    };

    public NetWorkDataStore(RetrofitManager retrofitManager) {
        this.retrofitManager = retrofitManager;
    }

    @Override
    public Observable<String> getHomePage() {
        return retrofitManager.getDyttService().getHomePage().map(transformCharset);
    }

    @Override
    public Observable<String> getNewest(@IntRange(from = 1, to = 131) int index) {
        return retrofitManager.getDyttService()
                .getNewest(index)
                .map(transformCharset)
                .flatMap(hrefTags)
                .map(hrefTagValue);
    }

    @Override
    public Observable<String> getFilmDetail(String filmDetailUrl) {
        return retrofitManager.getDyttService().getFilmDetail(filmDetailUrl).map(transformCharset);
    }

    public Func1<ResponseBody, String> getTransformCharset() {
        return transformCharset;
    }

    public Func1<String, Observable<Element>> getHrefTags() {
        return hrefTags;
    }

    public Func1<Element, String> getHrefTagValue() {
        return hrefTagValue;
    }
}
