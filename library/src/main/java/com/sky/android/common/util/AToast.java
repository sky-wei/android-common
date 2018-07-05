/*
 * Copyright (c) 2018 The sky Authors.
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

package com.sky.android.common.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by sky on 18-2-5.
 */

public class AToast {

    private final static AToast A_TOAST = new AToast();
    private Context mContext;
    private int mGravity = -1;

    private AToast() {
    }

    public AToast init(Context context) {
        if (mContext == null) {
            mContext = context.getApplicationContext();
        }
        return this;
    }

    public AToast setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    public int getGravity() {
        return mGravity;
    }

    public void showMessage(CharSequence text, int duration) {
        Toast toast = Toast.makeText(mContext, text, duration);
        if (mGravity != -1) toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void showMessage(int resId, int duration) {
        showMessage(mContext.getString(resId), duration);
    }

    public static AToast getInstance() {
        return A_TOAST;
    }

    public static void show(CharSequence text) {
        A_TOAST.showMessage(text, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence text, int duration) {
        A_TOAST.showMessage(text, duration);
    }

    public static void show(int resId) {
        A_TOAST.showMessage(resId, Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        A_TOAST.showMessage(resId, duration);
    }
}
