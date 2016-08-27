package com.sky.android.common.crash;

import java.io.File;

/**
 * Created by sky on 16-8-15.
 */
public interface CrashListener {

    /**
     * 保存异常的日志。
     * @param file
     */
    void afterSaveCrash(File file);
}
