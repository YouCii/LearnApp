package com.youcii.mvplearn.app;

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

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);
        LeakCanary.install(this);  // 启用LeakCanary, 正式发布时会自动失效
        Logger.addLogAdapter(new AndroidLogAdapter());

        initOkGo();
    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(ConnectConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(ConnectConfig.READ_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(ConnectConfig.WRITE_TIME_OUT, TimeUnit.SECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build());
    }
}
