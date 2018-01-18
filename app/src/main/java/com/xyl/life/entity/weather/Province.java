package com.xyl.life.entity.weather;

import org.litepal.crud.DataSupport;

/**
 * 实体类：Province（省）
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
