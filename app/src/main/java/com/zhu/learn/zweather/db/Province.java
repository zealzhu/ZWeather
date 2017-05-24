package com.zhu.learn.zweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by zhu on 2017/3/13.
 * 省份数据库表模型
 */

public class Province  extends DataSupport {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    //id
    private int id;
    //查询该省份所属城市用到的代码
    private int provinceCode;
    //省份名
    private String provinceName;
}
