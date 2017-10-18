/*
 * Copyright (c) 2017 The sky Authors.
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

package com.sky.android.common.utils;

import android.util.Log;

/**
 * Created by starrysky on 16-7-31.
 *
 * 日志输出类
 */
public class Alog {

    public final static String TAG = Alog.class.getSimpleName();

    private static boolean DEBUG = false;

    public static boolean isDEBUG() {
        return Alog.DEBUG;
    }

    public static void setDEBUG(boolean DEBUG) {
        Alog.DEBUG = DEBUG;
    }

    public static void i(String msg) {
        if (DEBUG) i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (DEBUG) Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) Log.i(tag, msg, tr);
    }

    public static void d(String msg) {
        if (DEBUG) d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (DEBUG) Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) Log.d(tag, msg, tr);
    }

    public static void e(String msg) {
        if (DEBUG) e(TAG, msg);
    }

    public static void e(String msg, Throwable tr) {
        if (DEBUG) e(TAG, msg, tr);
    }

    public static void e(String tag, String msg) {
        if (DEBUG) Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) Log.e(tag, msg, tr);
    }

    public static void w(String msg) {
        if (DEBUG) w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (DEBUG) Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) Log.w(tag, msg, tr);
    }
}
