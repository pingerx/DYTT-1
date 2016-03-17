package com.bzh.data.repository.network;

import android.support.annotation.NonNull;

import com.bzh.common.context.GlobalContext;
import com.bzh.common.utils.SystemUtils;
import com.bzh.data.ApplicationTestCase;
import com.bzh.data.entity.FilmDetailEntity;
import com.bzh.data.entity.FilmEntity;
import com.bzh.data.net.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import okio.Okio;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
@RunWith(MockitoJUnitRunner.class)
public class FilmNetWorkDataStoreTest extends ApplicationTestCase {

    FilmNetWorkDataStore realNetWorkDataStore;
    private FilmNetWorkDataStore mockNetWorkDataStore;

    @Before
    public void setUp() {
        realNetWorkDataStore = new FilmNetWorkDataStore(RetrofitManager.getInstance(null));

        mockNetWorkDataStore = new FilmNetWorkDataStore(RetrofitManager.getInstance(RuntimeEnvironment.application));
        GlobalContext.setContext((GlobalContext) RuntimeEnvironment.application);
    }

    @Test
    public void testGetNewest() throws Exception {

        Observable<FilmEntity> newest = mockNetWorkDataStore.getNewest(1);

        newest.subscribe(new Subscriber<FilmEntity>() {
            @Override
            public void onCompleted() {
                System.out.println("FilmNetWorkDataStoreTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("e = [" + e + "]");
            }

            @Override
            public void onNext(FilmEntity filmEntity) {
                System.out.println("FilmNetWorkDataStoreTest.onNext");
            }
        });

        // 真实数据
//        realNetWorkDataStore.getNewest(1).subscribe(new Subscriber<FilmEntity>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("FilmNetWorkDataStoreTest.onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("e = [" + e + "]");
//            }
//
//            @Override
//            public void onNext(FilmEntity s) {
//                assertNotNull(s);
//                System.out.println("s = [" + s + "]");
//            }
//        });
    }

//    @NonNull
//    private String getHtml(String fileName, String charsetName) throws IOException {
//        InputStream in = new FileInputStream(new File("html" + File.separator + fileName));
//        assertNotNull(in);
//        String html = Okio.buffer(Okio.source(in)).readString(Charset.forName(charsetName));
//        assertNotNull(html);
//        return html;
//    }

    @Test
    public void testGetFilmDetail() throws Exception {
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setName("2015年奇幻动作《道士下山》BD国语中字");
        filmEntity.setUrl("/html/gndy/dyzz/20160309/50431.html");
        realNetWorkDataStore.getFilmDetail(filmEntity)
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
                .flatMap(new Func1<FilmEntity, Observable<FilmDetailEntity>>() {
                    @Override
                    public Observable<FilmDetailEntity> call(FilmEntity s) {
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