package com.xyl.life.entity.book;

import java.io.Serializable;

/**
 * 实体类：图书标签
 */

public class Tags implements Serializable{
    private int count;
    private String name;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
