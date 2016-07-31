package com.sky.android.common.utils;

import android.util.Log;

/**
 * Created by starrysky on 16-7-31.
 *
 * 日志输出类
 */
public class Alog {

    public final static String TAG = Alog.class.getSimpleName();

    private static boolean debug = true;

    public static boolean isDebug() {
        return Alog.debug;
    }

    public void setDebug(boolean debug) {
        Alog.debug = debug;
    }

    public static void i(String msg) {
        if (debug) i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (debug) Log.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (debug) Log.i(tag, msg, tr);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (debug) Log.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (debug) Log.d(tag, msg, tr);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(tag, msg, tr);
    }

    public static void w(String msg) {
        if (debug) w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (debug) Log.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (debug) Log.w(tag, msg, tr);
    }
}
