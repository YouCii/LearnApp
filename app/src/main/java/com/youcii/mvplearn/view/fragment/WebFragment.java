package com.youcii.mvplearn.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.view.fragment.interfaces.IFragWebView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YouCii on 2016/8/12.
 */
public class WebFragment extends Fragment implements IFragWebView, View.OnClickListener {

    @Bind(R.id.webview)
    WebView webView;
    @Bind(R.id.nativeNum)
    TextView nativeNum;
    @Bind(R.id.numAdd)
    Button numAdd;
    @Bind(R.id.numDec)
    Button numDec;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    public void onStart() {
        super.onStart();

        numAdd.setOnClickListener(this);
        numDec.setOnClickListener(this);

        webSettings(webView);
        webView.loadUrl("file:///android_asset/android.html");
        webView.addJavascriptInterface(new jsInterface(), "Android");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private class jsInterface {

        jsInterface() {

        }

        @JavascriptInterface
        public void addNativeNum() {
            nativeNum.setText((Integer.parseInt(nativeNum.getText().toString()) + 1) + "");
        }

        @JavascriptInterface
        public void decNativeNum() {
            nativeNum.setText((Integer.parseInt(nativeNum.getText().toString()) - 1) + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.numAdd:
                webView.loadUrl("javascript:addHtmlNum()");
                break;
            case R.id.numDec:
                webView.loadUrl("javascript:decHtmlNum()");
                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void webSettings(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    // 支持javascript
        settings.setSupportZoom(false);          // 设置是否支持缩放
        settings.setBuiltInZoomControls(false);  // 设置是否支持缩放
        settings.setDisplayZoomControls(false); // 设置出现缩放工具

        /* 自动自适应屏幕 */
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//自适应屏幕
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);      // 扩大比例的缩放
    }

}
