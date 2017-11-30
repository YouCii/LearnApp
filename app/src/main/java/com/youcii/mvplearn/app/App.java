package com.youcii.mvplearn.app;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import org.litepal.LitePalApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class App extends LitePalApplication {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        CrashHandler.getInstance().init(this);
        Logger.init("Logger");

        initOkGo();
    }

    public static Context getInstance() {
        return context;
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build());
    }
}
