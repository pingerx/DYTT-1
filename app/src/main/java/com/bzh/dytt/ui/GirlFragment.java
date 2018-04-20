package com.bzh.dytt.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.BaseFragment;
import com.bzh.dytt.R;

public class GirlFragment extends BaseFragment {

    public static GirlFragment newInstance() {
        return new GirlFragment();
    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.girl_page, container, false);
    }
}
