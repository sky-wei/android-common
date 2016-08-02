package com.sky.android.common.utils;

import android.view.View;

/**
 * Created by starrysky on 16-8-2.
 */
public class ViewUtils {

    public static void setVisibility(View view, int visibility) {

        if (view == null || view.getVisibility() == visibility) return ;

        view.setVisibility(visibility);
    }
}
