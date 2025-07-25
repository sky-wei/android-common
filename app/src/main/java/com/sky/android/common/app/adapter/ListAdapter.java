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

package com.sky.android.common.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sky.android.common.app.R;
import com.sky.android.common.app.view.ItemLayout;
import com.sky.android.core.adapter.BaseListAdapter;

/**
 * Created by sky on 16-8-20.
 */
public class ListAdapter extends BaseListAdapter<String> {

    private ItemLayout curItemLayout;
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

//    @Override
//    public View onInflaterView(int type, LayoutInflater layoutInflater, ViewGroup parent) {
//        return layoutInflater.inflate(R.layout.item_list, parent, false);
//    }
//
//    @Override
//    public ViewHolder onInitViewHolder(int type, View view) {
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onInflateContent(int position, ViewHolder viewHolder, String content) {
//
//        viewHolder.setSelected(false);
//        viewHolder.setPosition(position);
//
//        viewHolder.tv_name.setText(content);
//    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, int viewType) {
        return layoutInflater.inflate(R.layout.item_list, parent, false);
    }

    @Override
    public ViewHolder<String> onCreateViewHolder(@NonNull View view, int viewType) {
        return new ValueHolder(view, this);
    }

    public class ValueHolder extends BaseListAdapter.ViewHolder<String> implements ItemLayout.OnItemSelectedListener {

        private ItemLayout mItemLayout;
        private int mPosition;
//        private TextView tvName;

        public ValueHolder(View itemView, BaseListAdapter<String> baseListAdapter) {
            super(itemView, baseListAdapter);
        }

        @Override
        public void onInitialize() {
            super.onInitialize();

            mItemLayout = (ItemLayout) mItemView;
            mItemLayout.setOnItemSelectedListener(this);

//            tvName = (TextView) findViewById(R.id.tv_name);
        }

        public void setSelected(boolean selected) {

            if (mItemLayout.isSelected() == selected) return ;

            mItemLayout.setSelected(selected);
        }

        public void setPosition(int position) {
            mPosition = position;
        }

        @Override
        public void onItemSelected(View view, boolean selected) {

            if (mOnItemSelectedListener == null) return ;

            if (selected) {
                if (curItemLayout != null) {
                    curItemLayout.setSelected(false);
                }

                curItemLayout = (ItemLayout) view;
                mOnItemSelectedListener.onItemSelected(view, mPosition);
                return ;
            }

            curItemLayout = null;
            mOnItemSelectedListener.onNothingSelected();
        }

        @Override
        public void onBind(int position, int viewType) {

        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(View view, int position);

        void onNothingSelected();
    }
}
