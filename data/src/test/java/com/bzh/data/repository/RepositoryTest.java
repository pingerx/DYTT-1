package com.bzh.data.repository;

import com.bzh.data.ApplicationTestCase;
import com.bzh.data.film.entity.FilmEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-18<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest extends ApplicationTestCase {

    @Mock
    Repository repository;

    @Mock
    ArrayList<FilmEntity> filmEntities;

    @Mock
    FilmEntity filmEntity;

    @Test
    public void test1() {

    }

}