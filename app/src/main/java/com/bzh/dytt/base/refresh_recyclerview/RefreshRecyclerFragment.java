package com.bzh.dytt.base.refresh_recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.bzh.common.utils.UIUtils;
import com.bzh.dytt.R;
import com.bzh.dytt.base.basic.BaseFragment;
import com.bzh.recycler.ExRecyclerView;
import com.jakewharton.rxbinding.view.RxView;

import butterknife.Bind;

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
public abstract class RefreshRecyclerFragment extends BaseFragment implements RefreshRecyclerView {

    @Bind(R.id.layoutLoading)
    LinearLayout layoutLoading;

    @Bind(R.id.layoutLoadFailed)
    LinearLayout layoutLoadFailed;

    @Bind(R.id.layoutEmpty)
    LinearLayout layoutEmpty;

    @Bind(R.id.layoutContent)
    LinearLayout layoutContent;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recyclerView)
    ExRecyclerView recyclerView;

    private RefreshRecyclerPresenter refreshRecyclerViewPresenter;

    @Override
    protected void initFragmentConfig() {
        refreshRecyclerViewPresenter = initRefreshRecyclerPresenter();
        refreshRecyclerViewPresenter.initFragmentConfig();
    }

    protected abstract RefreshRecyclerPresenter initRefreshRecyclerPresenter();

    @Override
    protected int getContentView() {
        return R.layout.comm_lay_refresh_recyclerview;
    }

    @Override
    protected void onFirstUserVisible() {
        refreshRecyclerViewPresenter.onFirstUserVisible();
    }

    @Override
    protected void onUserVisible() {
        refreshRecyclerViewPresenter.onUserVisible();
    }

    @Override
    protected void onUserInvisible() {
        refreshRecyclerViewPresenter.onUserInvisible();
    }

    @Override
    public void showLoadingLayout() {
        layoutLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingLayout() {
        layoutLoading.setVisibility(View.GONE);
    }

    @Override
    public void layoutLoadingVisibility(boolean isVisible) {
        RxView.visibility(layoutLoading, View.GONE).call(isVisible);
    }

    @Override
    public void showLoadFailedLayout() {
        layoutLoadFailed.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadFailedLayout() {
        layoutLoadFailed.setVisibility(View.GONE);
    }

    @Override
    public void layoutLoadFailedVisibility(boolean isVisible) {
        RxView.visibility(layoutLoadFailed, View.GONE).call(isVisible);
    }

    @Override
    public void showEmptyLayout() {
        layoutEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyLayout() {
        layoutEmpty.setVisibility(View.GONE);
    }

    @Override
    public void layoutEmptyVisibility(boolean isVisible) {
        RxView.visibility(layoutEmpty, View.GONE).call(isVisible);
    }

    @Override
    public void showContentLayout() {
        layoutContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentLayout() {
        layoutContent.setVisibility(View.GONE);
    }

    @Override
    public void layoutContentVisibility(boolean isVisible) {
        RxView.visibility(layoutContent, View.GONE).call(isVisible);
    }

    @Override
    public void showSwipeRefreshing() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideSwipeRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void initRecyclerView(LinearLayoutManager linearLayoutManager, RecyclerView.Adapter adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setAdapter(adapter);
    }


    public ExRecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    public void finishLoadMore() {
        recyclerView.finishLoadingMore();
    }

    private class MyItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, UIUtils.dip2px(1));
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
        }
    }
}
