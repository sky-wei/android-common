package com.sky.android.core.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.sky.android.core.interfaces.OnItemEventListener;

/**
 * Created by sky on 11/28/20.
 */
public abstract class BaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    private final BaseRecyclerAdapter<T> mBaseRecyclerAdapter;    // 基本的Recycler适配器

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
        return itemView.findViewById(id);
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
     * @param view 响应事件的view
     * @param position 索引id
     */
    public void callItemEvent(View view, int position, Object... args) {
        callItemEvent(-1, view, position, args);
    }

    /**
     * 回调适配器的onItemClick
     * @param event 事件id
     * @param view 响应事件的view
     * @param position 索引id
     */
    public void callItemEvent(int event, View view, int position, Object... args) {
        // 事件响应回调
        OnItemEventListener listener = mBaseRecyclerAdapter.getOnItemEventListener();
        if (listener != null) listener.onItemEvent(event, view, position, args);
    }
}
