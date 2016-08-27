package com.sky.android.common.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.android.common.app.view.ItemLyaout;
import com.sky.android.common.app.R;
import com.sky.android.common.base.BaseListAdapter;

/**
 * Created by starrysky on 16-8-20.
 */
public class ListAdapter extends BaseListAdapter<String, ListAdapter.ViewHolder> {

    private ItemLyaout curItemLyaout;
    private OnItemSelectedListener mOnItemSelectedListener;

    public ListAdapter(Context context) {
        super(context);
    }

    public OnItemSelectedListener getOnItemSelectedListener() {
        return mOnItemSelectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public View onInflaterView(int type, LayoutInflater layoutInflater, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.item_list, parent, false);
    }

    @Override
    public ViewHolder onInitViewHolder(int type, View view) {
        return new ViewHolder(view);
    }

    @Override
    public void onInflateContent(int position, ViewHolder viewHolder, String content) {

        viewHolder.setSelected(false);
        viewHolder.setPosition(position);

        viewHolder.tv_name.setText(content);
    }

    public class ViewHolder implements ItemLyaout.OnItemSelectedListener {

        private ItemLyaout mItemLyaout;
        private int mPosition;
        private TextView tv_name;

        public ViewHolder(View view) {

            mItemLyaout = (ItemLyaout) view;
            mItemLyaout.setOnItemSelectedListener(this);

            tv_name = (TextView) view.findViewById(R.id.tv_name);
        }

        public void setSelected(boolean selected) {

            if (mItemLyaout.isSelected() == selected) return ;

            mItemLyaout.setSelected(selected);
        }

        public void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onItemSelected(View view, boolean selected) {

            if (mOnItemSelectedListener == null) return ;

            if (selected) {
                if (curItemLyaout != null) {
                    curItemLyaout.setSelected(false);
                }

                curItemLyaout = (ItemLyaout) view;
                mOnItemSelectedListener.onItemSelected(view, mPosition);
                return ;
            }

            curItemLyaout = null;
            mOnItemSelectedListener.onNothingSelected();
        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(View view, int position);

        void onNothingSelected();
    }
}
