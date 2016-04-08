package com.bzh.dytt.film.detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.bzh.data.film.FilmDetailEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.Utils;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic_pageswitch.PagePresenter;
import com.bzh.log.Util;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
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
public class FilmDetailPresenter extends PagePresenter implements View.OnClickListener {

    public static final String XUNLEI_PACKAGENAME = "com.xunlei.downloadprovider";
    private final IFilmDetailView filmDetailView;
    private String url;
    private FilmDetailEntity filmDetailEntity;

    public FilmDetailPresenter(BaseActivity baseActivity, BaseFragment baseFragment, IFilmDetailView filmDetailView) {
        super(baseActivity, baseFragment, filmDetailView);
        this.filmDetailView = filmDetailView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == android.R.id.home) {
            getBaseActivity().finish();
        } else if (v.getId() == R.id.fab) {
            if (Utils.checkIsInstall(baseActivity, XUNLEI_PACKAGENAME)) {
                baseActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(Utils.getThunderEncode(filmDetailEntity.getDownloadUrl()))));
            } else {
                Snackbar.make(v, "未安装迅雷", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    @Override
    public void initFragmentConfig() {
        if (null != baseFragment.getArguments()) {
            url = baseFragment.getArguments().getString(FilmDetailFragment.FILM_URL);
            if (!TextUtils.isEmpty(url)) {
                FilmDetailTaskSubscriber taskSubscriber = new FilmDetailTaskSubscriber();
                Repository.getInstance().getFilmDetail(url)
                        .doOnSubscribe(taskSubscriber)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(taskSubscriber);
            }
        }
        filmDetailView.initToolbar();
        filmDetailView.initFab();
    }

    private class FilmDetailTaskSubscriber extends AbstractTaskSubscriber<FilmDetailEntity> {

        @Override
        public void onSuccess(FilmDetailEntity filmDetailEntity) {
            super.onSuccess(filmDetailEntity);
            FilmDetailPresenter.this.filmDetailEntity = filmDetailEntity;
            updateFileDetailStatus();
        }
    }

    private void updateFileDetailStatus() {
        filmDetailView.setFilmDetail(filmDetailEntity);

    }
}
