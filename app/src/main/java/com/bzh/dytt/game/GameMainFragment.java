package com.bzh.dytt.game;

import android.os.Bundle;

import com.bzh.dytt.base.tablayoutview.TabLayoutFragment;

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
public class GameMainFragment extends TabLayoutFragment implements GameMainIView {

    public static GameMainFragment newInstance() {
        GameMainFragment fragment = new GameMainFragment();
        return fragment;
    }
    
    @Override
    protected void initFragmentConfig() {
        tabLayoutPresenter = new GameMainPresenter(getBaseActivity(), this, this);
        tabLayoutPresenter.initFragmentConfig();
    }
}
