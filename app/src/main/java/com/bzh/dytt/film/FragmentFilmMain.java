package com.bzh.dytt.film;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

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
public class FragmentFilmMain extends BaseFragment {

    private static final String TAG = "FragmentFilmMain";

    public static FragmentFilmMain newInstance() {
        Bundle args = new Bundle();

        FragmentFilmMain fragment = new FragmentFilmMain();
        fragment.setArguments(args);
        return fragment;
    }

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.pager)
    ViewPager pager;

    private MyViewPagerAdapter viewPagerAdapter;

    @Override
    protected int getContentView() {
        return R.layout.frag_film_main;
    }

    @Override
    protected void onFirstUserVisible() {
        viewPagerAdapter = new MyViewPagerAdapter(baseActivity.getSupportFragmentManager());
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(viewPagerAdapter);

        tabLayout.setLayoutMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(Color.parseColor("#b3ffffff"), Color.WHITE);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentNewestFilm.newInstance();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
