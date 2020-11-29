/*
 * Copyright (c) 2020 The sky Authors.
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

package com.sky.android.core.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sky.android.common.util.ToastUtil;

/**
 * Created by sky on 2020-11-29.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取布局文件id
        int layoutId = getLayoutId();

        if (layoutId != 0) {
            setContentView(layoutId);
        }

        // 初始化
        initView(savedInstanceState, getIntent());
    }

    /**
     * 初始化view
     * @param savedInstanceState
     * @param intent
     */
    protected void initView(
            @Nullable Bundle savedInstanceState,
            @NonNull Intent intent
    ) {
        // 初始化View
        initView(intent);
    }

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     * @param intent
     */
    protected abstract void initView(@NonNull Intent intent);

    public Context getContext() {
        return this;
    }

    /**
     * 显示消息
     * @param msg
     */
    public void showMessage(String msg) {
        ToastUtil.show(msg);
    }
}
