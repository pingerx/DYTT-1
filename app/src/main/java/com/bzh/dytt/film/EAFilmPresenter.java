package com.bzh.dytt.film;

import android.support.v4.widget.SwipeRefreshLayout;

import com.bzh.data.film.entity.FilmEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.refresh_recyclerview.RefreshRecyclerPresenter;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.util.ArrayList;

import rx.Observable;

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
public class EAFilmPresenter extends RefreshRecyclerPresenter<FilmEntity, ArrayList<FilmEntity>> implements SwipeRefreshLayout.OnRefreshListener, ExCommonAdapter.OnItemClickListener, ExRecyclerView.OnLoadMoreListener {

    public EAFilmPresenter(BaseActivity baseActivity, BaseFragment baseFragment, EAFilmIView view) {
        super(baseActivity, baseFragment, view);
    }

    public Observable<ArrayList<FilmEntity>> getRequestDataObservable(String nextPage) {
        return Repository.getInstance().getEuropeAmerica(Integer.valueOf(nextPage));
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {
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
}
