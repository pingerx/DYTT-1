package com.bzh.dytt.film;

import android.support.v4.widget.SwipeRefreshLayout;

import com.bzh.data.basic.BaseInfoEntity;
import com.bzh.data.repository.Repository;
import com.bzh.dytt.R;
import com.bzh.dytt.base.god.BaseActivity;
import com.bzh.dytt.base.god.BaseFragment;
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
public class NewestFilmPresenter extends RefreshRecyclerPresenter<BaseInfoEntity, ArrayList<BaseInfoEntity>> implements SwipeRefreshLayout.OnRefreshListener, ExCommonAdapter.OnItemClickListener, ExRecyclerView.OnLoadMoreListener {

    public NewestFilmPresenter(BaseActivity baseActivity, BaseFragment baseFragment, NewestFilmIView newestFilmIView) {
        super(baseActivity, baseFragment, newestFilmIView);
    }

    public Observable<ArrayList<BaseInfoEntity>> getRequestListDataObservable(String nextPage) {
        return Repository.getInstance().getNewest(Integer.valueOf(nextPage));
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {
    }

    @Override
    public ExCommonAdapter<BaseInfoEntity> getExCommonAdapter() {
        return new ExCommonAdapter<BaseInfoEntity>(getBaseActivity(), R.layout.item_newestfilm) {
            @Override
            protected void convert(ExViewHolder viewHolder, BaseInfoEntity item) {
                viewHolder.setText(R.id.tv_film_name, item.getName());
                viewHolder.setText(R.id.tv_film_publish_time, getBaseActivity().getResources().getString(R.string.label_publish_time, item.getPublishTime()));
            }
        };
    }

    @Override
    public String getMaxPage() {
        return 131 + "";
    }
}
