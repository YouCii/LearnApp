package com.youcii.mvplearn.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * @author YouCii
 * @date 2016/7/18
 */
public class PhoneUtils {

    /**
     * 获取电话号码
     */
    public static String getNativePhoneNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        } else {
            return tm.getLine1Number();
        }
    }

    /**
     * 判断手机是否联网
     */
    public static int isOnLine(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // 有网
        if (networkInfo != null && networkInfo.isConnected()) {
            return 1;
        } else {
            // 没网
            return 0;
        }
    }

}
