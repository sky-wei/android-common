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

package com.sky.android.common.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.sky.android.common.util.Alog;

/**
 * Created by sky on 17-7-11.
 */

public class ServiceHelper {

    private static final String TAG = "ServiceHelper";

    public static final int SERVICE_CONNECTED = 0x101;
    public static final int SERVICE_DISCONNECTED = 0x102;

    private Context mContext;
    private ServiceConnection mServiceConnection;
    private IBinder mService;
    private ServiceListener mServiceListener;

    public ServiceHelper(Context context, ServiceListener listener) {
        mContext = context;
        mServiceListener = listener;
    }

    /**
     * 绑定服务
     */
    public void bindService(Intent intent) {

        if (mServiceConnection != null
                || intent == null) {
            return ;
        }

        try {
            // 绑定服务
            mServiceConnection = new VServiceConnection();
            mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        } catch (Exception e) {
            Alog.e(TAG, "绑定服务异常", e);
        }
    }

    /**
     * 解绑服务
     */
    public void unbindService() {

        if (mServiceConnection == null) return ;

        try {
            // 解绑服务
            mContext.unbindService(mServiceConnection);
            mServiceConnection = null;
        } catch (Exception e) {
            Alog.e(TAG, "解绑服务异常", e);
        }
    }

    public boolean isServiceContent() {
        return mService != null;
    }

    public IBinder getService() {
        return mService;
    }

    void onHandleMessage(Message msg) {

        if (SERVICE_CONNECTED == msg.what) {
            // 连接成功
            mServiceListener.onServiceConnected(this, mService);
        } else if (SERVICE_DISCONNECTED == msg.what) {
            // 连接失败
            mServiceListener.onServiceDisconnected(this);
        }
    }

    protected final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            onHandleMessage(msg);
        }
    };

    /**
     * 服务连接处理类
     */
    private final class VServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mService = service;

            // 发送连接成功消息
            mHandler.sendEmptyMessage(SERVICE_CONNECTED);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mService = null;
            mServiceConnection = null;

            // 发送连接失败的消息
            mHandler.sendEmptyMessage(SERVICE_DISCONNECTED);
        }
    }

    public interface ServiceListener {

        void onServiceConnected(ServiceHelper serviceHelper, IBinder service);

        void onServiceDisconnected(ServiceHelper serviceHelper);
    }
}
