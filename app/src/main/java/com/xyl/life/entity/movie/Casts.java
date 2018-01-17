package com.xyl.life.entity.movie;

import java.io.Serializable;

/**
 * 实体类：导演，包含四个属性：影人条目id，中文名，影人条目URL，影人头像
 */

public  class Casts implements Serializable {
    private String alt;
    private Images avatars;
    private String name;
    private String id;

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