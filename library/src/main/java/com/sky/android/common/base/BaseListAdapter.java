package com.sky.android.common.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class BaseListAdapter<Content, ViewHolder> extends BaseAdapter {

    private Context context;
    private List<Content> contents;
    private LayoutInflater layoutInflater;

    public BaseListAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Content> getContents() {
        return contents;
    }

    @Override
    public int getCount() {
        return contents == null ? 0 : contents.size();
    }

    @Override
    public Content getItem(int position) {
        return contents == null ? null : contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Content content = getItem(position);
        int type = getItemViewType(position);

        if (convertView == null) {

            convertView = onInflaterView(type, layoutInflater, parent);

            convertView.setTag(onInitViewHolder(type, convertView));
        }

        onInflateContent(position, (ViewHolder)convertView.getTag(), content);

        return convertView;
    }

    /**
     * 实例显示的View
     * @param type
     * @param layoutInflater
     * @param parent
     * @return
     */
    public abstract View onInflaterView(int type, LayoutInflater layoutInflater, ViewGroup parent);

    /**
     * 初始化View
     * @param view
     * @return
     */
    public abstract ViewHolder onInitViewHolder(int type, View view);

    /**
     * 设置View的内容信息
     * @param position
     * @param viewHolder
     * @param content
     */
    public abstract void onInflateContent(int position, ViewHolder viewHolder, Content content);
}
