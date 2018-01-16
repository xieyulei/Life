package com.xyl.life.entity.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/9.
 * ---------------------------
 * This class is used for:
 * 总的实体类：用来引用刚刚创建的各个实体类
 */

public class Weather {
    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

}
