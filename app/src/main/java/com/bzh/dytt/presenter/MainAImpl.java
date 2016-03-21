package com.bzh.dytt.presenter;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.bzh.dytt.R;
import com.bzh.dytt.ui.activity.BaseActivity;
import com.bzh.dytt.ui.view.IMainView;

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

    private final BaseActivity baseActivity;
    private final IMainView iMainView;

    public MainAImpl(BaseActivity baseActivity, IMainView iMainView) {
        this.baseActivity = baseActivity;
        this.iMainView = iMainView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        iMainView.initToolbar();
        iMainView.setTitle("电影天堂");
        iMainView.initDrawerToggle();
        iMainView.setNavigationItemSelectedListener(this);
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
        } else if (id == R.id.nav_gallery) {
            // 电视
        } else if (id == R.id.nav_slideshow) {
            // 综艺
        } else if (id == R.id.nav_manage) {
            // 动漫
        } else if (id == R.id.nav_share) {
            // 游戏
        } else if (id == R.id.nav_send) {
            // 设置
        }
        iMainView.closeDrawer();
        return true;
    }
}
