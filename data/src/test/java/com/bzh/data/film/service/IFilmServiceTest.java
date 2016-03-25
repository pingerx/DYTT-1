package com.bzh.data.film.service;

import com.bzh.data.service.RetrofitManager;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.functions.Action1;

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


    private RetrofitManager retrofitManager;

    @Before
    public void setUp() {
        retrofitManager = RetrofitManager.getInstance();
    }

    @Test
    public void testGetNewest() throws Exception {
        retrofitManager.getFilmService().getNewest(1)
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        assertNotNull(responseBody);
                        try {
                            assertNotNull(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Test
    public void testGetDomestic() throws Exception {
        retrofitManager.getFilmService().getDomestic(2)
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        assertNotNull(responseBody);
                        try {
                            assertNotNull(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Test
    public void testGetEuropeAmerica() throws Exception {
        retrofitManager.getFilmService().getEuropeAmerica(3)
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        assertNotNull(responseBody);
                        try {
                            assertNotNull(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Test
    public void testGetJapanSouthKorea() throws Exception {
        retrofitManager.getFilmService().getJapanSouthKorea(4)
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        assertNotNull(responseBody);
                        try {
                            assertNotNull(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Test
    public void testGetFilmDetail() throws Exception {

    }
}