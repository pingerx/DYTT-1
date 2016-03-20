package com.bzh.dytt;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarFragment;

public class MainActivity extends AppCompatActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setFragmentItems(getSupportFragmentManager(), R.id.fragmentContainer,
                new BottomBarFragment(SampleFragment.newInstance("Content for recents."), R.mipmap.ic_recents, "电影"),
                new BottomBarFragment(SampleFragment.newInstance("Content for favorites."), R.mipmap.ic_favorites, "电视"),
                new BottomBarFragment(SampleFragment.newInstance("Content for nearby stuff."), R.mipmap.ic_nearby, "综艺"),
                new BottomBarFragment(SampleFragment.newInstance("Content for friends."), R.mipmap.ic_friends, "动漫"),
                new BottomBarFragment(SampleFragment.newInstance("Content for food."), R.mipmap.ic_restaurants, "游戏")
        );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}
