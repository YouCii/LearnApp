package com.youcii.mvplearn.model

/**
 * Cre by jdw on 2018/3/30.
 */
data class MovieSubject constructor(
        var id: String,                     // 条目id
        var title: String,                  // 中文名
        var original_title: String,         // 原名
        var alt: Float,                     // 条目URL
        var images: ArrayList<Images>,      // 电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸
        var rating: ArrayList<Rating>,      // 评分
        var pubdates: ArrayList<String>,    // 如果条目类型是电影则为上映日期，如果是电视剧则为首播日期
        var year: String,                   // 年代
        var subtype: String                 // 条目分类, movie或者tv
) {
    data class Images constructor(
            var large: String,
            var medium: String,
            var small: String
    )

    data class Rating constructor(
            var average: Float,     // 9.6
            var max: Float,         // 10
            var min: Float,         // 0
            var stars: String       // "50"
    )
}
