package com.bzh.dytt.film;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bzh.dytt.R;
import com.bzh.dytt.base.BaseFragment;

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
public class FilmMainFragment extends BaseFragment implements FilmMainIView {

    private FilmMainPresenter filmMainPresenter;

    public static FilmMainFragment newInstance() {
        Bundle args = new Bundle();
        FilmMainFragment fragment = new FilmMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.viewpager)
    ViewPager container;

    @Override
    protected int getContentView() {
        return R.layout.frag_film_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        filmMainPresenter = new FilmMainPresenter(baseActivity, this, this);
    }

    @Override
    protected void onFirstUserVisible() {
        filmMainPresenter.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {
        filmMainPresenter.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {
        filmMainPresenter.onUserInvisible();
    }

    @Override
    public void initContainer(PagerAdapter pagerAdapter) {
        container.setOffscreenPageLimit(0);
        container.setAdapter(pagerAdapter);
    }

    @Override
    public void initTabLayout() {
        tabLayout.setLayoutMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabTextColors(Color.parseColor("#b3ffffff"), Color.WHITE);
        tabLayout.setupWithViewPager(container);
    }
}
