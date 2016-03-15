package com.bzh.data.repository.datastore;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bzh.data.ApplicationStub;
import com.bzh.data.ApplicationTestCase;
import com.bzh.data.net.RetrofitManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
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

    @Mock
    NetWorkDataStore netWorkDataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetHomePage() throws Exception {

        netWorkDataStore.getHomePage();
        verify(netWorkDataStore).getHomePage();

        //mock creation
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();


//        Observable<String> homePage = netWorkDataStore.getHomePage();
//        homePage.subscribe(new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                System.out.println("");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("e = [" + e + "]");
//            }
//
//            @Override
//            public void onNext(String res) {
//                Assert.assertNotNull(res);
//                String pattern = "[\u4e00-\u9fa5]+";
//                Pattern p = Pattern.compile(pattern);
//                Assert.assertTrue(p.matcher(res).find());
//                System.out.println(res);
//            }
//        });
    }
}