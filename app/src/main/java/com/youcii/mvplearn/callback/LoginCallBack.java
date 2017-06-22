package com.youcii.mvplearn.callback;

import android.content.Context;
import com.youcii.mvplearn.base.BaseCallBack;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/6/21.
 */

public class LoginCallBack extends BaseCallBack<String> {

	public LoginCallBack(Context context) {
		super(context);
	}

	@Override
	protected void onSuccess(String string) {
		EventBus.getDefault().post("登陆成功");
	}
}
