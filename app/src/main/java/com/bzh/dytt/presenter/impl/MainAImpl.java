package com.bzh.dytt.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bzh.dytt.R;
import com.bzh.dytt.presenter.IActivityPersenter;
import com.bzh.dytt.ui.activity.MainActivity;
import com.bzh.dytt.ui.activity.base.BaseActivity;
import com.bzh.dytt.ui.fragment.SampleFragment;
import com.bzh.dytt.ui.fragment.film.FilmMainFragment;
import com.bzh.dytt.ui.fragment.film.NewestFilmFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;

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
public class MainAImpl implements IActivityPersenter {

    private BottomBar mBottomBar;

    private BaseActivity baseActivity;

    public MainAImpl(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach(baseActivity, savedInstanceState);
        mBottomBar.setFragmentItems(baseActivity.getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(FilmMainFragment.newInstance(), R.mipmap.ic_recents, "电影"),
                new BottomBarFragment(SampleFragment.newInstance("Content for favorites."), R.mipmap.ic_favorites, "电视"),
                new BottomBarFragment(SampleFragment.newInstance("Content for nearby stuff."), R.mipmap.ic_nearby, "综艺"),
                new BottomBarFragment(SampleFragment.newInstance("Content for friends."), R.mipmap.ic_friends, "动漫"),
                new BottomBarFragment(SampleFragment.newInstance("Content for food."), R.mipmap.ic_restaurants, "游戏")
        );
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mBottomBar.onSaveInstanceState(outState);
    }
}
