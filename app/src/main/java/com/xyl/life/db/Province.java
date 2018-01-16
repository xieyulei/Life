package com.xyl.life.db;

import org.litepal.crud.DataSupport;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/9.
 * ---------------------------
 * This class is used for:
 * Province实体类
 */

public class Province extends DataSupport {

    private int id;//每个实体类都应该有的字段
    private String provinceName;//记录省的名字
    private int provinceCode;//记录省的代号

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public int getId() {
        return id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }
}
