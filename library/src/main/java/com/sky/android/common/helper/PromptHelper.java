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

package com.sky.android.common.helper;

import android.view.View;
import android.widget.TextView;

import com.sky.android.common.utils.ViewUtils;

/**
 * Created by starrysky on 16-8-2.
 */
public class PromptHelper {

    private View mView;
    private TextView mPromptText;

    public PromptHelper(View view, int prompText) {

        mView = view;
        mPromptText = (TextView) view.findViewById(prompText);
    }

    private void setPromptText(int text) {
        mPromptText.setText(text);
    }

    private void setPromptText(String text) {
        mPromptText.setText(text);
    }

    public void show(int text) {
        setPromptText(text);
        ViewUtils.setVisibility(mView, View.VISIBLE);
    }

    public void show(String text) {
        setPromptText(text);
        ViewUtils.setVisibility(mView, View.VISIBLE);
    }

    public void hide() {
        ViewUtils.setVisibility(mView, View.GONE);
    }
}
