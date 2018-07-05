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

package com.sky.android.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.sky.android.common.R;

/**
 * Created by sky on 17-2-14.
 */
public class LoadingDialog extends Dialog {

    TextView tv_tip;

    private boolean cancelable = true;
    private CancelCallback mCancelCallback;

    public LoadingDialog(Context context) {
        this(context, null);
    }

    public LoadingDialog(Context context, CancelCallback cancelCallback) {
        super(context, R.style.CustomProgressDialog);
        mCancelCallback = cancelCallback;

        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        cancelable = flag;
    }

    public void setTipText(int text) {
        tv_tip.setText(text);
    }

    public void setTipText(String text) {
        tv_tip.setText(text);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == keyCode && cancelable) {

            // 关闭
            dismiss();
            if (mCancelCallback != null) mCancelCallback.cancel();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public interface CancelCallback {

        void cancel();
    }
}
