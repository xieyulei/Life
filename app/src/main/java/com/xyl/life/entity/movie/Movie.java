package com.xyl.life.entity.movie;

import java.io.Serializable;

/**
 * 实体类：定义从豆瓣api获取到Json文件相关参数的数据类型
 */

public class Movie implements Serializable {

    private Rating rating ;//评分
    private long reviews_count;//影评数量
    private long wish_count ;//想看人数
    private String douban_site;//豆瓣小站
    private String year;//年代
    private Images images;//电影海报图
    private String alt ;//条目URL
    private String id ;//条目id
    private String mobile_url ;//移动版条目页URL
    private String title ;//中文名
    private String do_count ;//再看人数：电视剧默认值为0；电影值则为null
    private String share_url;//网页分享地址
    private String seasons_count;//总季数（only tv）
    private String schedule_url;//影讯页URL(only movie)
    private String episodes_count;//当前季的集数（only tv）
    private String [] countries;//制片国家、地区
    private String [] genres ;//影片类型，最多提供3个
    private int collect_count ;//看过人数
    private Casts[] casts;//主演，最多可获得4个
    private int current_season;//当前季数（only tv）
    private String original_title ;//原名
    private String summary;//简介
    private String subtype ;//条目分类，movie或者tv
    private Casts[] directors;//导演
    private int comments_count;//短评数量
    private int ratings_count ;//评分人数
    private String[] aka ;//又名

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public long getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(long reviews_count) {
        this.reviews_count = reviews_count;
    }

    public long getWish_count() {
        return wish_count;
    }

    public void setWish_count(long wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDo_count() {
        return do_count;
    }

    public void setDo_count(String do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(String seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public String getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(String episodes_count) {
        this.episodes_count = episodes_count;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Casts[] getCasts() {
        return casts;
    }

    public void setCasts(Casts[] casts) {
        this.casts = casts;
    }

    public int getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(int current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Casts[] getDirectors() {
        return directors;
    }

    public void setDirectors(Casts[] directors) {
        this.directors = directors;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public String[] getAka() {
        return aka;
    }

    public void setAka(String[] aka) {
        this.aka = aka;
    }
}
