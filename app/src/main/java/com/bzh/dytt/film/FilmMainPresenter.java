package com.bzh.dytt.film;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bzh.dytt.base.god.BaseActivity;
import com.bzh.dytt.base.god.BaseFragment;
import com.bzh.dytt.base.god.IFragmentPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
public class FilmMainPresenter implements IFragmentPresenter {

    public static final String NEWEST_FILM = "newest_film";
    public static final String DOMESTIC_FILM = "domestic_film";
    public static final String EUROPE_AMERICA_FILM = "europe_america_film";
    public static final String JAPAN_SOUTH_KOREA_FILM = "japan_south_korea_film";

    private final BaseActivity baseActivity;
    private final BaseFragment baseFragment;
    private final FilmMainIView filmMainIView;
    private ArrayList<StripTabItem> mItems;
    private Map<String, BaseFragment> fragments;
    private MyViewPagerAdapter myViewPagerAdapter;

    public FilmMainPresenter(BaseActivity baseActivity, BaseFragment baseFragment, FilmMainFragment filmMainIView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.filmMainIView = filmMainIView;
        mItems = generateTabs();
        fragments = new HashMap<>();
    }

    @Override
    public void initFragmentConfig() {
        myViewPagerAdapter = new MyViewPagerAdapter(baseFragment.getChildFragmentManager());
        filmMainIView.initContainer(myViewPagerAdapter);
        filmMainIView.initTabLayout();
    }

    @Override
    public void onFirstUserVisible() {

    }

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInvisible() {

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = fragments.get(makeFragmentName(position));
            if (fragment == null) {
                fragment = newFragment(mItems.get(position));
                fragments.put(makeFragmentName(position), fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mItems.get(position).getTitle();
        }
    }

    private BaseFragment newFragment(StripTabItem stripTabItem) {
        switch (stripTabItem.getType()) {
            case NEWEST_FILM:
                return NewestFilmFragment.newInstance();
            case DOMESTIC_FILM:
                return DomesticFilmFragment.newInstance();
            case EUROPE_AMERICA_FILM:
                return EAFilmFragment.newInstance();
            case JAPAN_SOUTH_KOREA_FILM:
                return JSKFilmFragment.newInstance();
        }
        return NewestFilmFragment.newInstance();
    }

    private ArrayList<StripTabItem> generateTabs() {
        ArrayList<StripTabItem> items = new ArrayList<>();
        items.add(new StripTabItem(NEWEST_FILM, "最新电影"));
        items.add(new StripTabItem(DOMESTIC_FILM, "国内电影"));
        items.add(new StripTabItem(EUROPE_AMERICA_FILM, "欧美电影"));
        items.add(new StripTabItem(JAPAN_SOUTH_KOREA_FILM, "日韩电影"));
        return items;
    }

    private String makeFragmentName(int position) {
        return mItems.get(position).getTitle();
    }

    public static class StripTabItem {

        private String type;

        private String title;

        private Serializable tag;

        public StripTabItem() {

        }

        public StripTabItem(String type, String title) {
            this.type = type;
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Serializable getTag() {
            return tag;
        }

        public void setTag(Serializable tag) {
            this.tag = tag;
        }
    }
}
