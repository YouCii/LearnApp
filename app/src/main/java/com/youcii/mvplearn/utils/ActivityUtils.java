package com.youcii.mvplearn.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by YouCii on 2016/8/11.
 */
public class ActivityUtils {

    // 判断隐式启动的Activity是否存在
    public static boolean isIntentExit(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
            if (packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null)
                return true;
        return false;
    }

}
