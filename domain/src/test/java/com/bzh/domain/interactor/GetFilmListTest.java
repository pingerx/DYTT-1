package com.bzh.domain.interactor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import rx.Scheduler;

import static org.junit.Assert.*;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-19<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */

@RunWith(MockitoJUnitRunner.class)
public class GetFilmListTest {

    @Mock
    Scheduler work;

    @Mock
    Scheduler post;
    private GetFilmList getFilmList;

    @Before
    public void setUp() {
        getFilmList = new GetFilmList(1, work, post);
    }

    @Test
    public void test() {
    }
}