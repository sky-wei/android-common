package com.sky.android.core.interfaces;

import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by sky on 11/28/20.
 */
public interface OnItemEventListener {

    void onItemEvent(int event, @NonNull View view, int position, Object... args);
}
