package com.youcii.mvplearn.response

import com.youcii.mvplearn.model.MovieEntity

/**
 * Created by jdw on 2018/3/30.
 */
data class TopMovieResponse constructor(
        var start: Int,                         // start
        var count: Int,                         // count
        var total: Int,                         // 总数
        var title: String,                      // 排行榜名称
        var subjects: ArrayList<MovieEntity>    // 数据集合
)