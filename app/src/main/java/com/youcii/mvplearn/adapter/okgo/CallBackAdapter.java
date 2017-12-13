package com.youcii.mvplearn.adapter.okgo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.model.Response;
import com.youcii.mvplearn.utils.GsonUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * CallBack 适配器
 *
 * @author Administrator
 * @date 2017/4/25
 * <p>
 * 用于承接网络访问框架与逻辑层, 避免更换框架时的大幅改动
 */
public class CallBackAdapter<T> extends AbsCallback<T> {
    private final static String TAG = "CallBackAdapter";
    private String destFileDir;
    private String destFileName;

    public CallBackAdapter() {
        this(null, null);
    }

    public CallBackAdapter(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
    }

    @Override
    public final T convertResponse(okhttp3.Response response) {
        T t = null;
        Class<T> cls = getTClass();
        try {
            if (cls == String.class) {
                t = (T) response.body().string();
            } else if (cls == Bitmap.class) {
                t = (T) BitmapFactory.decodeStream(response.body().byteStream());
            } else if (cls == File.class) {
                t = (T) new FileConvert(destFileDir, destFileName).convertResponse(response);
            } else {
                String json = response.body() == null ? "" : response.body().string();
                t = GsonUtils.json2Bean(json, cls);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.toString());
        }
        response.close();
        return t;
    }

    /**
     * 反射获取T.class
     * <p>
     * 测试没有问题, 哪怕Response有多层继承
     */
    private Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    /**
     * 替代三方包的回调暴漏给业务层
     */
    public void onSuccess(T t) {
    }

    /**
     * 替代三方包的回调暴漏给业务层
     */
    public void onError(String errorInfo) {
    }

    @Override
    public final void onSuccess(Response<T> response) {
        if (response.isSuccessful()) {
            T t = response.body();
            if (t != null) {
                onSuccess(t);
            } else {
                onError("回调格式错误");
            }
        } else {
            onError(response.message());
        }
    }

    @Override
    public final void onError(Response<T> response) {
        super.onError(response);
        onError(response.message());
    }
}
