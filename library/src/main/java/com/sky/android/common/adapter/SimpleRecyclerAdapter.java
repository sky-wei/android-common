package com.sky.android.common.adapter;

import android.content.Context;

import com.sky.android.common.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class SimpleRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    public SimpleRecyclerAdapter(Context context) {
        super(context);
    }
}
