package com.youcii.mvplearn.constant

class UrlConstant {
    companion object {
        /**
         * 阿里云的地址
         *
         * 注: url格式为 scheme://host[:port]/path[?query]
         *      作为Retrofit的BASE_URL时, 应注意如果存在 path 部分, 必须以/结尾
         */
        const val BASE_URL_ALI = "http://iploc.market.alicloudapi.com/"
        const val BASE_URL_DOU_BAN = "https://api.douban.com/"
        const val BASE_URL_KEY = "BASE_URL"
    }
}