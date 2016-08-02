package com.sky.android.common.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:// 点击返回图标事件
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Context getContext() {
        return this;
    }
}
