package com.bzh.dytt.film;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.BaseActivity;
import com.bzh.dytt.base.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.util.ArrayList;

import rx.Subscriber;
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
public class NewestFilmPresenter extends RefreshRecyclerPresenter<FilmEntity> implements SwipeRefreshLayout.OnRefreshListener, ExCommonAdapter.OnItemClickListener, ExRecyclerView.OnLoadMoreListener {

    private int index = 1;

    public NewestFilmPresenter(BaseActivity baseActivity, BaseFragment baseFragment, NewestFilmIView newestFilmIView) {
        super(baseActivity, baseFragment, newestFilmIView);
    }

    @Override
    public void requestData() {
        super.requestData();
        Repository.getInstance().getNewest(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NewestFilmSubscriber());
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        index = 1;
        Repository.getInstance().getNewest(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NewestFilmSubscriber());
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {
    }

    @Override
    public void onLoadingMore() {
        super.onLoadingMore();
        Repository.getInstance().getNewest(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<FilmEntity>>() {
                    @Override
                    public void onCompleted() {
                        index++;
                        getRefreshRecyclerView().getRecyclerView().finishLoadingMore();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<FilmEntity> filmEntities) {
                        getCommonAdapter().addData(filmEntities);
                    }
                });
    }

    @Override
    public ExCommonAdapter<FilmEntity> getExCommonAdapter() {
        return new ExCommonAdapter<FilmEntity>(getBaseActivity(), R.layout.item_newestfilm) {
            @Override
            protected void convert(ExViewHolder viewHolder, FilmEntity item) {
                viewHolder.setText(R.id.tv_film_name, item.getName());
                viewHolder.setText(R.id.tv_film_publish_time, getBaseActivity().getResources().getString(R.string.label_publish_time, item.getPublishTime()));
            }
        };
    }



    private final class NewestFilmSubscriber extends Subscriber<ArrayList<FilmEntity>> {

        @Override
        public void onStart() {
            super.onStart();
            getRefreshRecyclerView().showSwipeRefreshing();
        }

        @Override
        public void onCompleted() {
            getRefreshRecyclerView().hideSwipeRefreshing();
            index++;
        }

        @Override
        public void onError(Throwable e) {
            getRefreshRecyclerView().showLoadFailedLayout();
            getRefreshRecyclerView().hideContentLayout();
        }

        @Override
        public void onNext(ArrayList<FilmEntity> filmEntities) {
            getCommonAdapter().setData(filmEntities);
        }
    }
}
