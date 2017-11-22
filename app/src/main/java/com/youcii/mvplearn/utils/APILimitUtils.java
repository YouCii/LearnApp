package com.youcii.mvplearn.utils;

import android.content.Context;
import android.os.Build;

/**
 * Created by YouCii on 2016/8/18.
 */

public class APILimitUtils {

    public static int getColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorId);
        } else {
            return context.getResources().getColor(colorId, null);
        }
    }

}
