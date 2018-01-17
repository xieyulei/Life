package com.xyl.life.entity.movie;

import java.io.Serializable;

/**
 * 实体类：影片评分：包含四个参数：最低、最高、平均、获得星级
 */

public class Rating implements Serializable {
    private String id;
    private int min;
    private int max;
    private float average;
    private int stars;

    public Rating(String id, int min, int max, float average, int stars) {
        this.id = id;
        this.min = min;
        this.max = max;
        this.average = average;
        this.stars = stars;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
