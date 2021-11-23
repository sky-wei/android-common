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

package com.sky.android.core.data.model

import com.sky.android.common.util.Alog

/**
 * Created by sky on 2021/11/23.
 */
sealed class XResult<out T> {

    data class Success<out T>(val value: T) : XResult<T>()

    data class Failure(val throwable: Throwable?) : XResult<Nothing>()

    companion object {

        val Invalid = Failure(NullPointerException())
    }
}

inline fun <reified T> XResult<T>.doSuccess(
    success: (T) -> Unit
): XResult<T> {
    if (this is XResult.Success) {
        success(value)
    }
    return this
}

inline fun <reified T> XResult<T>.doFailure(
    failure: (Throwable?) -> Unit
): XResult<T> {
    if (this is XResult.Failure) {
        Alog.e("处理异常", throwable)
        failure(throwable)
    }
    return this
}