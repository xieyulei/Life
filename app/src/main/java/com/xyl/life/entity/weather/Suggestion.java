package com.xyl.life.entity.weather;

import com.google.gson.annotations.SerializedName;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/9.
 * ---------------------------
 * This class is used for:
 * 实体类：生活建议类，包含三个参数，分别为：舒适度建议、洗车建议、运动建议
 */

public class Suggestion {
    @SerializedName("comf")
    public Comfort comfort;

    @SerializedName("cw")
    public CarWash carWash;

    public Sport sport;

    public class Comfort{
        @SerializedName("txt")
        public String info;
    }

    public class CarWash{
        @SerializedName("txt")
        public String info;
    }
    public class Sport{
        @SerializedName("txt")
        public String info;
    }

}
