package com.bzh.data.repository.datastore;

import com.bzh.data.ApplicationStub;
import com.bzh.data.ApplicationTestCase;
import com.bzh.data.net.RetrofitManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.UnsupportedEncodingException;

import rx.Observable;
import rx.Subscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

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

    private NetWorkDataStore netWorkDataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        netWorkDataStore = new NetWorkDataStore(RetrofitManager.getInstance());
    }

    @Test
    public void testGetHomePage() throws Exception {
        Observable<String> homePage = netWorkDataStore.getHomePage();
        homePage.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("NetWorkDataStoreTest.onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("NetWorkDataStoreTest.onError");
            }

            @Override
            public void onNext(String s) {
                try {
                    s = new String(s.getBytes("gbk"));
                    System.out.println("s = [" + s + "]");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Assert.assertNotNull(s);
            }
        });
    }
}