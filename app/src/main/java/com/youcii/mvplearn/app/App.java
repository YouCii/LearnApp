package com.youcii.mvplearn.app;

import android.app.Application;
import android.util.DisplayMetrics;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.youcii.mvplearn.constant.ConnectConfig;

import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class App extends LitePalApplication {

    private static Application application;

    private static int screenHeight, screenWidth;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        // CrashHandler.getInstance().init(this);
        LeakCanary.install(this);  // 启用LeakCanary, 正式发布时会自动失效
        Logger.addLogAdapter(new AndroidLogAdapter());

        initScreenSize();
        initOkGo();
    }

    public static Application getInstance() {
        return application;
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ConnectConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(ConnectConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(ConnectConfig.WRITE_TIME_OUT, TimeUnit.SECONDS);
        OkGo.getInstance().init(this).setOkHttpClient(builder.build());
    }

    private void initScreenSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }
}
