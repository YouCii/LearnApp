package com.youcii.mvplearn.greendao

import com.youcii.mvplearn.utils.GsonUtils
import org.greenrobot.greendao.converter.PropertyConverter
import java.lang.reflect.ParameterizedType

/**
 * Created by jdw on 2018/4/18.
 */
open class BaseBeanConverter<T> : PropertyConverter<T, String> {

    override fun convertToDatabaseValue(entityProperty: T?): String {
        return GsonUtils.bean2Json(entityProperty)
    }

    override fun convertToEntityProperty(databaseValue: String?): T {
        return GsonUtils.json2Bean(databaseValue, getTClass())
    }

    private fun getTClass(): Class<T> {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

}