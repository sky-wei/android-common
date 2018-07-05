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

package com.sky.android.common.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by sky on 16-8-20.
 */
public class ItemLyaout extends RelativeLayout implements View.OnClickListener {

    private OnClickListener mOnClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    public ItemLyaout(Context context) {
        super(context);
        init();
    }

    public ItemLyaout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemLyaout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    @Override
    public boolean hasOnClickListeners() {
        return mOnClickListener != null;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener l) {
        mOnItemSelectedListener = l;
    }

    public boolean hasOnItemSelectedListeners() {
        return mOnItemSelectedListener != null;
    }

    @Override
    public void onClick(View v) {
        if (!isSelected()) setSelected(true);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (hasOnItemSelectedListeners()) {
            mOnItemSelectedListener.onItemSelected(this, selected);
        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(View view, boolean selected);
    }
}
