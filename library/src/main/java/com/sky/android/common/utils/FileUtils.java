package com.sky.android.common.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by starrysky on 16-8-27.
 */
public class FileUtils {

    public static boolean createFile(File file) {

        if (file.exists()) return true;

        // 创建目录
        createDir(file.getParentFile());

        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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
