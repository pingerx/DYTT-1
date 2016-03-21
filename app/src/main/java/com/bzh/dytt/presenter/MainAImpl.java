package com.bzh.dytt.presenter;

import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.MenuItem;

import com.bzh.dytt.R;
import com.bzh.dytt.ui.activity.BaseActivity;
import com.bzh.dytt.ui.fragment.BaseFragment;
import com.bzh.dytt.ui.fragment.NewestFilmFragment;
import com.bzh.dytt.ui.view.IMainView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.schedulers.NewThreadScheduler;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　音悦台 版权所有(c)2016<br>
 * <b>作者</b>：　　  zhihua.bie@yinyuetai.com<br>
 * <b>创建日期</b>：　16-3-21<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class MainAImpl implements IActivityPersenter, NavigationView.OnNavigationItemSelectedListener {

    public static final String FILM = "film";
    public static final String TV = "tv";
    public static final String VARIETY = "variety";
    public static final String GAME = "game";
    public static final String COMIC = "comic";

    private final BaseActivity baseActivity;
    private final IMainView iMainView;
    private InnerPageAdapter innerPageAdapter;
    private ArrayList<String> items;
    private Map<String, BaseFragment> fragments;

    public MainAImpl(BaseActivity baseActivity, IMainView iMainView) {
        this.baseActivity = baseActivity;
        this.iMainView = iMainView;
        fragments = new HashMap<>();
        items = new ArrayList<>();
        items.add(FILM);      // 电影
        items.add(TV);        // 电视
        items.add(VARIETY);   // 综艺
        items.add(GAME);      // 游戏
        items.add(COMIC);     // 动漫
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        iMainView.initToolbar();
        iMainView.setTitle("电影天堂");
        iMainView.initDrawerToggle();
        iMainView.setNavigationItemSelectedListener(this);

        innerPageAdapter = new InnerPageAdapter(baseActivity.getSupportFragmentManager());
        iMainView.initContainer(innerPageAdapter);
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

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // 电影
            iMainView.setCurrentItem(0);
        } else if (id == R.id.nav_gallery) {
            // 电视
            iMainView.setCurrentItem(1);
        } else if (id == R.id.nav_slideshow) {
            // 综艺
            iMainView.setCurrentItem(2);
        } else if (id == R.id.nav_manage) {
            // 动漫
            iMainView.setCurrentItem(3);
        } else if (id == R.id.nav_share) {
            // 游戏
            iMainView.setCurrentItem(4);
        } else if (id == R.id.nav_send) {
            // 设置
            iMainView.setCurrentItem(5);
        }
        iMainView.closeDrawer();

        return true;
    }

    private class InnerPageAdapter extends FragmentStatePagerAdapter {

        public InnerPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = fragments.get(items.get(position));
            if (fragment == null) {
                fragment = newFragment(items.get(position));
                fragments.put(items.get(position), fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }

    private BaseFragment newFragment(String item) {
        switch (item) {
            case FILM:
                return NewestFilmFragment.newInstance();
            case TV:
                return NewestFilmFragment.newInstance();
            case VARIETY:
                return NewestFilmFragment.newInstance();
            case COMIC:
                return NewestFilmFragment.newInstance();
            case GAME:
                return NewestFilmFragment.newInstance();
        }
        throw new RuntimeException("没有指定类型的Fragment");
    }
}
