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

package com.sky.android.common.interfaces;

import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sky on 17-8-8.
 */

public interface BrowseView {

    WebView getWebView();

    WebSettings getWebSettings();

    CookieManager getCookieManager();

    void addJavascriptInterface(Object object, String name);

    void removeJavascriptInterface(String name);

    void setWebChromeClient(WebChromeClient client);

    void setWebViewClient(WebViewClient client);

    void loadUrl(String url);

    void loadData(String data, String mimeType, String encoding);

    void reload();

    String getUrl();

    String getOriginalUrl();

    boolean onKeyDown(int keyCode, KeyEvent event);

    void addBrowseListener(BrowseListener listener);

    void removeBrowseListener(BrowseListener listener);

    interface BrowseListener {

        void onInitWebView(BrowseView view);

        void onReleaseWebView(BrowseView view);

        void onReceivedTitle(String title);
    }
}
