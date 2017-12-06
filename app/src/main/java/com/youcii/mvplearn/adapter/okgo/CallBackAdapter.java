package com.youcii.mvplearn.adapter.okgo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.model.Response;
import com.youcii.mvplearn.R;
import com.youcii.mvplearn.app.App;
import com.youcii.mvplearn.base.BaseResponse;
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
    public T convertResponse(okhttp3.Response response) {
        T t = null;
        Class<?> cls = getTClass();
        try {
            if (cls == BaseResponse.class) {
                String json = response.body().string();
                Object responseObject = GsonUtils.json2Bean(json, getTClass());
                if (!App.getInstance().getString(R.string.success).equals(((BaseResponse) responseObject).status)) {
                    t = (T) new Exception("网络请求失败：" + ((BaseResponse) responseObject).msg);
                } else {
                    t = (T) responseObject;
                }
            } else if (cls == String.class) {
                t = (T) response.body().string();
            } else if (cls == Bitmap.class) {
                t = (T) BitmapFactory.decodeStream(response.body().byteStream());
            } else if (cls == File.class) {
                t = (T) new FileConvert(destFileDir, destFileName).convertResponse(response);
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
     * 反射获取T.class TODO 有问题
     */
    private Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

    /**
     * 替代三方包的回调暴漏给业务层
     */
    public void onSuccess(ResponseAdapter<T> response) {
    }

    /**
     * 替代三方包的回调暴漏给业务层
     */
    public void onError(ResponseAdapter<T> response) {
    }

    @Override
    public final void onSuccess(Response<T> response) {
        onSuccess(new ResponseAdapter<>(response));
    }

    @Override
    public final void onError(Response<T> response) {
        super.onError(response);
        onError(new ResponseAdapter<>(response));
    }
}
