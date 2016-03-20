package com.bzh.dytt.ui.fragment.film;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.bzh.dytt.R;
import com.bzh.dytt.presenter.impl.film.NewestFilmFImpl;
import com.bzh.dytt.ui.fragment.base.BaseFragment;
import com.bzh.dytt.ui.view.film.INewestFilmView;

import butterknife.Bind;
import butterknife.BindInt;

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
public class NewestFilmFragment extends BaseFragment implements INewestFilmView {

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.exception)
    LinearLayout exception;
    private NewestFilmFImpl newestFilmF;

    public static NewestFilmFragment newInstance() {

        Bundle args = new Bundle();
        NewestFilmFragment fragment = new NewestFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newestFilmF = new NewestFilmFImpl(baseActivity, this, this);
    }

    @Override
    protected int getContentView() {
        return R.layout.frag_newestfilm;
    }

    @Override
    protected void onFirstUserVisible() {
        newestFilmF.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {
        newestFilmF.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {
        newestFilmF.onUserInvisible();
    }
}
