package com.xyl.life.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * 实体类：包含两个参数，分别为：温度、天气信息
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }

}
