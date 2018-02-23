package com.bzh.dytt.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.BaseFragment;
import com.bzh.dytt.R;

public class HomeChildrenFragment extends BaseFragment {

    public static HomeChildrenFragment newInstance() {
        Bundle args = new Bundle();

        HomeChildrenFragment fragment = new HomeChildrenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_children, container, false);
    }
}
