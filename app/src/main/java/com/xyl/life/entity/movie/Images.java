package com.xyl.life.entity.movie;

/**
 * 实体类：电影海报图，分别提供大中小三个尺寸的海报
 * 大：288px 乘以 465px
 * 中：96px 乘以 155px
 * 小：64px 乘以 103px
 */

public class Images {
    private String id;
    private String small;
    private String medium;
    private String large;

    public Images(String id, String small, String medium, String large) {
        this.id = id;
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}
