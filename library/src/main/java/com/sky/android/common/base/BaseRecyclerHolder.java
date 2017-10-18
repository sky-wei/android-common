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

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sky.android.common.interfaces.OnItemEventListener;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    private BaseRecyclerAdapter<T> mBaseRecyclerAdapter;    // 基本的Recycler适配器

    public BaseRecyclerHolder(View itemView, BaseRecyclerAdapter<T> baseRecyclerAdapter) {
        super(itemView);
        mBaseRecyclerAdapter = baseRecyclerAdapter;
    }

    /**
     * 初始化，用来初始化相应的控件
     */
    public abstract void onInitialize();

    /**
     * 绑定View，用于处理数据跟View进行关联
     * @param position 数据索引id
     * @param viewType View类型
     */
    public abstract void onBind(int position, int viewType);

    /**
     * 回收View
     */
    public void onRecycled() {
    }

    /**
     * View添加到Window
     */
    public void onAttachedToWindow() {
    }

    /**
     * View被移除Vindow
     */
    public void onDetachedFromWindow() {

    }

    /**
     * 通过控件id查找相应的View
     * @param id 控件id
     * @return 返回View
     */
    public View findViewById(int id) {
        return itemView == null ? null : itemView.findViewById(id);
    }

    /**
     * 获取当前绑定的View
     * @return 返回View
     */
    public View getItemView() {
        return itemView;
    }

    /**
     * 返回Recycler的适配器
     * @return
     */
    public BaseRecyclerAdapter<T> getBaseRecyclerAdapter() {
        return mBaseRecyclerAdapter;
    }

    /**
     * 获取适配器的Item的数量
     * @return
     */
    public int getItemCount() {
        return mBaseRecyclerAdapter.getItemCount();
    }

    /**
     * 获取指定索引id的内容信息
     * @param position 索引id
     * @return 指定id的内容信息
     */
    public T getItem(int position) {
        return mBaseRecyclerAdapter.getItem(position);
    }

    /**
     * 回调适配器的onItemClick
     * @param event 事件id
     * @param view 响应事件的view
     * @param position 索引id
     */
    public void onItemEvent(int event, View view, int position, Object... args) {
        // 事件响应回调
        OnItemEventListener listener = mBaseRecyclerAdapter.getOnItemEventListener();
        if (listener != null) listener.onItemEvent(event, view, position, args);
    }
}
