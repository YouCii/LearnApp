package com.youcii.mvplearn.app;

import com.orhanobut.logger.Logger;

import org.litepal.LitePalApplication;

/**
 * Created by YouCii on 2016/7/15.
 */
public class App extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        CrashHandler.getInstance().init(this);
        Logger.init("Logger");

    }

}
