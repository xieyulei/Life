package com.xyl.life.entity.movie;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/12.
 * ---------------------------
 * This class is used for:
 * 影片前250
 */

public class Top250 {
    private int count;
    private int start;
    private int total;//总数
    private Movie[] subjects;//条目列表
    private String title;//排行榜名称

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Movie[] getSubjects() {
        return subjects;
    }

    public void setSubjects(Movie[] subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
