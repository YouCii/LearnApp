package com.youcii.mvplearn.model

import android.os.Parcel
import android.os.Parcelable


/**
 * Created by jdw on 2018/6/6.
 *
 * 没有使用 Parcelize注解, 使用aidl的时候有bug, 提示不能转化createFromParcel为对应参数, 所以没有再用
 */
data class ServiceData(var ip: String = "", private var port: Int = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt())

    fun readFromParcel(parcel: Parcel) {
        ip = parcel.readString()
        port = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ip)
        parcel.writeInt(port)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ServiceData> {
        override fun createFromParcel(parcel: Parcel): ServiceData {
            return ServiceData(parcel)
        }

        override fun newArray(size: Int): Array<ServiceData?> {
            return arrayOfNulls(size)
        }
    }
}