package com.youcii.mvplearn.utils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.lang.reflect.Type;

/**
 * Created by jdw on 2016/7/18.
 */
public class GsonUtils {

    /**
     * 把对象转化成json字符串
     */
    public static String bean2Json(Object bean) {
        return new Gson().toJson(bean);
    }

    /**
     * 把json字符串转化为对象
     */
    public static <T> T json2Bean(String json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    /**
     * 把json字符串转化为对象
     */
    public static <T> T json2Bean(JsonReader jsonReader, Class<T> cls) {
        return new Gson().fromJson(jsonReader, cls);
    }

    /**
     * 把json字符串转化为List或Map
     * <p>
     * List<R>
     * Map<String, R>
     * List<Map<String, R>>
     * <p>
     * 多次尝试, 只能传入TypeToken的匿名子类
     * new TypeToken<List<R>>() {
     * }.getType()
     */
    public static <T> T json2Collection(String json, Type type) {
        return new Gson().fromJson(json, type);
    }
}

