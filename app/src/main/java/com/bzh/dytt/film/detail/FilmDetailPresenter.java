package com.bzh.dytt.film.detail;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.bzh.data.film.FilmDetailEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.film.list.IFilmDetailView;
import com.bzh.log.MyLog;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-4-3<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public class FilmDetailPresenter implements IFragmentPresenter, View.OnClickListener {

    private final BaseActivity baseActivity;
    private BaseFragment baseFragment;
    private final IFilmDetailView filmDetailView;
    private String url;

    public FilmDetailPresenter(BaseActivity baseActivity, BaseFragment baseFragment, IFilmDetailView filmDetailView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.filmDetailView = filmDetailView;
    }


    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void initFragmentConfig() {
        if (null != baseFragment.getArguments()) {
            url = baseFragment.getArguments().getString(FilmDetailFragment.FILM_URL);
            if (!TextUtils.isEmpty(url)) {
                Repository.getInstance().getFilmDetail(url)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<FilmDetailEntity>() {
                            @Override
                            public void call(FilmDetailEntity filmDetailEntity) {
                                filmDetailView.setFilmPoster(filmDetailEntity.getCoverUrl());
                            }
                        });
            }
        }
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
