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

package com.sky.android.core.interfaces

import androidx.lifecycle.Lifecycle

/**
 * Created by sky on 2020-12-10.
 */
interface IBaseView {

    /**
     * 获取Lifecycle
     */
    fun getMyLifecycle(): Lifecycle

    /**
     * 显示加载提示框
     */
    fun showLoading()

    /**
     * 取消加载提示框
     */
    fun cancelLoading()

    /**
     * 显示消息
     * @param msg
     */
    fun showMessage(msg: String)
}