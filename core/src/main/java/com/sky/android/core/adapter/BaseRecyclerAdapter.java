package com.sky.android.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.android.core.interfaces.OnItemEventListener;

import java.util.List;

/**
 * Created by sky on 11/28/20.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder<T>> {

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;     // 用于加载布局文件
    private List<T> mItems;

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

    public boolean isItemEmpty() {
        return mItems == null || mItems.isEmpty();
    }

    @Override
    public @NonNull BaseRecyclerHolder<T> onCreateViewHolder(
            @Nullable ViewGroup viewGroup, int viewType
    ) {
        // 创建View
        View itemView = onCreateView(mLayoutInflater, viewGroup, viewType);

        // 创建ViewHolder
        BaseRecyclerHolder<T> baseRecyclerHolder = onCreateViewHolder(itemView, viewType);

        // 初始化操作
        baseRecyclerHolder.onInitialize();

        return baseRecyclerHolder;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder<T> baseRecyclerHolder, int position) {

        // 进入绑定
        baseRecyclerHolder.onBind(position, getItemViewType(position));
    }

    @Override
    public void onViewRecycled(@NonNull BaseRecyclerHolder<T> holder) {
        super.onViewRecycled(holder);
        // View释放
        holder.onRecycled();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseRecyclerHolder<T> holder) {
        super.onViewAttachedToWindow(holder);
        // View添加Window
        holder.onAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull BaseRecyclerHolder<T> holder) {
        super.onViewDetachedFromWindow(holder);
        // View移除Window
        holder.onDetachedFromWindow();
    }

    /**
     * 根据指定的类型创建View并返回
     * @param layoutInflater 用于加载布局文件类
     * @param viewGroup ViewGroup
     * @param viewType View类型
     * @return 返回View
     */
    public abstract View onCreateView(
            @NonNull LayoutInflater layoutInflater,
            @Nullable ViewGroup viewGroup,
            int viewType
    );

    /**
     * 给指定的View创建相应的ViewHolder
     * @param itemView View
     * @return 返回ViewHolder
     */
    public abstract BaseRecyclerHolder<T> onCreateViewHolder(
            @NonNull View itemView,
            int viewType
    );
}
