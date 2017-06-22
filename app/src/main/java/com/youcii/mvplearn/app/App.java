package com.youcii.mvplearn.app;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

import org.litepal.LitePalApplication;

/**
 * Created by YouCii on 2016/7/15.
 */
public class App extends LitePalApplication {

	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();

		context = getApplicationContext();

		CrashHandler.getInstance().init(this);
		Logger.init("Logger");

		OkGo.init(this);
		OkGo.getInstance().setConnectTimeout(1000);
	}

	public static Context getInstance() {
		return context;
	}

}
