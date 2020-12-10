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

package com.sky.android.core.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sky.android.core.viewmodel.BaseViewModel
import com.sky.android.core.viewmodel.ViewModelFactory

/**
 * Created by sky on 2020-12-07.
 */
abstract class BaseVMActivity<VM: BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun initView(savedInstanceState: Bundle?, intent: Intent) {

        mViewModel = createViewModel()

        // 初始化
        initView(intent)
    }

    /**
     * 初始化ViewModel
     */
    protected open fun createViewModel(): VM {
        return ViewModelProvider(
                this,
                ViewModelFactory.getInstance(application)
        ).get(viewModelClass)
    }

    /**
     * 获取ViewModel的Class
     */
    protected abstract val viewModelClass: Class<VM>
}