package com.bzh.dytt.variety;

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
public class VarietyMainFragment extends TabLayoutFragment implements VarietyMainIView {

    public static VarietyMainFragment newInstance() {

        Bundle args = new Bundle();

        VarietyMainFragment fragment = new VarietyMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initFragmentConfig() {
        tabLayoutPresenter = new VarietyMainPresenter(getBaseActivity(), this, this);
        tabLayoutPresenter.initFragmentConfig();
    }
}
