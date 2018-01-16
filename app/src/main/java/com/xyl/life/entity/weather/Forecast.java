package com.xyl.life.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/9.
 * ---------------------------
 * This class is used for:
 * 实体类：包含三个参数，分别为：最高温度、最低温度、天气状况
 */

public class Forecast {
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {
        public String max;
        public String min;
    }

    public class More {
        @SerializedName("txt_d")
        public String info;
    }

}
