package com.bzh.dytt.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.bzh.dytt.R;
import com.bzh.dytt.presenter.MainAImpl;
import com.bzh.dytt.ui.view.IMainView;
import com.bzh.dytt.widget.XViewPager;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements IMainView {

    MainAImpl mainA;

    ActionBarDrawerToggle toggle;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.nav_view)
    NavigationView navigationView;


    @Bind(R.id.viewPager)
    XViewPager container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainA = new MainAImpl(this, this);
        mainA.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initToolbar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void initDrawerToggle() {
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void initContainer(PagerAdapter pagerAdapter, int limit) {
        container.setOffscreenPageLimit(limit);
        container.setAdapter(pagerAdapter);
    }

    @Override
    public void setCurrentItem(int item) {
        container.setCurrentItem(item, false);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void setNavigationItemSelectedListener(MainAImpl mainA) {
        navigationView.setNavigationItemSelectedListener(mainA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainA.onDestroy();
    }
}
