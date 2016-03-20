package com.bzh.dytt.ui.fragment.film;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.bzh.dytt.R;
import com.bzh.dytt.ui.fragment.base.BaseFragment;

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
public class FilmMainFragment extends BaseFragment {

    private static final String TAG = "FilmMainFragment";
    
    public static FilmMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        FilmMainFragment fragment = new FilmMainFragment();
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
        Log.d(TAG, "onFirstUserVisible() called with: " + "");
        viewPagerAdapter = new MyViewPagerAdapter(baseActivity.getSupportFragmentManager());
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(viewPagerAdapter);

        tabLayout.setLayoutMode(TabLayout.MODE_FIXED);
        tabLayout.setTabTextColors(Color.parseColor("#b3ffffff"), Color.WHITE);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onUserVisible() {
        Log.d(TAG, "onUserVisible() called with: " + "");
    }

    @Override
    protected void onUserInvisible() {
        Log.d(TAG, "onUserInvisible() called with: " + "");
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return NewestFilmFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

}
