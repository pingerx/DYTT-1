package com.bzh.dytt;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.bzh.dytt.exception.PresenterException;

public class BaseFragment<T extends IPresenter> extends Fragment {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doCreate(savedInstanceState);
    }

    protected void doCreate(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public final void onResume() {
        super.onResume();
        if(mPresenter == null) {
            throw new PresenterException("Presenter is null, did you forget to call createPresenter?");
        }
        doResume();
    }

    protected void doResume() {
        // Override this method in derived fragment
        // Do not do anything here
    }

    @Override
    public final void onPause() {
        doPause();
        super.onPause();
    }

    protected void doPause() {
        // Override this method in derived fragment
        // Do not do anything here
    }

}
