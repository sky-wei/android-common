package com.sky.android.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.android.common.interfaces.OnItemEventListener;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder<T>> {

    private Context mContext;
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

    /**
     * 获取指定索引id的内容信息
     * @param position 索引id
     * @return 指定id的内容信息
     */
    public abstract T getItem(int position);
}
