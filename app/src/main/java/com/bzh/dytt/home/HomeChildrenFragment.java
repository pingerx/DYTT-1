package com.bzh.dytt.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.dytt.BaseFragment;
import com.bzh.dytt.R;
import com.bzh.dytt.data.HomeArea;

import butterknife.BindView;

public class HomeChildrenFragment extends BaseFragment {

    private static final String TAG = "HomeChildrenFragment";

    public static HomeChildrenFragment newInstance(HomeArea homeArea) {
        Bundle args = new Bundle();
        args.putParcelable("HOME_AREA", homeArea);
        HomeChildrenFragment fragment = new HomeChildrenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.home_child_swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.home_child_recycler_view)
    RecyclerView mRecyclerView;

    private HomeArea mHomeArea;

    @Override
    protected void doCreate(@Nullable Bundle savedInstanceState) {
        super.doCreate(savedInstanceState);
        Bundle arguments = getArguments();
        assert arguments != null;
        mHomeArea = arguments.getParcelable("HOME_AREA");
        assert mHomeArea != null;
        Log.d(TAG, "doCreate() [" + mHomeArea.getTitle() + "]");
    }

    @Override
    protected View doCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_children, container, false);
    }

    @Override
    protected void doViewCreate(View view, Bundle savedInstanceState) {
        super.doViewCreate(view, savedInstanceState);
    }
}
