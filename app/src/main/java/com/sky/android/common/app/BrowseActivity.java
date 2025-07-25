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

package com.sky.android.common.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sky.android.common.util.ToastUtil;
import com.sky.android.core.fragment.BrowseFragment;
import com.sky.android.core.interfaces.IBrowseView;

/**
 * Created by sky on 17-2-13.
 */
public class BrowseActivity extends AppCompatActivity implements IBrowseView.BrowseListener {

    private IBrowseView mBrowseView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_browse);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String loadUrl = getIntent().getStringExtra("load_url");

        if (TextUtils.isEmpty(loadUrl)) {
            showMessage("链接不能为空");
            finish();
            return ;
        }

        // 设置浏览参数
        Bundle args = new Bundle();
        args.putString(BrowseFragment.BROWSE_URL, loadUrl);

        // 创建Fragment实例
        Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(
                getClassLoader(), BrowseFragment.class.getName()
        );
        fragment.setArguments(args);
        mBrowseView = (IBrowseView) fragment;
        mBrowseView.addBrowseListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_frame, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu_browse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (R.id.menu_open_browse == id) {
            try {
                // 从外部浏览器打开
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri contentUrl = Uri.parse(mBrowseView.getUrl());
                intent.setData(contentUrl);
                startActivity(intent);
            } catch (Throwable tr) {
                showMessage("无法从外部浏览器打开");
            }
        } else if (R.id.menu_refresh == id) {
            // 刷新界面
            mBrowseView.reload();
        } else if (R.id.menu_copy_link == id) {
            // 复制链接
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setPrimaryClip(ClipData.newPlainText(null, mBrowseView.getUrl()));
            showMessage("已复制链接");
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBrowseView = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mBrowseView.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onInitWebView(@NonNull IBrowseView webView) {
    }

    @Override
    public void onReleaseWebView(@NonNull IBrowseView webView) {
    }

    @Override
    public void onReceivedTitle(@NonNull String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
    }
}
