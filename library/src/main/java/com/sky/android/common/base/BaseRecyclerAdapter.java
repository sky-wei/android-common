/*
 * Copyright (c) 2017 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.android.common.interfaces.OnItemEventListener;

import java.util.List;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder<T>> {

    private Context mContext;
    private List<T> mItems;
    private LayoutInflater mLayoutInflater;     // 用于加载布局文件

    private OnItemEventListener mOnItemEventListener;

    public BaseRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    public OnItemEventListener getOnItemEventListener() {
        return mOnItemEventListener;
    }

    public void setOnItemEventListener(OnItemEventListener onItemEventListener) {
        mOnItemEventListener = onItemEventListener;
    }

    public void setItems(List<T> items) {
        mItems = items;
    }

    public List<T> getItems() {
        return mItems;
    }

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

    @Override
    public BaseRecyclerHolder<T> onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // 创建View
        View itemView = onCreateView(mLayoutInflater, viewGroup, viewType);

        // 创建ViewHolder
        BaseRecyclerHolder<T> baseRecyclerHolder = onCreateViewHolder(itemView, viewType);

        // 初始化操作
        if (baseRecyclerHolder != null) baseRecyclerHolder.onInitialize();

        return baseRecyclerHolder;
    }

    public LayoutInflater getmLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder<T> baseRecyclerHolder, int position) {

        if (baseRecyclerHolder == null) return ;

        // 进入绑定
        baseRecyclerHolder.onBind(position, getItemViewType(position));
    }

    @Override
    public void onViewRecycled(BaseRecyclerHolder<T> holder) {
        super.onViewRecycled(holder);
        // View释放
        if (holder != null) holder.onRecycled();
    }

    @Override
    public void onViewAttachedToWindow(BaseRecyclerHolder<T> holder) {
        super.onViewAttachedToWindow(holder);
        // View添加Window
        if (holder != null) holder.onAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(BaseRecyclerHolder<T> holder) {
        super.onViewDetachedFromWindow(holder);
        // View移除Window
        if (holder != null) holder.onDetachedFromWindow();
    }

    /**
     * 根据指定的类型创建View并返回
     * @param layoutInflater 用于加载布局文件类
     * @param viewGroup ViewGroup
     * @param viewType View类型
     * @return 返回View
     */
    public abstract View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, int viewType);

    /**
     * 给指定的View创建相应的ViewHolder
     * @param itemView View
     * @return 返回ViewHolder
     */
    public abstract BaseRecyclerHolder<T> onCreateViewHolder(View itemView, int viewType);
}
