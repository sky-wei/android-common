/*
 * Copyright (c) 2021 The sky Authors.
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

package com.sky.android.core.interfaces

import android.view.KeyEvent
import android.webkit.*

/**
 * Created by sky on 2021-06-28.
 */
interface IBrowseView {

    fun getWebView(): WebView

    fun getWebSettings(): WebSettings

    fun getCookieManager(): CookieManager

    fun addJavascriptInterface(`object`: Any, name: String)

    fun removeJavascriptInterface(name: String)

    fun setWebChromeClient(client: WebChromeClient)

    fun setWebViewClient(client: WebViewClient)

    fun loadUrl(url: String)

    fun loadData(
        data: String,
        mimeType: String,
        encoding: String
    )

    fun reload()

    fun getUrl(): String

    fun getOriginalUrl(): String

    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean

    fun addBrowseListener(listener: BrowseListener)

    fun removeBrowseListener(listener: BrowseListener)

    interface BrowseListener {

        fun onInitWebView(view: IBrowseView)

        fun onReleaseWebView(view: IBrowseView)

        fun onReceivedTitle(title: String)
    }
}