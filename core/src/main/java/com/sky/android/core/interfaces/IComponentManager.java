/*
 * Copyright (c) 2021 The sky Authors.
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

package com.sky.android.core.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by sky on 2021-07-20.
 */
public interface IComponentManager {

    /**
     * 获取组件
     * @param name
     * @param <T>
     * @return
     */
    <T extends IComponent> T get(@NonNull Class<T> name);

    /**
     * 获取所有组件
     * @return
     */
    List<IComponent> getAllComponent();

    /**
     * 移除组件
     * @param name
     */
    void remove(@NonNull Class<? extends IComponent> name);

    /**
     * 是否包含组件
     * @param name
     * @return
     */
    boolean contains(@NonNull Class<? extends IComponent> name);

    /**
     * 释放所有
     */
    void release();

    /**
     * 添加监听
     * @param listener
     */
    void addListener(@NonNull ComponentListener listener);

    /**
     * 移除监听
     * @param listener
     */
    void removeListener(@NonNull ComponentListener listener);


    /**
     * 工厂类
     */
    interface Factory {

        <T extends IComponent> IComponent create(@NonNull Context context, @NonNull Class<T> name);
    }


    /**
     * 监听接口类
     */
    interface ComponentListener {

        /**
         * 创建
         * @param name
         */
        void onCreate(@NonNull Class<? extends IComponent> name);

        /**
         * 释放
         * @param name
         */
        void onRelease(@NonNull Class<? extends IComponent> name);
    }
}
