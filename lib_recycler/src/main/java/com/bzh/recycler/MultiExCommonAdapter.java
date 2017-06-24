package com.bzh.recycler;

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;


public abstract class MultiExCommonAdapter<T extends IModelType> extends ExCommonAdapter {

    private SparseArray<Integer> mLayouts;

    public MultiExCommonAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getDefItemViewType(int position) {
        return ((IModelType) mData.get(position)).getModelType();
    }

    @Override
    protected ExViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, getLayoutId(viewType));
    }

    private int getLayoutId(int viewType) {
        return mLayouts.get(viewType);
    }

    protected void addItemType(int type, int layoutResId) {
        if (mLayouts == null) {
            mLayouts = new SparseArray<>();
        }
        mLayouts.put(type, layoutResId);
    }

    @Override
    protected void convert(ExViewHolder helper, Object item) {
        convert(helper, (T) item);
    }

    protected abstract void convert(ExViewHolder helper, T item);
}
