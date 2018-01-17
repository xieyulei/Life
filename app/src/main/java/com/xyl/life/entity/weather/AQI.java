package com.xyl.life.entity.weather;

/**
 * 实体类：包含两个参数，分别为：aqi指数与pm2.5指数
 */

public class AQI {


    public AQIActivity city;

    public class AQIActivity{
        public String aqi;
        public String pm25;
    }
}
