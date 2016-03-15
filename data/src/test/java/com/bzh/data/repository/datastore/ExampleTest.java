package com.bzh.data.repository.datastore;

import com.bzh.data.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-14<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class ExampleTest extends ApplicationTestCase {


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    List list;

    @Test
    public void test1() {
        list.add(1);
        list.clear();

        verify(list).add(1);
        verify(list).clear();
    }


}
