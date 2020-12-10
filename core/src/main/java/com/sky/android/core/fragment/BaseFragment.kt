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
package com.sky.android.core.fragment

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sky.android.common.util.ToastUtil
import com.sky.android.core.interfaces.IBaseView

/**
 * Created by sky on 2020-11-29.
 */
abstract class BaseFragment : Fragment(), IBaseView {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return createView(inflater, container)
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化View
        initView(view, savedInstanceState, arguments)
    }

    /**
     * 创建View
     * @param inflater
     * @param container
     * @return
     */
    protected open fun createView(
            inflater: LayoutInflater,
            container: ViewGroup?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    /**
     * 初始化view
     * @param view
     * @param savedInstanceState
     * @param args
     */
    protected open fun initView(
            view: View,
            savedInstanceState: Bundle?,
            args: Bundle?
    ) {
        // 初始化View
        initView(view, args)
    }

    /**
     * 获取布局id
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 初始化View
     * @param view
     * @param args
     */
    protected abstract fun initView(view: View, args: Bundle?)

    override fun getContext(): Context {
        return activity!!
    }

    val applicationContext: Context
        get() = context.applicationContext

    val application: Application
        get() = activity!!.application

    override fun showLoading() {
        val baseView = activity
        if (baseView is IBaseView) {
            baseView.showLoading()
        }
    }

    override fun cancelLoading() {
        val baseView = activity
        if (baseView is IBaseView) {
            baseView.cancelLoading()
        }
    }

    /**
     * 显示提示消息
     * @param msg
     */
    override fun showMessage(msg: String) {
        ToastUtil.show(msg)
    }
}