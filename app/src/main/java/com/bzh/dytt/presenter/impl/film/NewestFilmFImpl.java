package com.bzh.dytt.presenter.impl.film;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bzh.data.film.entity.FilmEntity;
import com.bzh.domain.interactor.DefaultSubscriber;
import com.bzh.domain.interactor.GetFilmList;
import com.bzh.dytt.presenter.IFragmentPersenter;
import com.bzh.dytt.ui.activity.base.BaseActivity;
import com.bzh.dytt.ui.adapter.film.NewestFilmAdapter;
import com.bzh.dytt.ui.fragment.base.BaseFragment;
import com.bzh.dytt.ui.view.film.INewestFilmView;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
public class NewestFilmFImpl implements IFragmentPersenter, NewestFilmAdapter.OnRecyclerItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "NewestFilmFImpl";

    private final BaseActivity baseActivity;
    private final BaseFragment baseFragment;
    private final INewestFilmView newestFilmView;
    private GetFilmList getFilmList;
    private NewestFilmAdapter newestFilmAdapter;

    public NewestFilmFImpl(BaseActivity baseActivity, BaseFragment baseFragment, INewestFilmView newestFilmView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.newestFilmView = newestFilmView;
    }

    @Override
    public void onFirstUserVisible() {
        Log.d(TAG, "onFirstUserVisible() called with: " + "");
        newestFilmAdapter = new NewestFilmAdapter(baseActivity);
        newestFilmAdapter.setOnRecyclerItemClick(this);
        newestFilmView.initRecyclerView(new LinearLayoutManager(baseActivity), newestFilmAdapter);
        newestFilmView.setOnRefreshListener(this);
        getFilmList = new GetFilmList(1, Schedulers.io(), AndroidSchedulers.mainThread());
        getFilmList.execute(new NewestFilmSubscriber());
    }

    @Override
    public void onUserVisible() {
        Log.d(TAG, "onUserVisible() called with: " + "");
    }

    @Override
    public void onUserInvisible() {
        Log.d(TAG, "onUserInvisible() called with: " + "");
    }

    @Override
    public void onRecyclerItemClick(View view, int position) {
        Toast.makeText(baseActivity, "被点击了" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        getFilmList.execute(new NewestFilmSubscriber());
    }

    private final class NewestFilmSubscriber extends DefaultSubscriber<ArrayList<FilmEntity>> {

        @Override
        public void onStart() {
            super.onStart();
            newestFilmView.showSwipeRefreshing();
        }

        @Override
        public void onCompleted() {
            super.onCompleted();
            newestFilmView.hideSwipeRefreshing();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            newestFilmView.showException();
            newestFilmView.hideRecyclerView();
        }

        @Override
        public void onNext(ArrayList<FilmEntity> filmEntities) {
            super.onNext(filmEntities);
            Log.d(TAG, "onNext() called with: " + "filmEntities = [" + filmEntities + "]");
            newestFilmAdapter.setFilmEntities(filmEntities);
        }
    }

}
