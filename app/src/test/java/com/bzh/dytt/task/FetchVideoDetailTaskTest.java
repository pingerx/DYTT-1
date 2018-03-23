package com.bzh.dytt.task;


import com.bzh.dytt.data.network.DyttService;
import com.bzh.dytt.task.FetchVideoDetailTask;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class FetchVideoDetailTaskTest {


    private DyttService mService;
    private FetchVideoDetailTask mFetchVideoDetailTask;

    @Before
    public void init(){
//        mService = mock(DyttService.class);
//        mFetchVideoDetailTask = new FetchVideoDetailTask("/html/gndy/dyzz/20180308/56462.html", mService);
    }

    @Test
    public void getVideoDetail() {
        mFetchVideoDetailTask.run();
    }
}
