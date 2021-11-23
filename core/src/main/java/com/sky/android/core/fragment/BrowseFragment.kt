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

package com.sky.android.core.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import com.sky.android.common.util.Alog
import com.sky.android.common.util.ViewUtil
import com.sky.android.core.R
import com.sky.android.core.interfaces.IBrowseView
import com.sky.android.core.interfaces.IBrowseView.BrowseListener
import com.sky.android.core.util.PatchUtil.repairMediaUri
import java.util.*

/**
 * Created by sky on 2021-06-28.
 */
open class BrowseFragment : BaseFragment(), IBrowseView, DownloadListener {

    companion object {

        const val BROWSE_URL = "load_url"

        private const val FILE_CHOOSER_RESULT_CODE = 0x01

        /** 视频全屏参数  */
        val COVER_SCREEN_PARAMS = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private var mCustomView: View? = null
    private var mFullScreenContainer: FrameLayout? = null
    private var mCustomViewCallback: WebChromeClient.CustomViewCallback? = null
    private var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    private val mBrowseListeners: MutableList<BrowseListener> = ArrayList()
    private var mOriginalUrl: String? = null

    private lateinit var mRoot: ViewGroup
    private lateinit var mWebView: WebView
    private lateinit var mProgressBar: ProgressBar

    override val layoutId: Int
        get() = R.layout.fragment_browse

    override fun initView(view: View, args: Bundle?) {

        mRoot = view.findViewById(R.id.rl_root)
        mWebView = view.findViewById(R.id.web_view)
        mProgressBar = view.findViewById(R.id.progress_bar)

        // 初始化设置
        onInitCookieSettings()
        onInitWebSettings()
        onInitWebViewSettings()

        if (args != null
            && args.containsKey(BROWSE_URL)) {
            // 打开页面
            loadUrl(args.getString(BROWSE_URL)!!)
        }
    }

    override fun onResume() {
        super.onResume()
        mWebView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mWebView.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        for (listener in mBrowseListeners) {
            listener.onReleaseWebView(this)
        }

        mRoot.removeView(mWebView)
        mWebView.removeAllViews()
        mBrowseListeners.clear()
        try {
            mWebView.destroy()
        } catch (t: Throwable) {
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_RESULT_CODE && mFilePathCallback != null) {
            val result =
                if (data == null || resultCode != Activity.RESULT_OK) null else data.data
            // 主要修复小米机型的问题
            repairMediaUri(activity, result)
            if (result != null) {
                mFilePathCallback?.onReceiveValue(arrayOf(result))
            } else {
                mFilePathCallback?.onReceiveValue(arrayOf())
            }
            mFilePathCallback = null
        }
    }

    override fun getWebSettings(): WebSettings {
        return mWebView.settings
    }

    override fun getCookieManager(): CookieManager {
        return CookieManager.getInstance()
    }

    @SuppressLint("JavascriptInterface")
    override fun addJavascriptInterface(
        `object`: Any,
        name: String
    ) {
        mWebView.addJavascriptInterface(`object`, name)
    }

    override fun removeJavascriptInterface(name: String) {
        mWebView.removeJavascriptInterface(name)
    }

    override fun setWebChromeClient(client: WebChromeClient) {
        mWebView.webChromeClient = client
    }

    override fun setWebViewClient(client: WebViewClient) {
        mWebView.webViewClient = client
    }

    override fun reload() {
        mWebView.reload()
    }

    override fun loadUrl(url: String) {

        if (TextUtils.isEmpty(url)) return

        if (mOriginalUrl == null) {
            // 初始URL(第一次的时候)
            mOriginalUrl = url
        }

        // 打开url
        mWebView.loadUrl(url)
    }

    override fun loadData(
        data: String,
        mimeType: String,
        encoding: String
    ) {
        mWebView.loadData(data, mimeType, encoding)
    }

    override fun getUrl(): String {
        // 获取当前的URL
        val url = mWebView.url?: return ""
        return if (url.startsWith("file:")) mOriginalUrl?:"" else url
    }

    override fun getOriginalUrl(): String {
        return mOriginalUrl?:""
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return keyCode == KeyEvent.KEYCODE_BACK && onGoBack()
    }

    override fun addBrowseListener(listener: BrowseListener) {
        mBrowseListeners.add(listener)
    }

    override fun removeBrowseListener(listener: BrowseListener) {
        mBrowseListeners.remove(listener)
    }

    override fun getWebView(): WebView {
        return mWebView
    }

    /**
     * 初始化Cookie设置
     */
    open fun onInitCookieSettings() {
        getCookieManager().setAcceptCookie(true)
    }

    /**
     * 初始化Web设置
     */
    @SuppressLint("SetJavaScriptEnabled")
    open fun onInitWebSettings() {

        val settings = getWebSettings()

        //支持JS
        settings.javaScriptEnabled = true
        //设置适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //支持缩放
        settings.setSupportZoom(true)
        //隐藏原生的缩放控件
        settings.displayZoomControls = false
        settings.builtInZoomControls = true
        //支持内容重新布局
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

        //设置缓存模式
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCacheEnabled(true)
        settings.setAppCachePath(context!!.cacheDir.absolutePath)
        //设置可访问文件
        settings.allowFileAccess = true
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true)
        //支持自动加载图片
        settings.loadsImagesAutomatically = true
        settings.setNeedInitialFocus(true)

        //设置编码格式
        settings.defaultTextEncodingName = "UTF-8"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    open fun onInitWebViewSettings() {
        mWebView.webViewClient = DefaultWebViewClient()
        mWebView.webChromeClient = DefaultWebChromeClient()
        mWebView.setDownloadListener(this)
        for (listener in mBrowseListeners) {
            listener.onInitWebView(this)
        }
    }

    open fun onGoBack(): Boolean {
        if (mCustomView != null) { // 隐藏全屏
            hideCustomView()
            return true
        }
        if (mWebView.canGoBack()) { // 返回
            mWebView.goBack()
            return true
        }
        return false
    }

    /** 视频播放全屏  */
    private fun showCustomView(
        view: View,
        callback: WebChromeClient.CustomViewCallback
    ) {
        if (mCustomView != null) {
            callback.onCustomViewHidden()
            return
        }
        val decor = window.decorView as FrameLayout
        mFullScreenContainer = FullscreenHolder(context!!)
        mFullScreenContainer?.addView(view, COVER_SCREEN_PARAMS)
        decor.addView(mFullScreenContainer, COVER_SCREEN_PARAMS)
        mCustomView = view
        setStatusBarVisibility(false)
        mCustomViewCallback = callback
    }

    /** 隐藏视频全屏  */
    private fun hideCustomView() {
        if (mCustomView == null) return
        setStatusBarVisibility(true)
        val decor = window.decorView as FrameLayout
        decor.removeView(mFullScreenContainer)
        mFullScreenContainer = null
        mCustomView = null
        mCustomViewCallback!!.onCustomViewHidden()
        mWebView.visibility = View.VISIBLE
    }

    private fun setStatusBarVisibility(visible: Boolean) { // 设置全屏
        val flag = if (visible) 0 else WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // 设置隐藏虚拟按钮
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
        val uiOption2 =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        decorView.systemUiVisibility = if (visible) uiOption2 else uiOptions
    }

    private val window: Window
        private get() = activity!!.window

    override fun onDownloadStart(
        url: String,
        userAgent: String,
        contentDisposition: String,
        mimetype: String,
        contentLength: Long
    ) {
        // 启动外部下载
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context!!.startActivity(intent)
    }

    /** 全屏容器界面  */
    private inner class FullscreenHolder(ctx: Context) :
        FrameLayout(ctx) {
        override fun onTouchEvent(evt: MotionEvent): Boolean {
            return true
        }

        init {
            setBackgroundColor(Color.BLACK)
        }
    }

    open inner class DefaultWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedError(
            view: WebView,
            errorCode: Int,
            description: String,
            failingUrl: String
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            // 加载本地错误界面
//            loadUrl("file:///android_asset/errorpage/error.html")
        }
    }

    open inner class DefaultWebChromeClient : WebChromeClient() {

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            for (listener in mBrowseListeners) {
                listener.onReceivedTitle(title)
            }
        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress >= 100) { // 隐藏
                mProgressBar.progress = newProgress
                ViewUtil.setVisibility(mProgressBar, View.INVISIBLE)
                return
            }
            // 显示
            mProgressBar.progress = newProgress
            ViewUtil.setVisibility(mProgressBar, View.VISIBLE)
        }

        override fun getVideoLoadingProgressView(): View? {
            val frameLayout = FrameLayout(context!!)
            frameLayout.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT
            )
            return frameLayout
        }

        override fun onShowCustomView(
            view: View,
            callback: CustomViewCallback
        ) {
            showCustomView(view, callback)
        }

        override fun onHideCustomView() {
            hideCustomView()
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            try {
                mFilePathCallback = filePathCallback
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, fileChooserParams.createIntent())
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser")
                startActivityForResult(
                    chooserIntent,
                    FILE_CHOOSER_RESULT_CODE
                )
                return true
            } catch (tr: Throwable) {
                mFilePathCallback = null
                Alog.e("选择文件异常", tr)
            }
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
        }
    }
}