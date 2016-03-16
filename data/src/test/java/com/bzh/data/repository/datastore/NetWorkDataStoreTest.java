package com.bzh.data.repository.datastore;

import android.support.annotation.NonNull;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.entity.FilmDetailEntity;
import com.bzh.data.net.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import okio.Okio;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class NetWorkDataStoreTest extends ApplicationTestCase {

    @Mock
    NetWorkDataStore netWorkDataStore;

    NetWorkDataStore realNetWorkDataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        realNetWorkDataStore = new NetWorkDataStore(RetrofitManager.getInstance(null));
    }

    @Test
    public void testGetHomePage() throws Exception {

        // 验证行为
        netWorkDataStore.getHomePage();
        verify(netWorkDataStore).getHomePage();

        when(netWorkDataStore.getHomePage()).thenReturn(Observable.just("<title>电影天堂_免费电影_迅雷电影下载</title>"));
        netWorkDataStore.getHomePage().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String res) {
                String pattern = "[\u4e00-\u9fa5]+";
                Pattern p = Pattern.compile(pattern);

                assertNotNull(res);
                assertTrue(p.matcher(res).find());
                assertEquals("<title>电影天堂_免费电影_迅雷电影下载</title>", res);
            }
        });
    }

    @Test
    public void testGetNewest() throws Exception {
        // 模拟数据
        when(netWorkDataStore.getHrefTags()).thenReturn(new Func1<String, Observable<Element>>() {
            @Override
            public Observable<Element> call(String s) {
                Document document = Jsoup.parse(s);
                Elements elements = document.select("div.co_content8").select("ul");
                return Observable.from(elements.select("a[href]"));
            }
        });
        when(netWorkDataStore.getHrefTagValue()).thenReturn(new Func1<Element, String>() {
            @Override
            public String call(Element element) {
                return element.attr("href");
            }
        });

        Observable<String> htmlSource = Observable.just(getHtml("newest_1.html", "GB2312"))
                .flatMap(netWorkDataStore.getHrefTags())
                .map(netWorkDataStore.getHrefTagValue());

        when(netWorkDataStore.getNewest(1)).thenReturn(htmlSource);
        netWorkDataStore.getNewest(1)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        assertNotNull(s);
                        assertTrue(s.endsWith("html"));
                        assertTrue(s.contains("html"));
                    }
                });

        // 真实数据
        realNetWorkDataStore.getNewest(1).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                assertNotNull(s);
                assertTrue(s.endsWith("html"));
                assertTrue(s.contains("html"));
            }
        });
    }

    @NonNull
    private String getHtml(String fileName, String charsetName) throws IOException {
        InputStream in = new FileInputStream(new File("html" + File.separator + fileName));
        assertNotNull(in);
        String html = Okio.buffer(Okio.source(in)).readString(Charset.forName(charsetName));
        assertNotNull(html);
        return html;
    }

    @Test
    public void testGetFilmDetail() throws Exception {
        realNetWorkDataStore.getFilmDetail("/html/gndy/dyzz/20160309/50431.html")
                .subscribe(new Subscriber<FilmDetailEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FilmDetailEntity s) {
                        assertNotNull(s);
                        System.out.println("FilmDetailEntity = [" + s.toString() + "]");
                    }
                });
    }

    @Test
    public void testGetFilmDetailList() {
        final long start = System.currentTimeMillis();
        realNetWorkDataStore.getNewest(1)
                .flatMap(new Func1<String, Observable<FilmDetailEntity>>() {
                    @Override
                    public Observable<FilmDetailEntity> call(String s) {
                        return realNetWorkDataStore.getFilmDetail(s);
                    }
                })
                .subscribe(new Subscriber<FilmDetailEntity>() {
                    @Override
                    public void onCompleted() {
                        long end = System.currentTimeMillis();
                        System.out.println("total time = [" + (end - start) / 1000 + "]");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FilmDetailEntity filmDetailEntity) {
                        assertNotNull(filmDetailEntity);
                        assertNotNull(filmDetailEntity.getTitle());
                        System.out.println("filmDetailEntity = [" + filmDetailEntity.getTitle() + "]");
                    }
                });
    }

}