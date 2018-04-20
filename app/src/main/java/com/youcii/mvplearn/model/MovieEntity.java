package com.youcii.mvplearn.model;

import com.youcii.mvplearn.greendao.BaseBeanConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;

/**
 * @author jdw
 * @date 2018/4/17
 */
@Entity
public class MovieEntity {

    @Index(unique = true)
    private String id;                   // 条目id
    private String title;                // 中文名
    private String original_title;       // 原名
    private String alt;                  // 条目URL
    @Convert(converter = MovieImagesConverter.class, columnType = String.class)
    private MovieImages images;          // 电影海报图，分别提供288px x 465px(大)，96px x 155px(中) 64px x 103px(小)尺寸
    @Convert(converter = MovieRatingConverter.class, columnType = String.class)
    private MovieRating rating;          // 评分
    private String year;                 // 年代
    private String subtype;              // 条目分类, movie或者tv

    public static class MovieImages {
        public String large;
        public String medium;
        public String small;
    }

    public static class MovieRating {
        public float average;     // 9.6
        public float max;         // 10
        public float min;         // 0
        public String stars;      // "50"
    }

    public static class MovieImagesConverter extends BaseBeanConverter<MovieImages> {
    }

    public static class MovieRatingConverter extends BaseBeanConverter<MovieRating> {
    }

    @Generated(hash = 1266359472)
    public MovieEntity(String id, String title, String original_title, String alt, MovieImages images, MovieRating rating, String year, String subtype) {
        this.id = id;
        this.title = title;
        this.original_title = original_title;
        this.alt = alt;
        this.images = images;
        this.rating = rating;
        this.year = year;
        this.subtype = subtype;
    }

    @Generated(hash = 1226161063)
    public MovieEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return this.original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getAlt() {
        return this.alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubtype() {
        return this.subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public MovieImages getImages() {
        return this.images;
    }

    public void setImages(MovieImages images) {
        this.images = images;
    }

    public MovieRating getRating() {
        return this.rating;
    }

    public void setRating(MovieRating rating) {
        this.rating = rating;
    }

}
