package com.bzh.data.repository.network;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.home.HomePageNetWorkDataStore;
import com.bzh.data.service.RetrofitManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.regex.Pattern;

import rx.Subscriber;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
public class HomePageNetWorkDataStoreTest extends ApplicationTestCase {

    HomePageNetWorkDataStore realNetWorkDataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        realNetWorkDataStore = new HomePageNetWorkDataStore(RetrofitManager.getInstance(null));
    }

    @Test
    public void testGetHomePage() throws Exception {
        realNetWorkDataStore.getHomePage().subscribe(new Subscriber<String>() {
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
                System.out.println("res = [" + res + "]");
            }
        });
    }

}