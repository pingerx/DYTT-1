package com.bzh.data.film;

import com.bzh.data.film.IFilmService;
import com.bzh.data.service.RetrofitManager;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

import static org.junit.Assert.*;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-25<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class IFilmServiceTest {

    private IFilmService iFilmService;
    private Subscriber<ResponseBody> action1;

    @Before
    public void setUp() {
        iFilmService = RetrofitManager.getInstance().getFilmService();
        action1 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                assertNull(e);
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                assertNotNull(responseBody);
                try {
                    assertNotNull(responseBody.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Test
    public void testGetNewest() throws Exception {
        iFilmService.getNewest(1)
                .subscribe(action1);

    }

    @Test
    public void testGetDomestic() throws Exception {
        iFilmService.getDomestic(2)
                .subscribe(action1);
    }

    @Test
    public void testGetEuropeAmerica() throws Exception {
        iFilmService.getEuropeAmerica(3)
                .subscribe(action1);
    }

    @Test
    public void testGetJapanSouthKorea() throws Exception {
        iFilmService.getJapanSouthKorea(4)
                .subscribe(action1);
    }

    @Test
    public void testGetFilmDetail() throws Exception {
    }
}