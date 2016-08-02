package com.sky.android.common.helper;

import android.view.View;
import android.widget.TextView;

import com.sky.android.common.utils.ViewUtils;

/**
 * Created by starrysky on 16-8-2.
 */
public class PromptHelper {

    private View mView;
    private TextView mPromptText;

    public PromptHelper(View view, int prompText) {

        mView = view;
        mPromptText = (TextView) view.findViewById(prompText);
    }

    private void setPromptText(int text) {
        mPromptText.setText(text);
    }

    private void setPromptText(String text) {
        mPromptText.setText(text);
    }

    public void show(int text) {
        setPromptText(text);
        ViewUtils.setVisibility(mView, View.VISIBLE);
    }

    public void show(String text) {
        setPromptText(text);
        ViewUtils.setVisibility(mView, View.VISIBLE);
    }

    public void hide() {
        ViewUtils.setVisibility(mView, View.GONE);
    }
}
