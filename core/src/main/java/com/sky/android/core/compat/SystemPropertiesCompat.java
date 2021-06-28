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

package com.sky.android.core.compat;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.sky.android.common.util.ReflectUtils;

/**
 * Created by sky on 2021-06-28.
 */
public class SystemPropertiesCompat {

    /**
     * 获取Class对象
     * @return
     */
    public static Class<?> systemPropertiesClass() {
        return ReflectUtils.classForName("android.os.SystemProperties");
    }

    /**
     * 获取指定名称的系统属性信息
     * @param key
     * @return
     */
    public static String get(@NonNull String key) {
        return get(key, "");
    }

    public static String get(@NonNull String key, String def) {
        return (String) ReflectUtils.invokeQuietly(
                systemPropertiesClass(), "get",
                new Class[]{String.class, String.class}, new Object[]{key, def});
    }

    public static int getInt(@NonNull String key) {
        return getInt(key, 0);
    }

    public static int getInt(@NonNull String key, int def) {
        return (int) ReflectUtils.invokeQuietly(
                systemPropertiesClass(), "getInt",
                new Class[]{String.class, int.class}, new Object[]{key, def});
    }

    public static long getLong(@NonNull String key) {
        return getLong(key, 0L);
    }

    public static long getLong(@NonNull String key, long def) {
        return (long) ReflectUtils.invokeQuietly(
                systemPropertiesClass(), "getLong",
                new Class[]{String.class, long.class}, new Object[]{key, def});
    }

    public static boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(@NonNull String key, boolean def) {
        return (boolean) ReflectUtils.invokeQuietly(
                systemPropertiesClass(), "getBoolean",
                new Class[]{String.class, boolean.class}, new Object[]{key, def});
    }

    public static boolean isExist(@NonNull String key) {
        return !TextUtils.isEmpty(get(key));
    }
}
