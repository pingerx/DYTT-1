package com.bzh.dytt.ui.activity;

import android.os.Bundle;

import com.bzh.dytt.R;
import com.bzh.dytt.presenter.impl.MainAImpl;
import com.bzh.dytt.ui.activity.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private MainAImpl mainA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainA = new MainAImpl(this);
        mainA.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainA.onSaveInstanceState(outState);
    }
}
