package com.zhu.learn.zweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by zhu on 2017/3/13.
 * 县数据库表模型
 */

public class Country extends DataSupport {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    private int id;
    private String countryName;
    private String weatherCode;
    private int cityId;
}
