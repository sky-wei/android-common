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

package com.sky.android.common.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.sky.android.common.R;
import com.sky.android.common.base.BaseFragment;
import com.sky.android.common.interfaces.BrowseView;
import com.sky.android.common.util.Alog;
import com.sky.android.common.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sky on 17-8-8.
 */

public class BrowseFragment extends BaseFragment implements BrowseView, DownloadListener {

    public static final String BROWSE_URL = "load_url";

    private static final int FILE_CHOOSER_RESULT_CODE = 0x01;

    RelativeLayout rl_root;
    WebView web_view;
    ProgressBar progress_bar;

    /** 视频全屏参数 */
    private final FrameLayout.LayoutParams COVER_SCREEN_PARAMS =
            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private View mCustomView;
    private FrameLayout mFullScreenContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private ValueCallback<Uri[]> mFilePathCallback;
    private List<BrowseListener> mBrowseListeners = new ArrayList<>();
    private String mOriginalUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_browse;
    }

    @Override
    protected void initView(View view, Bundle args) {

        rl_root = view.findViewById(R.id.rl_root);
        web_view = view.findViewById(R.id.web_view);
        progress_bar = view.findViewById(R.id.progress_bar);

        // 初始化设置
        initCookieSettings();
        initWebSettings();
        initWebViewSettings();

        if (args != null) {
            // 打开页面
            loadUrl(args.getString(BROWSE_URL));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null)
            web_view.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (web_view != null)
            web_view.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (web_view != null) {

            for (BrowseListener listener : mBrowseListeners) {
                listener.onReleaseWebView(this);
            }

            rl_root.removeView(web_view);
            web_view.removeAllViews();
            mBrowseListeners.clear();

            try {
                web_view.destroy();
            } catch (Throwable t) {
            }
            web_view = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_CHOOSER_RESULT_CODE && mFilePathCallback != null) {

            Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();

            if (result != null) {
                mFilePathCallback.onReceiveValue(new Uri[] { result });
            } else {
                mFilePathCallback.onReceiveValue(new Uri[] {});
            }
            mFilePathCallback = null;
        }
    }

    @Override
    public WebSettings getWebSettings() {
        return web_view != null ? web_view.getSettings() : null;
    }

    @Override
    public CookieManager getCookieManager() {
        return CookieManager.getInstance();
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void addJavascriptInterface(Object object, String name) {
        if (web_view != null) web_view.addJavascriptInterface(object, name);
    }

    @Override
    public void removeJavascriptInterface(String name) {
        if (web_view != null) web_view.removeJavascriptInterface(name);
    }

    @Override
    public void setWebChromeClient(WebChromeClient client) {
        if (web_view != null) web_view.setWebChromeClient(client);
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        if (web_view != null) web_view.setWebViewClient(client);
    }

    @Override
    public void reload() {
        // 刷新
        if (web_view != null) web_view.reload();
    }

    @Override
    public void loadUrl(String url) {

        if (TextUtils.isEmpty(url)) return;

        if (mOriginalUrl == null) {
            // 初始URL(第一次的时候)
            mOriginalUrl = url;
        }

        // 打开url
        if (web_view != null) web_view.loadUrl(url);
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        if (web_view != null) web_view.loadData(data, mimeType, encoding);
    }

    @Override
    public String getUrl() {

        if (web_view == null) return "";

        // 获取当前的URL
        String url = web_view.getUrl();

        if (TextUtils.isEmpty(url)) return "";

        return url.startsWith("file:") ? mOriginalUrl : url;
    }

    @Override
    public String getOriginalUrl() {
        return mOriginalUrl;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && onGoBack()) {
            return true;
        }
        return false;
    }

    @Override
    public void addBrowseListener(BrowseListener listener) {
        if (listener != null) mBrowseListeners.add(listener);
    }

    @Override
    public void removeBrowseListener(BrowseListener listener) {
        if (listener != null) mBrowseListeners.remove(listener);
    }

    @Override
    public WebView getWebView() {
        return web_view;
    }

    /**
     * 初始化Cookie设置
     */
    protected void initCookieSettings() {

        getCookieManager().setAcceptCookie(true);
    }

    /**
     * 初始化Web设置
     */
    protected void initWebSettings() {

        WebSettings settings = getWebSettings();

        //支持JS
        settings.setJavaScriptEnabled(true);
        //设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //支持缩放
        settings.setSupportZoom(true);
        //隐藏原生的缩放控件
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        //支持内容重新布局
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.supportMultipleWindows();
//        settings.setSupportMultipleWindows(true);
        //设置缓存模式
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getContext().getCacheDir().getAbsolutePath());

        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }
        settings.setNeedInitialFocus(true);
        //设置编码格式
        settings.setDefaultTextEncodingName("UTF-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    protected void initWebViewSettings() {

        web_view.setWebViewClient(new DefaultWebViewClient());
        web_view.setWebChromeClient(new DefaultWebChromeClient());
        web_view.setDownloadListener(this);

        for (BrowseListener listener : mBrowseListeners) {
            listener.onInitWebView(this);
        }
    }

    protected boolean onGoBack() {

        if (mCustomView != null) {
            // 隐藏全屏
            hideCustomView();
            return true;
        } if (web_view.canGoBack()) {
            // 返回
            web_view.goBack();
            return true;
        }
        return false;
    }

    /** 视频播放全屏 **/
    private void showCustomView(View view, WebChromeClient.CustomViewCallback callback) {
        // if a view already exists then immediately terminate the new one
        if (mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }

        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        mFullScreenContainer = new FullscreenHolder(getContext());
        mFullScreenContainer.addView(view, COVER_SCREEN_PARAMS);
        decor.addView(mFullScreenContainer, COVER_SCREEN_PARAMS);
        mCustomView = view;
        setStatusBarVisibility(false);
        mCustomViewCallback = callback;
    }

    /** 隐藏视频全屏 */
    private void hideCustomView() {

        if (mCustomView == null) return ;

        setStatusBarVisibility(true);
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        decor.removeView(mFullScreenContainer);
        mFullScreenContainer = null;
        mCustomView = null;
        mCustomViewCallback.onCustomViewHidden();
        web_view.setVisibility(View.VISIBLE);
    }

    private void setStatusBarVisibility(boolean visible) {
        // 设置全屏
        int flag = visible ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 设置隐藏虚拟按钮
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(visible ? 0 : uiOptions);
    }

    private Window getWindow() {
        return getActivity().getWindow();
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

        if (url == null) return ;

        // 启动外部下载
        Uri uri= Uri.parse(url);
        Intent intent=new Intent(Intent.ACTION_VIEW, uri);
        getContext().startActivity(intent);
    }

    /** 全屏容器界面 */
    private final class FullscreenHolder extends FrameLayout {

        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
        }

        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    public class DefaultWebViewClient extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            // 加载本地错误界面
            loadUrl("file:///android_asset/errorpage/error.html");
        }
    }

    public class DefaultWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            for (BrowseListener listener : mBrowseListeners) {
                listener.onReceivedTitle(title);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

            if (newProgress >= 100) {
                // 隐藏
                progress_bar.setProgress(newProgress);
                ViewUtil.setVisibility(progress_bar, View.INVISIBLE);
                return ;
            }

            // 显示
            progress_bar.setProgress(newProgress);
            ViewUtil.setVisibility(progress_bar, View.VISIBLE);
        }

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            showCustomView(view, callback);
        }

        @Override
        public void onHideCustomView() {
            hideCustomView();
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {

            try {
                mFilePathCallback = filePathCallback;
                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, fileChooserParams.createIntent());
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "File Chooser");
                startActivityForResult(chooserIntent, FILE_CHOOSER_RESULT_CODE);
                return true;
            } catch (Throwable tr) {
                mFilePathCallback = null;
                Alog.e("选择文件异常", tr);
            }
            return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }
    }
}
