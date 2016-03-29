package com.bzh.dytt.game;

import android.os.Bundle;

import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;
import com.bzh.dytt.variety.BaseVarietyInfoIView;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　新版其他<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class NewestGameFragment extends RefreshRecyclerFragment implements BaseGameInfoIView {

    public static NewestGameFragment newInstance() {
        Bundle args = new Bundle();
        NewestGameFragment fragment = new NewestGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected RefreshRecyclerPresenter initRefreshRecyclerPresenter() {
        return new NewestGamePresenter(getBaseActivity(), this, this);
    }
}
