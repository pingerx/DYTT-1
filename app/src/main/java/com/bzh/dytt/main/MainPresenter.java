package com.bzh.dytt.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.bzh.data.basic.MeiZiEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.FragmentContainerActivity;
import com.bzh.dytt.base.basic.IActivityPresenter;
import com.bzh.dytt.comic.ComicMainFragment;
import com.bzh.dytt.film.FilmMainFragment;
import com.bzh.dytt.game.GameMainFragment;
import com.bzh.dytt.meizi.MeiZiFragment;
import com.bzh.dytt.tv.TvMainFragment;
import com.bzh.dytt.variety.VarietyMainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
public class MainPresenter implements IActivityPresenter, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainPresenter";

    public static final String FILM = "film";
    public static final String TV = "tv";
    public static final String VARIETY = "variety";
    public static final String GAME = "game";
    public static final String COMIC = "comic";
    public static final String MEIZI = "meizi";

    private final BaseActivity baseActivity;
    private final MainIView iMainView;
    private InnerPageAdapter innerPageAdapter;
    private ArrayList<String> items;
    private Map<String, BaseFragment> fragments;

    public MainPresenter(BaseActivity baseActivity, MainIView iMainView) {
        this.baseActivity = baseActivity;
        this.iMainView = iMainView;
        fragments = new HashMap<>();
        items = new ArrayList<>();
        items.add(FILM);      // 电影
        items.add(TV);        // 电视
        items.add(VARIETY);   // 综艺
        items.add(COMIC);     // 动漫
        items.add(GAME);      // 游戏
        items.add(MEIZI);      // 妹子
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        iMainView.initToolbar("电影天堂");
        iMainView.initDrawerToggle();
        iMainView.setNavigationItemSelectedListener(this);
        innerPageAdapter = new InnerPageAdapter(baseActivity.getSupportFragmentManager());
        iMainView.initContainer(innerPageAdapter, items.size());
        iMainView.setHeadView("https://raw.githubusercontent.com/biezhihua/MyResource/master/biezhihua.png");
        loadMeiZi();
    }

    private void loadMeiZi() {
        Repository.getInstance().getMeiZi(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<MeiZiEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<MeiZiEntity> meiZiEntities) {
                        if (meiZiEntities != null && meiZiEntities.size() > 0) {
                            iMainView.setHeaderViewBackground(meiZiEntities.get(0).getUrl());
                        }
                    }
                });
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
        switch (id) {
            case R.id.nav_film: {
                iMainView.setCurrentItem(0);
                iMainView.setTitle("电影");
            }
            break;
            case R.id.nav_tv: {
                iMainView.setCurrentItem(1);
                iMainView.setTitle("电视");
            }
            break;
            case R.id.nav_variety: {
                iMainView.setCurrentItem(2);
                iMainView.setTitle("综艺");
            }
            break;
            case R.id.nav_comic: {
                iMainView.setCurrentItem(3);
                iMainView.setTitle("动漫");
            }
            break;
            case R.id.nav_game: {
                iMainView.setCurrentItem(4);
                iMainView.setTitle("游戏");
            }
            break;
            case R.id.nav_meizi: {
                iMainView.setCurrentItem(5);
                iMainView.setTitle("妹子");
            }
            break;
            case R.id.nav_setting: {

            }
            break;
        }
        iMainView.closeDrawer();
        return true;
    }

    private class InnerPageAdapter extends FragmentPagerAdapter {

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
                return FilmMainFragment.newInstance();
            case TV:
                return TvMainFragment.newInstance();
            case VARIETY:
                return VarietyMainFragment.newInstance();
            case COMIC:
                return ComicMainFragment.newInstance();
            case GAME:
                return GameMainFragment.newInstance();
            case MEIZI:
                return MeiZiFragment.newInstance();
        }
        throw new RuntimeException("没有指定类型的Fragment");
    }
}
