package com.youcii.mvplearn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class H5Activity extends BaseActivity {

    @Bind(R.id.webView)
    BridgeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5);
        ButterKnife.bind(this);

        webSetting();
        webView.loadUrl("file:///android_asset/NativeWeb.html");
    }

    private void webSetting() {
        WebSettings mWebSettings = webView.getSettings();
        // mWebSettings.setJavaScriptEnabled(true);     // 设置WebView属性，能够执行JavaScript脚本
        // mWebSettings.setSupportZoom(true);           // 设置可以支持缩放
        // mWebSettings.setBuiltInZoomControls(true);   // 设置出现缩放工具
        mWebSettings.setUseWideViewPort(true);          // 为图片添加放大缩小功能
        mWebSettings.setLoadWithOverviewMode(true);     // 缩放至屏幕的大小
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, H5Activity.class));
    }

}
