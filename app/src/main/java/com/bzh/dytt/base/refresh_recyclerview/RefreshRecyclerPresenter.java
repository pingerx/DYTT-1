package com.bzh.dytt.base.refresh_recyclerview;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.bzh.dytt.base.god.BaseActivity;
import com.bzh.dytt.base.god.BaseFragment;
import com.bzh.dytt.base.god.IFragmentPresenter;
import com.bzh.dytt.base.god.IPaging;
import com.bzh.dytt.rx.TaskSubscriber;
import com.bzh.recycler.ExCommonAdapter;
import com.bzh.recycler.ExRecyclerView;
import com.bzh.recycler.ExViewHolder;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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
public abstract class RefreshRecyclerPresenter<Entity, Entities> implements
        IFragmentPresenter,
        SwipeRefreshLayout.OnRefreshListener,
        ExCommonAdapter.OnItemClickListener,
        ExRecyclerView.OnLoadMoreListener {

    private static final String TAG = "REPresenter";

    private Observable<Entities> listObservable;
    private DefaultTaskSubscriber subscriber;
    private RefreshConfig refreshConfig;
    private boolean contentEmpty = true;

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
    private static final int REQUEST_MODE_DATA_REFRESH = 2;

    /**
     * Loading more data
     */
    private static final int REQUEST_MODE_DATA_LOAD_MORE = 3;


    @IntDef({TASK_STATE_PREPARE, TASK_STATE_SUCCESS, TASK_STATE_FINISH, TASK_STATE_FAILED})
    public @interface TASK_STATE {
    }

    private static final int TASK_STATE_PREPARE = 1;
    private static final int TASK_STATE_SUCCESS = 2;
    private static final int TASK_STATE_FINISH = 3;
    private static final int TASK_STATE_FAILED = 4;

    /**
     * structure
     */
    private final BaseActivity baseActivity;
    private final BaseFragment baseFragment;
    private final RefreshRecyclerView iView;
    private ExCommonAdapter<Entity> exCommonAdapter;

    /**
     * Paging information
     */
    private IPaging paging;

    private WeakReference<Handler> handlerWR = new WeakReference<Handler>(new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    });


    public RefreshRecyclerPresenter(BaseActivity baseActivity, BaseFragment baseFragment, RefreshRecyclerView iView) {
        this.baseActivity = baseActivity;
        this.baseFragment = baseFragment;
        this.iView = iView;
    }

    @Override
    public void initFragmentConfig() {
        paging = configPaging();
        refreshConfig = new RefreshConfig();
        exCommonAdapter = getExCommonAdapter();
        iView.getRecyclerView().setOnItemClickListener(this);
        iView.getRecyclerView().setOnLoadingMoreListener(this);
        iView.initRecyclerView(new LinearLayoutManager(baseActivity), exCommonAdapter);
        iView.getSwipeRefreshLayout().setOnRefreshListener(this);
        iView.getSwipeRefreshLayout().setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        onRefreshConfigChanged(refreshConfig);
    }

    /**
     * Update the page some state when the configuration changed.
     */
    public void onRefreshConfigChanged(RefreshConfig refreshConfig) {
        iView.footerVisibility(isRefreshing());
        iView.layLoadingVisibility(refreshConfig.canLoadMore);
        iView.btnLoadMoreVisibility(!refreshConfig.canLoadMore);
        iView.setTextLoadingHint(loadingHintLabel());
        iView.setTextLoadMoreHint(loadDisabledLabel());
    }

    public String loadingHintLabel() {
        return "加载中...";
    }

    public String loadDisabledLabel() {
        return "全部都加载完了";
    }

    @NonNull
    public IPaging configPaging() {
        return new DefaultPaging();
    }

    @Override
    public void onUserVisible() {
    }

    @Override
    public void onUserInvisible() {
        if (subscriber != null && subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    public void onRequestData(@MODE_REQUEST_DATA int requestMode) {

        Log.d(TAG, "onRequestData() called with: " + "requestMode = [" + getRequestModeName(requestMode) + "]");

        if (REQUEST_MODE_DATA_FIRST == requestMode || REQUEST_MODE_DATA_REFRESH == requestMode) {
            paging = configPaging();
        }

        subscriber = new DefaultTaskSubscriber(requestMode);

        if (null != paging) {
            listObservable = getRequestDataObservable(paging.getNextPage());
            listObservable
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(subscriber)
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }
    }

    private String getRequestModeName(int requestMode) {
        switch (requestMode) {
            case REQUEST_MODE_DATA_FIRST:
                return "第一次请求数据";
            case REQUEST_MODE_DATA_REFRESH:
                return "刷新数据";
            case REQUEST_MODE_DATA_LOAD_MORE:
                return "加载更多数据";
        }
        return "未知状态";
    }


    public abstract Observable<Entities> getRequestDataObservable(String nextPage);

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
        if (refreshConfig != null && refreshConfig.canLoadMore) {
            onRequestData(REQUEST_MODE_DATA_LOAD_MORE);
        }
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

    public RefreshRecyclerView getiView() {
        return iView;
    }

    public ExCommonAdapter<Entity> getCommonAdapter() {
        return exCommonAdapter;
    }

    public IPaging getPaging() {
        return paging;
    }

    public class DefaultPaging implements IPaging {

        private int mIndex = 1;

        @Override
        public String getMaxPage() {
            return RefreshRecyclerPresenter.this.getMaxPage();
        }

        @Override
        public void processData() {
            mIndex++;
        }

        @Override
        public String getNextPage() {
            return mIndex + "";
        }
    }

    public static class RefreshConfig implements Serializable {
        public boolean canLoadMore = true; // initial value must be true;
    }

    /**
     * Maximum value of the current page request index.
     */
    public abstract String getMaxPage();

    public class DefaultTaskSubscriber extends Subscriber<Entities> implements TaskSubscriber<Entities>, Action0 {

        private int requestMode;

        public DefaultTaskSubscriber(int requestMode) {
            this.requestMode = requestMode;
        }

        @Override
        final public void onStart() {
            // no something to do
        }

        @Override
        final public void onCompleted() {
            this.onFinish();
            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

        @Override
        final public void onError(Throwable e) {
            this.onFailure(e);
            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

        @Override
        public void onNext(Entities entities) {
            this.onSuccess(entities);
        }

        @Override
        final public void call() {
            this.onPrepare();
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////

        @Override
        public void onPrepare() {
            taskStateChanged(TASK_STATE_PREPARE, null);
        }

        @Override
        public void onFinish() {
            taskStateChanged(TASK_STATE_FINISH, null);
        }

        @Override
        public void onFailure(Throwable e) {
            taskStateChanged(TASK_STATE_FAILED, e.getMessage());
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(Entities entities) {
            setContentEmpty(resultIsEmpty(entities));
            taskStateChanged(TASK_STATE_SUCCESS, null);

            if (entities == null || exCommonAdapter == null) {
                return;
            }

            ArrayList<Entity> resultList = (ArrayList<Entity>) entities;

            if (requestMode == REQUEST_MODE_DATA_FIRST || requestMode == REQUEST_MODE_DATA_REFRESH) {
                exCommonAdapter.setData(resultList);
            } else if (requestMode == REQUEST_MODE_DATA_LOAD_MORE) {
                exCommonAdapter.addData(resultList);
            }

            // process paging
            if (null != paging) {
                paging.processData();
            }

            // process canLoadMOre
            if (requestMode == REQUEST_MODE_DATA_FIRST) {
                refreshConfig.canLoadMore = true;
            }

            try {
                if (null != paging) {
                    if (Integer.valueOf(paging.getNextPage()) > Integer.valueOf(paging.getMaxPage())) {
                        refreshConfig.canLoadMore = false;
                    } else {
                        refreshConfig.canLoadMore = true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                refreshConfig.canLoadMore = false;
            }

            onRefreshConfigChanged(refreshConfig);
        }

        public boolean resultIsEmpty(Entities entities) {
            return entities == null;
        }
    }

    public void onRefreshViewComplete() {
        if (iView.isRefreshing()) {
            iView.hideSwipeRefreshing();
        }
        if (iView.isLoadingMore()) {
            iView.finishLoadMore();
        }
    }

    /**
     * Whether the page is refreshing or loading state.
     */
    public boolean isRefreshing() {
        return subscriber != null && !subscriber.isUnsubscribed();
    }

    /**
     * According to the task execution state to update the page display state.
     *
     * @param tag May result in a task execution process information
     */
    public void taskStateChanged(@TASK_STATE int taskState, Serializable tag) {
        switch (taskState) {
            case TASK_STATE_PREPARE: {
                iView.layoutLoadingVisibility(isContentEmpty());
                iView.layoutContentVisibility(!isContentEmpty());
                iView.layoutEmptyVisibility(false);
                iView.layoutLoadFailedVisibility(false);
            }
            break;
            case TASK_STATE_SUCCESS: {
                iView.layoutLoadingVisibility(false);
                iView.layoutEmptyVisibility(isContentEmpty());
                iView.layoutContentVisibility(!isContentEmpty());
            }
            break;
            case TASK_STATE_FAILED: {
                iView.layoutEmptyVisibility(!isContentEmpty());
                iView.layoutLoadingVisibility(!isContentEmpty());
                iView.layoutLoadFailedVisibility(isContentEmpty());
                if (tag != null) {
                    iView.setTextLoadFailed(tag.toString());
                }
            }
            break;
            case TASK_STATE_FINISH: {
                if (isRefreshing()) {
                    onRefreshViewComplete();
                }
            }
            break;
        }
    }

    public void setContentEmpty(boolean empty) {
        this.contentEmpty = empty;
    }

    public boolean isContentEmpty() {
        return contentEmpty;
    }
}
