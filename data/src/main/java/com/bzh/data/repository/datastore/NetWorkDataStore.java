package com.bzh.data.repository.datastore;

import com.bzh.data.exception.DataLayerException;
import com.bzh.data.net.DyttService;
import com.bzh.data.net.RetrofitManager;

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

    public static final String CHARSET_NAME = "GB2312";
    private RetrofitManager retrofitManager;
    private Func1<ResponseBody, String> transformCharset = new Func1<ResponseBody, String>() {
        @Override
        public String call(ResponseBody responseBody) {
            try {
                return new String(responseBody.bytes(), CHARSET_NAME);
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
    };

    public NetWorkDataStore(RetrofitManager retrofitManager) {
        this.retrofitManager = retrofitManager;
    }

    @Override
    public Observable<String> getHomePage() {
        Observable<ResponseBody> homePage = retrofitManager.getDyttService().getHomePage();
        return homePage.map(transformCharset);
    }
}
