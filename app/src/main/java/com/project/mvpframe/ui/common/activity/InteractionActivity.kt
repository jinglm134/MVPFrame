package com.project.mvpframe.ui.common.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.http.SslError
import android.os.Build
import android.view.View
import android.webkit.*
import com.project.mvpframe.R
import com.project.mvpframe.base.BaseActivity
import com.project.mvpframe.base.BasePresenter
import com.project.mvpframe.ui.user.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_interaction.*
import org.json.JSONObject

/**
 * @CreateDate 2019/12/16 10:21
 * @Author jaylm
 */

class InteractionActivity : BaseActivity<BasePresenter<*, *>>() {

    override fun initMVP() {
    }

    override fun bindLayout(): Int {
        return R.layout.activity_interaction
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("DEPRECATION")
    override fun initView(contentView: View) {

        val settings = webView.settings
        settings.domStorageEnabled = true
        settings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        settings.allowFileAccess = true
        settings.setAppCacheEnabled(true)

        webView.isHorizontalScrollBarEnabled = false//水平滚动条不显示
        webView.isVerticalScrollBarEnabled = false //垂直滚动条不显示
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        settings.blockNetworkImage = false

        /*设置编码方式*/
        settings.defaultTextEncodingName = "utf-8"
        /*设置支持js*/
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        /*设置支持缩放*/
        settings.setSupportZoom(true)
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        webView.webViewClient = ReWebViewClient()
        webView.webChromeClient = ReWebChromeClient()

        webView.addJavascriptInterface(WebJsCall(), "android")

        val title = intent.getStringExtra("title")
        setHeader(title)
        val url = intent.getStringExtra("url")
        webView.loadUrl(url)
    }

    internal inner class ReWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            view.evaluateJavascript("javascript:\$Vue.init()") { }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
            super.onReceivedSslError(view, handler, error)
        }
    }

    inner class ReWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                progressBar.visibility = View.GONE//加载完网页进度条消失
            } else {
                progressBar.visibility = View.VISIBLE
                progressBar.progress = newProgress//设置进度值
            }
            super.onProgressChanged(view, newProgress)

        }
    }

    private inner class WebJsCall {
        @JavascriptInterface
        fun onMessage(type: String): String {
            val intent = Intent()
            when (type) {
                "initData", "init" -> {
                    //初始化传递用户信息给web端
                    val jsonObject = JSONObject()
                    jsonObject.put("userToken", "userToken")
                    jsonObject.put("userInfo", "userInfo")
                    jsonObject.toString()
                }
                "login" -> {
                    //去登录
                    intent.setClass(mActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                }
            }
            return ""
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.stopLoading()
        webView.settings.javaScriptEnabled = false
        webView.clearHistory()
        webView.removeAllViews()
        webView.destroy()
    }
}