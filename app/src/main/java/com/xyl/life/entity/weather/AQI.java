package com.xyl.life.entity.weather;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/9.
 * ---------------------------
 * This class is used for:
 * 实体类：包含两个参数，分别为：aqi指数与pm2.5指数
 */

public class AQI {


    public AQIActivity city;

    public class AQIActivity{
        public String aqi;
        public String pm25;
    }
}
