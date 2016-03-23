package com.bzh.dytt.base.refresh_recyclerview;

import android.support.annotation.IntDef;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.bzh.dytt.base.basic.BaseActivity;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.dytt.base.basic.IFragmentPresenter;
import com.bzh.dytt.base.basic.IPaging;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.util.ArrayList;

import rx.Observable;
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
public abstract class RefreshRecyclerPresenter<Entity> extends Subscriber<ArrayList<Entity>> implements IFragmentPresenter, SwipeRefreshLayout.OnRefreshListener, ExCommonAdapter.OnItemClickListener, ExRecyclerView.OnLoadMoreListener {

    private Observable<ArrayList<Entity>> listObservable;

    /**
     * The definition of a page request data mode
     */
    @IntDef({REQUEST_MODE_DATA_FIRST, REQUEST_MODE_DATA_REFRESH, REQUEST_MODE_DATA_LOAD_MORE})
    public @interface MODE_REQUEST_DATA {
    }

    /**
     * The first request data
     */
    private static final int REQUEST_MODE_DATA_FIRST = 1;

    /**
     * The refresh current page to reset data
     */
    private static final int REQUEST_MODE_DATA_REFRESH = REQUEST_MODE_DATA_FIRST << 1;

    /**
     * Loading more data
     */
    private static final int REQUEST_MODE_DATA_LOAD_MORE = REQUEST_MODE_DATA_REFRESH << 1;

    /**
     * structure
     */
    private final BaseActivity baseActivity;
    private final BaseFragment baseFragment;
    private final RefreshRecyclerView refreshRecyclerView;


    private ExCommonAdapter<Entity> exCommonAdapter;

    /**
     * Paging information
     */
    private IPaging paging;

    public RefreshRecyclerPresenter(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView refreshRecyclerView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.refreshRecyclerView = refreshRecyclerView;
    }

    @Override
    public void initFragmentConfig() {
        paging = new DefaultPaging();
        exCommonAdapter = getExCommonAdapter();
        refreshRecyclerView.getRecyclerView().setOnItemClickListener(this);
        refreshRecyclerView.getRecyclerView().setOnLoadingMoreListener(this);
        refreshRecyclerView.initRecyclerView(new LinearLayoutManager(baseActivity), exCommonAdapter);
        refreshRecyclerView.getSwipeRefreshLayout().setOnRefreshListener(this);
    }


    @Override
    public void onUserVisible() {
    }

    @Override
    public void onUserInvisible() {
        if (this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    public void onRequestData(@MODE_REQUEST_DATA int requestMode) {
        if (REQUEST_MODE_DATA_FIRST == requestMode || REQUEST_MODE_DATA_REFRESH == requestMode) {
            paging = new DefaultPaging();
        }

        if (null != paging) {
            listObservable = getRequestDataObservable(paging.getNextPage());
            listObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        }
    }

    protected abstract Observable<ArrayList<Entity>> getRequestDataObservable(String nextPage);

    @Override
    public void onFirstUserVisible() {
        onRequestData(REQUEST_MODE_DATA_FIRST);
    }

    @Override
    public void onRefresh() {
        onRequestData(REQUEST_MODE_DATA_REFRESH);
    }

    @Override
    public void onLoadingMore() {
        onRequestData(REQUEST_MODE_DATA_LOAD_MORE);
    }

    @Override
    public void onItemClick(ExViewHolder viewHolder) {
    }

    public abstract ExCommonAdapter<Entity> getExCommonAdapter();

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }

    public BaseFragment getBaseFragment() {
        return baseFragment;
    }

    public RefreshRecyclerView getRefreshRecyclerView() {
        return refreshRecyclerView;
    }

    public ExCommonAdapter<Entity> getCommonAdapter() {
        return exCommonAdapter;
    }

    public IPaging getPaging() {
        return paging;
    }

    static class DefaultPaging implements IPaging {

        private int mIndex = 1;

        @Override
        public void processData() {
            mIndex++;
        }

        @Override
        public String getNextPage() {
            return mIndex + "";
        }
    }
}
