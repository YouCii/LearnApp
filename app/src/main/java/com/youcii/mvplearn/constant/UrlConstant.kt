package com.youcii.mvplearn.constant

class UrlConstant {
    companion object {
        /**
         * 阿里云的地址
         *
         * 注: url格式为 scheme://host[:port]/path[?query]
         *      作为Retrofit的BASE_URL时, 应注意如果存在 path 部分, 必须以/结尾, 且后续拼接的字段不允许以/开头
         *      全适应方式: baseUrl都以/结尾, 后续拼接均不以/开头
         */
        const val BASE_URL_ALI = "http://iploc.market.alicloudapi.com/"
        const val BASE_URL_DOU_BAN = "https://api.douban.com/"
        const val BASE_URL_KEY = "BASE_URL"
    }
}