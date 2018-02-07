package com.youcii.mvplearn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.activity.interfaces.IHttpTestView;
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.presenter.HttpTestPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HttpTestActivity extends BaseActivity implements IHttpTestView {

	@Bind(R.id.request_path)
	EditText requestPath;
	@Bind(R.id.ll_http_params)
	LinearLayout llHttpParams;
	@Bind(R.id.request_type_choice)
	RadioGroup requestTypeChoice;
	@Bind(R.id.http_callback)
	TextView httpCallback;

	@Bind(R.id.server_ip)
	EditText serverIp;
	@Bind(R.id.server_port)
	EditText serverPort;
	@Bind(R.id.server_name)
	EditText serverName;
	@Bind(R.id.setting_time)
	EditText settingTime;
	@Bind(R.id.setting_frequency)
	EditText settingFrequency;

	private HttpTestPresenter httpTestPresenter;
	private List<View> keyView = new ArrayList<>(), valueView = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 代码设置隐藏ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_http_test);
		ButterKnife.bind(this);

		httpTestPresenter = new HttpTestPresenter(this);
	}

	@Override
	protected boolean isLandscape() {
		return true;
	}

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, HttpTestActivity.class);
		context.startActivity(intent);
	}

	@OnClick({R.id.http_add_param, R.id.test_start})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.http_add_param:
				addParam();
				break;
			case R.id.test_start:
				httpTestPresenter.startRequest();
				break;
			default:
				break;
		}
	}

	private int i = 0;

	@Override
	public void doOnCallBack(String content) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				httpCallback.setText(++i + "\n" + content);
			}
		});
	}

	@Override
	public void addParam() {
		View view = getLayoutInflater().inflate(R.layout.include_key_value, null);
		// 整改照片列表
		llHttpParams.addView(view);

		keyView.add(view.findViewById(R.id.include_key));
		valueView.add(view.findViewById(R.id.include_value));
	}

	@Override
	public String getUrl() {
		String url = "http://";
		url += serverIp.getText().toString();
		url += ":" + serverPort.getText().toString();
		url += "/" + serverName.getText().toString();
		url += "/" + requestPath.getText().toString();
		return url;
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> map = new HashMap<>(5);
		for (int i = 0; i < keyView.size(); i++) {
			String key = ((EditText) keyView.get(i)).getText().toString();
			if (!"".equals(key)) {
				map.put(key, ((EditText) valueView.get(i)).getText().toString());
			}
		}
		return map;
	}

	@Override
	public boolean isPostRequest() {
		return requestTypeChoice.getCheckedRadioButtonId() == R.id.request_type_post;
	}

	@Override
	public int getRequestTime() {
		return Integer.parseInt(settingTime.getText().toString());
	}

	@Override
	public int getRequestFrequency() {
		return Integer.parseInt(settingFrequency.getText().toString());
	}

}
