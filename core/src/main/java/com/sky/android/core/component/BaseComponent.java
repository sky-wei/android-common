package com.sky.android.core.component;

import android.content.Context;

import com.sky.android.core.interfaces.IComponent;

public abstract class BaseComponent implements IComponent {

    private final Context mContext;

    public BaseComponent(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void release() {

    }
}
