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

package com.sky.android.core.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import java.lang.reflect.InvocationTargetException

/**
 * Created by sky on 2020-12-10.
 */
@Suppress("NULLABLE_TYPE_PARAMETER_AGAINST_NOT_NULL_TYPE_PARAMETER")
class ViewModelFactory(private val application: Application) : NewInstanceFactory() {

    companion object {

        @Volatile private var singleton: ViewModelProvider.Factory? = null

        fun getInstance(application: Application): ViewModelProvider.Factory {
            return singleton ?: synchronized(this) {
                singleton ?: ViewModelFactory(application).also { singleton = it }
            }
        }

        fun setSingletonInstance(factory: ViewModelProvider.Factory) {
            synchronized(this) {
                check(singleton == null) { "Singleton instance already exists." }
                singleton = factory
            }
        }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (AndroidViewModel::class.java.isAssignableFrom(modelClass)) {
            try {
                modelClass.getConstructor(Application::class.java).newInstance(application)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: InvocationTargetException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        } else super.create(modelClass)
    }
}