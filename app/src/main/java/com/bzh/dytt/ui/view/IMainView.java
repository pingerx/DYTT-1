package com.bzh.dytt.ui.view;

import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.bzh.dytt.presenter.MainAImpl;

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
public interface IMainView {


    void initToolbar();

    void setTitle(String title);

    void initDrawerToggle();

    void initContainer(PagerAdapter pagerAdapter);

    void setCurrentItem(int item);

    void closeDrawer();

    void setNavigationItemSelectedListener(MainAImpl mainA);
}
