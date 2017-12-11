package com.youcii.mvplearn.model

import com.youcii.mvplearn.base.BaseResponse

/**
 * Created by jdw on 2017/12/4.
 */
class IpQueryResponse : BaseResponse() {

    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * province : 北京市
     * city : 北京市
     * adcode : 110000
     * rectangle : 116.0119343,39.66127144;116.7829835,40.2164962
     */

    var info = ""
    var infocode = ""
    var province = ""
    var city = ""
    var adcode = ""
    var rectangle = ""

    override fun toString(): String {
        return "IpQueryResponse\n\ninfo='$info', \ninfocode='$infocode', \nprovince='$province', \ncity='$city', \nadcode='$adcode', \nrectangle='$rectangle'"
    }

}