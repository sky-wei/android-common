package com.sky.android.common.app;

import android.app.Application;

import com.sky.android.common.crash.CrashHandler;
import com.sky.android.common.crash.CrashListener;

import java.io.File;

/**
 * Created by starrysky on 16-8-27.
 */
public class SkyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler crashHandler = new CrashHandler(this, new CrashListener() {
            @Override
            public void afterSaveCrash(File file) {

                System.out.println(">>>>>>>>>>>>>>>> " + file);
            }
        });
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }
}
