package com.bzh.data.repository;

import android.support.annotation.NonNull;

import com.bzh.common.utils.SystemUtils;
import com.bzh.data.exception.TaskException;
import com.bzh.data.film.entity.BaseInfoEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
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
public abstract class IHtmlDataStore {

    @NonNull
    public Observable<ArrayList<BaseInfoEntity>> getNewWorkObservable(final Observable<ResponseBody> observable) {
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


    public Func1<String, ArrayList<BaseInfoEntity>> transformEntity = new Func1<String, ArrayList<BaseInfoEntity>>() {
        @Override
        public ArrayList<BaseInfoEntity> call(String s) {
            Document document = Jsoup.parse(s);
            Elements elements = document.select("div.co_content8").select("ul");
            Elements hrefs = elements.select("a[href]");

            Pattern pattern = Pattern.compile("^\\[.*\\]$");
            ArrayList<BaseInfoEntity> filmEntities = new ArrayList<>();
            for (Element element : hrefs) {
                String fullName = element.text();
                if (pattern.matcher(fullName).matches()) {
                    continue;
                }
                BaseInfoEntity baseInfoEntity = new BaseInfoEntity();
                baseInfoEntity.setName(fullName.substring(0, fullName.lastIndexOf("》") + 1));
                baseInfoEntity.setUrl(element.attr("href"));
                filmEntities.add(baseInfoEntity);
            }

            Elements fonts = elements.select("font");
            for (int i = 0; i < fonts.size(); i++) {
                String fullName = fonts.get(i).text();
                filmEntities.get(i).setPublishTime(fullName.substring(fullName.indexOf("：") + 1, fullName.indexOf("点击")).trim());
            }
            return filmEntities;
        }
    };


    public String TO_CHARSET_NAME = "GB2312";

    /**
     * 将html解析成字符串
     */
    public Func1<ResponseBody, String> transformCharset = new Func1<ResponseBody, String>() {
        @Override
        public String call(ResponseBody responseBody) {
            try {
                return new String(responseBody.bytes(), TO_CHARSET_NAME);
            } catch (IOException e) {
                e.printStackTrace();
                throw new TaskException(TaskException.ERROR_HTML_PARSE);
            }
        }
    };
}
