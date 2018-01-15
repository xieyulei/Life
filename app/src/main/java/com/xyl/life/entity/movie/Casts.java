package com.xyl.life.entity.movie;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/12.
 * ---------------------------
 * This class is used for:
 * 实体类--导演，包含四个属性：影人条目id，中文名，影人条目URL，影人头像
 */

public class Casts {
    private String alt;
    private Images avatars;
    private String name;
    private String id;

    public Casts(String alt, Images avatars, String name, String id) {
        this.alt = alt;
        this.avatars = avatars;
        this.name = name;
        this.id = id;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Images getAvatars() {
        return avatars;
    }

    public void setAvatars(Images avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

