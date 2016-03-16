package com.bzh.data.mockito;

import com.bzh.data.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

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

    // 验证行为
    @Mock
    List list;

    @Test
    public void test1() {
        list.add(1);
        list.clear();

        verify(list).add(1);
        verify(list).clear();
    }


    ///////////////////////////////////////////////////////////////////////////
    // 模拟我们所期望的结果
    ///////////////////////////////////////////////////////////////////////////
    @Mock
    Iterator iterator;

    @Test
    public void test2() {
        when(iterator.next()).thenReturn("hello").thenReturn("world");
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();
        assertEquals("hello world world", result);
    }

    @Mock
    OutputStream outputStream;

    @Test
    public void test3() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        doThrow(new IOException()).when(outputStream).close();
        outputStreamWriter.close();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 参数匹配
    ///////////////////////////////////////////////////////////////////////////
    @Mock
    Comparable comparable;

    @Test
    public void test4() {
        when(comparable.compareTo("Test")).thenReturn(1);
        when(comparable.compareTo("OMG")).thenReturn(2);

        assertEquals(1, comparable.compareTo("Test"));
        assertEquals(2, comparable.compareTo("OMG"));

        assertEquals(0, comparable.compareTo("Not Stub"));
    }


    @Test
    public void test5() {
        when(list.get(anyInt())).thenReturn(1);

        assertEquals(1, list.get(1));
        assertEquals(1, list.get(999));
        assertTrue(list.contains(1));
        assertTrue(!list.contains(3));
    }


    ///////////////////////////////////////////////////////////////////////////
    // 验证确切的调用次数
    ///////////////////////////////////////////////////////////////////////////

    @Test
    public void test6() {
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);

        verify(list).add(1);
        verify(list, times(1)).add(1);
        verify(list, times(2)).add(2);
        verify(list, times(3)).add(3);
        verify(list, never()).add(4);
        verify(list, atLeastOnce()).add(1);
        verify(list, atLeast(2)).add(2);
        verify(list, atMost(3)).add(3);
    }


    @Test(expected = RuntimeException.class)
    public void test7() {
        doThrow(new RuntimeException()).when(list).add(1);
        list.add(1);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 验证执行顺序
    ///////////////////////////////////////////////////////////////////////////

    @Mock
    List<Integer> ints;
    @Mock
    List<String> strs;

    @Test
    public void test8() {
        ints.add(1);
        strs.add("bie");
        ints.add(2);
        strs.add("zhi");
        InOrder inOrder = inOrder(ints, strs);
        inOrder.verify(ints).add(1);
        inOrder.verify(strs).add("bie");
        inOrder.verify(ints).add(2);
        inOrder.verify(strs).add("zhi");

    }

    ///////////////////////////////////////////////////////////////////////////
    // 确保模拟的对象上无互动发生
    ///////////////////////////////////////////////////////////////////////////
    @Mock
    List list1;

    @Mock
    List list2;

    @Mock
    List list3;

    @Test
    public void test9() {
        list.add(1);
        verify(list).add(1);
        verify(list, never()).add(2);

        verifyZeroInteractions(list2, list3);
    }


    @Test(expected = NoInteractionsWanted.class)
    public void test10() {
        list.add(1);
        list.add(2);
        verify(list).add(anyInt());
        verifyNoMoreInteractions(list);

        list2.add(1);
        list2.add(2);
        verify(list2).add(1);
        verifyNoMoreInteractions(list2);
    }


    ///////////////////////////////////////////////////////////////////////////
    // 连续调用
    ///////////////////////////////////////////////////////////////////////////

    @Test(expected = RuntimeException.class)
    public void test11() {
        when(list.get(0)).thenReturn(0);
        when(list.get(0)).thenReturn(1);
        when(list.get(0)).thenReturn(2);

        when(list.get(1)).thenReturn(0).thenReturn(1).thenThrow(new RuntimeException());

        assertEquals(2, list.get(0));
        assertEquals(2, list.get(0));
        assertEquals(0, list.get(1));
        assertEquals(1, list.get(1));
        list.get(1);
    }

    @Test
    public void test12() {
        when(list.get(anyInt())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return "hello world:" + invocation.getArguments()[0];
            }
        });

        assertEquals("hello world:0", list.get(0));
        assertEquals("hello world:999", list.get(999));
    }
}

