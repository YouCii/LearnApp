package com.youcii.mvplearn.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.youcii.mvplearn.app.App;

/**
 * @author YouCii
 * @date 2016/7/18
 */
public class PhoneUtils {

    /**
     * 判断手机是否联网
     */
    public static boolean isOnLine() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
