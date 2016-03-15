package com.bzh.data.mockito;

import com.bzh.data.ApplicationTestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * ========================================================== <br>
 * <b>版权</b>：　　　音悦台 版权所有(c) 2015 <br>
 * <b>作者</b>：　　　别志华 biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-15 <br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0 <br>
 * <b>修订历史</b>：　<br>
 * ========================================================== <br>
 */

@RunWith(MockitoJUnitRunner.class)
public class MockitoExample {

    // 1. Let's verify some behaviour!

    @Mock
    List<Integer> ints;

    @Mock
    List<String> strs;

    @Test
    public void test1() {

        //using mock object
        strs.add("one");
        strs.clear();

        //verification
        verify(strs).add("one");
        verify(strs).clear();
    }

    // How about some stubbing?

    @Mock
    LinkedList mockedList;

    @Test
    public void test2() {
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        System.out.println(mockedList.get(0));
//        System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(2));
        verify(mockedList).get(0);
    }

    // 3. Argument matchers
    @Test
    public void test3() {
        when(mockedList.get(anyInt())).thenReturn("element");

        System.out.println(mockedList.get(898));

        verify(mockedList).get(anyInt());
    }


    // 4. Verifying exact number of invocations / at least x / never
    @Test
    public void test4() {
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        verify(mockedList, never()).add("never happended");

        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("five times");
        verify(mockedList, atMost(5)).add("three times");

    }


    // 5. Stubbing void methods with exceptions
    @Test
    public void test5() {
        doThrow(new RuntimeException()).when(mockedList).clear();
        mockedList.clear();
    }

    // 6. Verification in order
    @Test
    public void test6() {
        List singleMock = mock(List.class);
        InOrder inOrder = inOrder(singleMock);


        singleMock.add("was added first");
        singleMock.add("was added second");

        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");
        ///

        // 先执行动作 在 验证动作顺序
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);
        InOrder inOrder1 = inOrder(firstMock, secondMock);

        firstMock.add("was called first");
        secondMock.add("was called second");

        inOrder1.verify(firstMock).add("was called first");
        inOrder1.verify(secondMock).add("was called second");
    }

    // 7. Making sure interaction(s) never happened on mock
    @Test
    public void test7() {
        List mockOne = mock(List.class);
        List mockTwo = mock(List.class);
        List mockThree = mock(List.class);

        mockOne.add("one");

        verify(mockOne).add("one");

        verify(mockOne, never()).add("two");

        verifyZeroInteractions(mockTwo, mockThree);
    }

    // 8. Finding redundant invocations
    @Test
    public void test8() {
        mockedList.add("one");
        mockedList.add("two");


        verify(mockedList).add("one");

        verifyNoMoreInteractions(mockedList);
    }

    // 10. Stubbing consecutive calls (iterator-style stubbing)
    @Test
    public void test10() {

    }

    // 13. Spying on real objects
    @Test
    public void test13() {
        List list = new LinkedList();
        List spy = spy(list);

        when(spy.size()).thenReturn(100);

        spy.add("one");
        spy.add("two");

        System.out.println(spy.get(0));

        System.out.println(spy.size());

        verify(spy).add("one");
        verify(spy).add("two");

        //Impossible: real method is called so spy.get(0) throws IndexOutOfBoundsException (the list is yet empty)
        when(spy.get(0)).thenReturn("foo");

        //You have to use doReturn() for stubbing
        doReturn("foo").when(spy).get(0);
    }

    // 19. Aliases for behavior driven development (Since 1.8.0)


    // 22. Verification with timeout (Since 1.8.5)
}
