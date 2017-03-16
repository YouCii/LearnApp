package com.tianyuan.mvplearn.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by YouCii on 2016/7/18.
 */
public class GsonUtils {

    /* 把对象转化成json字符串 */
    public static String bean2Json(Object bean) {
        return new Gson().toJson(bean);
    }

    /* 把json字符串转化为对象 */
    public static <T> T json2Bean(String json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    /* 把json字符串转化为 List */
    public static <T> List<T> json2List(String json) {
        Type type = new TypeToken<List<T>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    /* 把json字符串转化为 Map */
    public static <T> Map<String, T> json2Map(String json) {
        Type type = new TypeToken<Map<String, T>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }

    /* 把json字符串转化为 List<Map> */
    public static <T> List<Map<String, T>> json2ListMap(String json) {
        Type type = new TypeToken<List<Map<String, T>>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }
}
