package com.bzh.dytt.film;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bzh.dytt.R;
import com.bzh.dytt.base.BaseFragment;
import com.bzh.recycler.ExRecyclerView;

import butterknife.Bind;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class NewestFilmFragment extends BaseFragment implements NewestFilmIView {

    private static final String TAG = "NewestFilmFragment";

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    ExRecyclerView recyclerView;

    @Bind(R.id.exception)
    LinearLayout exception;

    private NewestFilmPresenter newestFilmF;

    public static NewestFilmFragment newInstance() {

        Bundle args = new Bundle();
        NewestFilmFragment fragment = new NewestFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newestFilmF = new NewestFilmPresenter(baseActivity, this, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_newestfilm;
    }

    @Override
    protected void onFirstUserVisible() {
        Log.d(TAG, "onFirstUserVisible() called with: " + "");
        newestFilmF.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {
        Log.d(TAG, "onUserVisible() called with: " + "");
        newestFilmF.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {
        newestFilmF.onUserInvisible();
    }

    @Override
    public void showException() {
        exception.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void initRecyclerView(LinearLayoutManager linearLayoutManager, RecyclerView.Adapter adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showSwipeRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public ExRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}
