package com.youcii.mvplearn.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.FileConvert;
import com.youcii.mvplearn.R;
import com.youcii.mvplearn.app.App;
import com.youcii.mvplearn.utils.GsonUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author Administrator
 * @date 2017/4/25
 */
public abstract class BaseCallBack<T> extends AbsCallback<T> {
	protected Context context;
	private String destFileDir;
	private String destFileName;

	public BaseCallBack(Context context) {
		this(context, null, null);
	}

	public BaseCallBack(Context context, String destFileDir, String destFileName) {
		this.context = context;
		this.destFileDir = destFileDir;
		this.destFileName = destFileName;
	}

	@Override
	public void onSuccess(T t, Call call, Response response) {
		if (t == null) {
			onError(call, response, new Exception("网络请求失败"));
			return;
		} else if (t instanceof Exception) {
			onError(call, response, (Exception) t);
			return;
		}
		onSuccess(t);
		response.close();
	}

	protected abstract void onSuccess(T t);

	@Override
	public T convertSuccess(Response response) {
		Class<?> cls = getTClass();
		if (cls == BaseResponse.class) {
			String json;
			try {
				json = response.body().string();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			Object responseObject = GsonUtils.json2Bean(json, getTClass());
			if (!App.getInstance().getString(R.string.success).equals(((BaseResponse) responseObject).status)) {
				return (T) new Exception("网络请求失败：" + ((BaseResponse) responseObject).msg);
			}
			return (T) responseObject;
		} else if (cls == String.class) {
			try {
				return (T) response.body().string();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else if (cls == Bitmap.class) {
			return (T) BitmapFactory.decodeStream(response.body().byteStream());
		} else if (cls == File.class) {
			return (T) convertToFile(response);
		}
		return null;
	}

	@Override
	public void onError(@Nullable Call call, Response response, Exception e) {
		super.onError(call, response, e);

		if (response.body().toString().contains("ConnectTimeoutException")) {
			Toast.makeText(context, "服务器超时无响应", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 反射获取T.class
	 */
	private Class<T> getTClass() {
		Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return tClass;
	}

	/**
	 * 返回file
	 */
	private File convertToFile(Response response) {
		FileConvert convert = new FileConvert(destFileDir, destFileName);
		convert.setCallback(this);
		File file = null;
		try {
			file = convert.convertSuccess(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
}
