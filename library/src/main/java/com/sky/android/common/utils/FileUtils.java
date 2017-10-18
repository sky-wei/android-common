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

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by starrysky on 16-8-27.
 */
public class FileUtils {

    /**
     * 创建文件
     * @param file
     * @return
     */
    public static boolean createFile(File file) {

        if (file == null) return false;

        if (file.isFile()) return true;

        // 创建目录
        createDir(file.getParentFile());

        try {
            return file.createNewFile();
        } catch (IOException e) {
            Alog.e("CreateNewFile Exception", e);
        }
        return false;
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        return (file == null || !file.isFile()) ? false : file.delete();
    }

    /**
     * 创建文件目录
     * @param file
     * @return
     */
    public static boolean createDir(File file) {
        return file == null ? false : file.mkdirs();
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ioe) {
                // ignore
            }
        }
    }
}
