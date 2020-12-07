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

package com.sky.android.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sky.android.common.util.ToastUtil;

/**
 * Created by sky on 2020-11-29.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        return createView(inflater, container);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化View
        initView(view, getArguments());
    }

    /**
     * 创建View
     * @param inflater
     * @param container
     * @return
     */
    protected View createView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container
    ) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    /**
     * 获取布局id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     * @param view
     * @param args
     */
    protected abstract void initView(@NonNull View view, @Nullable Bundle args);

    @NonNull
    public Context getContext() {
        return getActivity();
    }

    public Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    /**
     * 显示提示消息
     * @param msg
     */
    public void showMessage(@NonNull String msg) {
        ToastUtil.show(msg);
    }
}
