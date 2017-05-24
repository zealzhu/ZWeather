package com.zhu.learn.zweather.gson.xiaomi;

/**
 * Created by zhu on 2017/3/14.
 */

public class Aqi {
    private String city;

    private String city_id;

    private String pub_time;

    private String aqi;

    private String pm25;

    private String pm10;

    private String so2;

    private String no2;

    private String src;

    private String spot;

    public void setCity(String city){
        this.city = city;
    }
    public String getCity(){
        return this.city;
    }
    public void setCity_id(String city_id){
        this.city_id = city_id;
    }
    public String getCity_id(){
        return this.city_id;
    }
    public void setPub_time(String pub_time){
        this.pub_time = pub_time;
    }
    public String getPub_time(){
        return this.pub_time;
    }
    public void setAqi(String aqi){
        this.aqi = aqi;
    }
    public String getAqi(){
        return this.aqi;
    }
    public void setPm25(String pm25){
        this.pm25 = pm25;
    }
    public String getPm25(){
        return this.pm25;
    }
    public void setPm10(String pm10){
        this.pm10 = pm10;
    }
    public String getPm10(){
        return this.pm10;
    }
    public void setSo2(String so2){
        this.so2 = so2;
    }
    public String getSo2(){
        return this.so2;
    }
    public void setNo2(String no2){
        this.no2 = no2;
    }
    public String getNo2(){
        return this.no2;
    }
    public void setSrc(String src){
        this.src = src;
    }
    public String getSrc(){
        return this.src;
    }
    public void setSpot(String spot){
        this.spot = spot;
    }
    public String getSpot(){
        return this.spot;
    }
}
