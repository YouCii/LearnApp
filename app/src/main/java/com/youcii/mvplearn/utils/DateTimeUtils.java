package com.youcii.mvplearn.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by YouCii on 2016/7/18.
 */
public class DateTimeUtils {

    /* 获取当前时间 */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
    }
}
