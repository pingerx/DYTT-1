package com.bzh.dytt;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.exception.PresenterException;

import butterknife.ButterKnife;

public class BaseFragment<T extends IPresenter> extends Fragment {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = doCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public final void onResume() {
        super.onResume();
        if (mPresenter == null) {
            throw new PresenterException("Presenter is null, did you forget to call createPresenter?");
        }
        doResume();
    }

    @Override
    public final void onPause() {
        doPause();
        super.onPause();
    }

    protected void doResume() {
        // Override this method in derived fragment
        // Do not do anything here
    }

    protected void doCreate(@Nullable Bundle savedInstanceState) {

    }

    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    protected void doPause() {
        // Override this method in derived fragment
        // Do not do anything here
    }
}
