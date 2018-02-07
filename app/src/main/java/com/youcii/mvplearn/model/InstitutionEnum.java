package com.youcii.mvplearn.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * java 中的 IntDef
 * <p>
 * 可用于替代Enum, 但要想像Enum一样 暂存数据或者提供各种方法, 还需另写工具类, 比较麻烦
 * flag=true时, 可以使用 & | 等操作符
 */
@IntDef(flag = false, value = {InstitutionEnum.SHOP, InstitutionEnum.HOTEL, InstitutionEnum.BANK})
@Retention(RetentionPolicy.CLASS)
public @interface InstitutionEnum {

    long SHOP = 0L;
    long HOTEL = 1L;
    long BANK = 1L << 1;

}
