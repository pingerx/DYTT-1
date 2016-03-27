package com.bzh.dytt.base.refresh_recyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private static final String TAG = "RefreshRecyclerFragment";

    @Bind(R.id.layoutLoading)
    LinearLayout layoutLoading;

    @Bind(R.id.layoutLoadFailed)
    LinearLayout layoutLoadFailed;

    @Bind(R.id.txtLoadFailed)
    TextView txtLoadFailed;

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
    public void layoutLoadingVisibility(boolean isVisible) {
        if (layoutLoading != null) {
            RxView.visibility(layoutLoading, View.GONE).call(isVisible);
        }
    }

    @Override
    public void layoutLoadFailedVisibility(boolean isVisible) {
        if (layoutLoadFailed != null) {
            RxView.visibility(layoutLoadFailed, View.GONE).call(isVisible);
        }
    }

    @Override
    public void layoutEmptyVisibility(boolean isVisible) {
        if (layoutEmpty != null) {
            RxView.visibility(layoutEmpty, View.GONE).call(isVisible);
        }
    }

    @Override
    public void layoutContentVisibility(boolean isVisible) {
        if (layoutContent != null) {
            RxView.visibility(layoutContent, View.GONE).call(isVisible);
        }
    }

    @Override
    public void btnLoadMoreVisibility(boolean isVisible) {
        if (recyclerView.getFooterView().findViewById(R.id.btnLoadMore) != null) {
            RxView.visibility(recyclerView.getFooterView().findViewById(R.id.btnLoadMore), View.GONE).call(isVisible);
        }
    }

    @Override
    public void layLoadingVisibility(boolean isVisible) {
        if (recyclerView.getFooterView().findViewById(R.id.layLoading) != null) {
            RxView.visibility(recyclerView.getFooterView().findViewById(R.id.layLoading), View.GONE).call(isVisible);
        }
    }

    @Override
    public void setTextLoadingHint(String content) {
        if (recyclerView.getFooterView().findViewById(R.id.txtLoadingHint) != null) {
            if (!TextUtils.isEmpty(content)) {
                ((TextView) recyclerView.getFooterView().findViewById(R.id.txtLoadingHint)).setText(content);
            }
        }
    }


    @Override
    public void footerVisibility(boolean isVisible) {
        if (recyclerView.getFooterView() != null) {
            RxView.visibility(recyclerView.getFooterView(), View.GONE).call(isVisible);
        }
    }

    @Override
    public void setTextLoadMoreHint(String content) {
        if (recyclerView.getFooterView().findViewById(R.id.btnLoadMore) != null) {
            if (!TextUtils.isEmpty(content)) {
                ((TextView) recyclerView.getFooterView().findViewById(R.id.btnLoadMore)).setText(content);
            }
        }
    }

    @Override
    public void showSwipeRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideSwipeRefreshing() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public boolean isRefreshing() {
        if (swipeRefreshLayout != null) {
            return swipeRefreshLayout.isRefreshing();
        }
        return false;
    }

    @Override
    public boolean isLoadingMore() {
        return recyclerView != null && recyclerView.isLoadingMore();
    }

    @Override
    public void setTextLoadFailed(String content) {
        if (txtLoadFailed != null) {
            if (!TextUtils.isEmpty(content)) {
                txtLoadFailed.setText(content);
            } else {
                Log.i(TAG, "setTextLoadFailed: content is empty");
            }
        }
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
