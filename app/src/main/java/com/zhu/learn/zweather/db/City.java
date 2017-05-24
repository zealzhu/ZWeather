package com.zhu.learn.zweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by zhu on 2017/3/13.
 * 城市数据库表模型
 */

public class City extends DataSupport{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;
}
