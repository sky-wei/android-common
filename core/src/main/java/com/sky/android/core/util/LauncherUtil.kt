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

package com.sky.android.core.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.sky.android.common.util.Alog

/**
 * Created by sky on 2020-12-07.
 */
object LauncherUtil {

    private val TAG = LauncherUtil::class.simpleName

    /**
     * 启动Activity
     */
    fun startActivity(context: Context, tClass: Class<out Activity>): Boolean {
        return startActivity(context, Intent(context, tClass))
    }

    /**
     * 启动Activity
     */
    fun startActivity(context: Context, intent: Intent): Boolean {
        try {
            // 启动Activity
            context.startActivity(intent)
            return true
        } catch (e: Exception) {
            Alog.e(TAG, "启动Activity异常", e)
        }
        return false
    }

    /**
     * 启动Activity
     */
    fun startActivityForResult(activity: Activity, intent: Intent, requestCode: Int): Boolean {
        try {
            // 启动Activity
            activity.startActivityForResult(intent, requestCode)
            return true
        } catch (e: Exception) {
            Alog.e(TAG, "启动Activity异常", e)
        }
        return false
    }

    /**
     * 启动Activity
     */
    fun startActivity(fragment: Fragment, intent: Intent): Boolean {
        try {
            // 启动Activity
            fragment.startActivity(intent)
            return true
        } catch (e: Exception) {
            Alog.e(TAG, "启动Activity异常", e)
        }
        return false
    }

    /**
     * 启动Activity
     */
    fun startActivityForResult(fragment: Fragment, intent: Intent, requestCode: Int): Boolean {
        try {
            // 启动Activity
            fragment.startActivityForResult(intent, requestCode)
            return true
        } catch (e: Exception) {
            Alog.e(TAG, "启动Activity异常")
        }
        return false
    }
}