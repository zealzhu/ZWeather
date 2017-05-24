package com.zhu.learn.zweather.gson.xiaomi;

/**
 * Created by zhu on 2017/3/14.
 */

public class Yestoday {
    private String cityCode;

    private String date;

    private int humidityMax;

    private int humidityMin;

    private int precipitationMax;

    private int precipitationMin;

    private int tempMax;

    private int tempMin;

    private String weatherEnd;

    private String weatherStart;

    private String windDirectionEnd;

    private String windDirectionStart;

    private int windMax;

    private int windMin;

    public void setCityCode(String cityCode){
        this.cityCode = cityCode;
    }
    public String getCityCode(){
        return this.cityCode;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getDate(){
        return this.date;
    }
    public void setHumidityMax(int humidityMax){
        this.humidityMax = humidityMax;
    }
    public int getHumidityMax(){
        return this.humidityMax;
    }
    public void setHumidityMin(int humidityMin){
        this.humidityMin = humidityMin;
    }
    public int getHumidityMin(){
        return this.humidityMin;
    }
    public void setPrecipitationMax(int precipitationMax){
        this.precipitationMax = precipitationMax;
    }
    public int getPrecipitationMax(){
        return this.precipitationMax;
    }
    public void setPrecipitationMin(int precipitationMin){
        this.precipitationMin = precipitationMin;
    }
    public int getPrecipitationMin(){
        return this.precipitationMin;
    }
    public void setTempMax(int tempMax){
        this.tempMax = tempMax;
    }
    public int getTempMax(){
        return this.tempMax;
    }
    public void setTempMin(int tempMin){
        this.tempMin = tempMin;
    }
    public int getTempMin(){
        return this.tempMin;
    }
    public void setWeatherEnd(String weatherEnd){
        this.weatherEnd = weatherEnd;
    }
    public String getWeatherEnd(){
        return this.weatherEnd;
    }
    public void setWeatherStart(String weatherStart){
        this.weatherStart = weatherStart;
    }
    public String getWeatherStart(){
        return this.weatherStart;
    }
    public void setWindDirectionEnd(String windDirectionEnd){
        this.windDirectionEnd = windDirectionEnd;
    }
    public String getWindDirectionEnd(){
        return this.windDirectionEnd;
    }
    public void setWindDirectionStart(String windDirectionStart){
        this.windDirectionStart = windDirectionStart;
    }
    public String getWindDirectionStart(){
        return this.windDirectionStart;
    }
    public void setWindMax(int windMax){
        this.windMax = windMax;
    }
    public int getWindMax(){
        return this.windMax;
    }
    public void setWindMin(int windMin){
        this.windMin = windMin;
    }
    public int getWindMin(){
        return this.windMin;
    }
}
