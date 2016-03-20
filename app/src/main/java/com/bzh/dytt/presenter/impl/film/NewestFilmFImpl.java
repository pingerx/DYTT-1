package com.bzh.dytt.presenter.impl.film;

import android.support.v4.app.FragmentActivity;

import com.bzh.dytt.presenter.IFragmentPersenter;
import com.bzh.dytt.ui.activity.base.BaseActivity;
import com.bzh.dytt.ui.fragment.base.BaseFragment;
import com.bzh.dytt.ui.fragment.film.NewestFilmFragment;
import com.bzh.dytt.ui.view.film.INewestFilmView;

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
public class NewestFilmFImpl implements IFragmentPersenter {

    private final BaseActivity baseActivity;
    private BaseFragment baseFragment;
    private final INewestFilmView newestFilmView;

    public NewestFilmFImpl(BaseActivity baseActivity, BaseFragment baseFragment, INewestFilmView newestFilmView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.newestFilmView = newestFilmView;
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
}
