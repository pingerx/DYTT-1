package com.bzh.recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bzh.lib_recycler.R;

public class ExRecyclerView extends RecyclerView {

    private ExCommonAdapter.OnItemLongClickListener onItemLongClickListener;
    private ExCommonAdapter.OnItemClickListener onItemClickListener;

    private boolean isLoadingMore;
    private OnLoadMoreListener onLoadingMoreListener;

    private View headerView;
    private View footerView;

    public ExRecyclerView(Context context) {
        this(context, null);
    }

    public ExRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    protected void initView(Context context) {
        initDefaultFooterView(context);
        this.addOnScrollListener(new OnExScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                if (onLoadingMoreListener != null) {
                    onLoadMore(onLoadingMoreListener);
                }
            }
        });
    }

    private void initDefaultFooterView(Context context) {
        footerView = LayoutInflater.from(context).inflate(R.layout.comm_footer, null);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }


    public void addFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void addHeaderView(View headerView) {
        this.headerView = headerView;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof ExCommonAdapter) {
            ((ExCommonAdapter) adapter).setOnItemClickListener(onItemClickListener);
            ((ExCommonAdapter) adapter).setOnItemLongClickListener(onItemLongClickListener);
            ((ExCommonAdapter) adapter).setHeaderView(headerView);
            ((ExCommonAdapter) adapter).setFooterView(footerView);
        }
    }

    /**
     * 执行加载更多的逻辑，需要传入加载更多时加载数据的监听
     */
    public void onLoadMore(final OnLoadMoreListener onLoadingMoreListener) {
        if (footerView != null && !isLoadingMore) {
            isLoadingMore = true;
            footerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onLoadingMoreListener.onLoadingMore();
                }
            }, 300);
        }
    }

    public void finishLoadingMore() {
        if (footerView != null && isLoadingMore) {
            isLoadingMore = false;
        }
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }

    public void setOnItemLongClickListener(ExCommonAdapter.OnItemLongClickListener mOnItemLongClickListener) {
        this.onItemLongClickListener = mOnItemLongClickListener;
    }

    public void setOnItemClickListener(ExCommonAdapter.OnItemClickListener mOnItemClickListener) {
        this.onItemClickListener = mOnItemClickListener;
    }

    public void setOnLoadingMoreListener(OnLoadMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }

    public View getFooterView() {
        return footerView;
    }

    public interface OnLoadMoreListener {
        void onLoadingMore();
    }

    public View getHeaderView() {
        return headerView;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction < 1) {
            return !(!super.canScrollVertically(direction) || getChildAt(0) != null && getChildAt(0).getTop() >= 0);
        }
        return super.canScrollVertically(direction);
    }
}
