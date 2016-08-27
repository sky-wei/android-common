package com.sky.android.common.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by starrysky on 16-8-20.
 */
public class ItemLyaout extends RelativeLayout implements View.OnClickListener {

    private OnClickListener mOnClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;

    public ItemLyaout(Context context) {
        super(context);
        init();
    }

    public ItemLyaout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemLyaout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        super.setOnClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mOnClickListener = l;
    }

    @Override
    public boolean hasOnClickListeners() {
        return mOnClickListener != null;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener l) {
        mOnItemSelectedListener = l;
    }

    public boolean hasOnItemSelectedListeners() {
        return mOnItemSelectedListener != null;
    }

    @Override
    public void onClick(View v) {
        if (!isSelected()) setSelected(true);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);

        if (hasOnItemSelectedListeners()) {
            mOnItemSelectedListener.onItemSelected(this, selected);
        }
    }

    public interface OnItemSelectedListener {

        void onItemSelected(View view, boolean selected);
    }
}
