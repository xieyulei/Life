package com.xyl.life.entities.book;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/15.
 * ---------------------------
 * This class is used for:
 */

public class BookItem {
    private String mIcon;
    private String Name;
    private int mPrice;
    private float mScore;
    private static final String FREE = "Free";

    public BookItem(String name,String icon) {
        mIcon = icon;
        Name = name;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public void setScore(float score) {
        mScore = score;
    }

    public String getIcon() {
        return mIcon;
    }

    public String getName() {
        return Name;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getDisplayPrice() {
        return mPrice==0?FREE:Float.toString(mPrice);
    }

    public float getScore() {
        return mScore;
    }
}
