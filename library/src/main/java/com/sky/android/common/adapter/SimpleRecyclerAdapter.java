package com.sky.android.common.adapter;

import android.content.Context;

import com.sky.android.common.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class SimpleRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    private List<T> mItems;

    public SimpleRecyclerAdapter(Context context) {
        super(context);
    }

    public void setItems(List<T> items) {
        mItems = items;
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public T getItem(int position) {
        return isItemEmpty() ? null : mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return isItemEmpty() ? 0 : mItems.size();
    }

    protected boolean isItemEmpty() {
        return mItems == null || mItems.isEmpty() ? true : false;
    }
}
