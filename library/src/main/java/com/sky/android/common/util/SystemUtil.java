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

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by sky on 18-7-5.
 */
public class SystemUtil {

    public static void killMyProcess() {
        killProcess(android.os.Process.myPid());
    }

    public static void killProcess(int pid){
        android.os.Process.killProcess(pid);
    }

    public static void killMyApp() {
        // 关闭
        SystemUtil.killMyProcess();
        System.exit(0);
    }

    public static String getSystemProp(String key) {
        return getSystemProp(key, "");
    }

    public static String getSystemProp(String key, String defaultValue) {
        return (String) ReflectUtil.invokeQuietly(
                ReflectUtil.classForName("android.os.SystemProperties"), "get",
                new Class[]{String.class, String.class}, new Object[]{key, defaultValue});
    }

    /**
     * 执行指定的命令
     * @param cmd
     */
    public static ExecResult exec(String cmd) {

        if (TextUtils.isEmpty(cmd)) {
            throw new NullPointerException("执行任务不能为空");
        }

        Process process = null;
        BufferedReader inputStream = null;
        BufferedReader errorStream = null;

        ExecResult execResult = new ExecResult();

        try {
            // 执行pm install命令
            process = Runtime.getRuntime().exec(cmd);

            inputStream = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            errorStream = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String line;
            StringBuilder inputString = new StringBuilder();
            StringBuilder errorString = new StringBuilder();

            // 获取返回结果状态
            execResult.result = process.waitFor();

            while ((line = inputStream.readLine()) != null)
                inputString.append(line);

            while ((line = errorStream.readLine()) != null)
                errorString.append(line);

            execResult.errorMsg = errorString.toString();
            execResult.successMsg = inputString.toString();
        } catch (Throwable e) {
            Alog.e("执行命令异常", e);
            execResult.errorMsg = e.getMessage();
        } finally {
            FileUtil.closeQuietly(errorStream);
            FileUtil.closeQuietly(inputStream);
            if (process != null) process.destroy();
        }
        return execResult;
    }

    public static class ExecResult {

        public int result = -1;
        public String errorMsg;
        public String successMsg;

        @Override
        public String toString() {
            return "ExecResult{" +
                    "result=" + result +
                    ", errorMsg='" + errorMsg + '\'' +
                    ", successMsg='" + successMsg + '\'' +
                    '}';
        }
    }
}
