package com.zhu.learn.zweather.gson.xiaomi;

/**
 * Created by zhu on 2017/3/14.
 */

public class AccuCC {
    private String EpochTime;

    private String LocalObservationDateTime;

    private String Pressure;

    private String RealFeelTemperature;

    private String RelativeHumidity;

    private String UVIndex;

    private String Visibility;

    private String WindDirectionDegrees;

    private String WindSpeed;

    public void setEpochTime(String EpochTime){
        this.EpochTime = EpochTime;
    }
    public String getEpochTime(){
        return this.EpochTime;
    }
    public void setLocalObservationDateTime(String LocalObservationDateTime){
        this.LocalObservationDateTime = LocalObservationDateTime;
    }
    public String getLocalObservationDateTime(){
        return this.LocalObservationDateTime;
    }
    public void setPressure(String Pressure){
        this.Pressure = Pressure;
    }
    public String getPressure(){
        return this.Pressure;
    }
    public void setRealFeelTemperature(String RealFeelTemperature){
        this.RealFeelTemperature = RealFeelTemperature;
    }
    public String getRealFeelTemperature(){
        return this.RealFeelTemperature;
    }
    public void setRelativeHumidity(String RelativeHumidity){
        this.RelativeHumidity = RelativeHumidity;
    }
    public String getRelativeHumidity(){
        return this.RelativeHumidity;
    }
    public void setUVIndex(String UVIndex){
        this.UVIndex = UVIndex;
    }
    public String getUVIndex(){
        return this.UVIndex;
    }
    public void setVisibility(String Visibility){
        this.Visibility = Visibility;
    }
    public String getVisibility(){
        return this.Visibility;
    }
    public void setWindDirectionDegrees(String WindDirectionDegrees){
        this.WindDirectionDegrees = WindDirectionDegrees;
    }
    public String getWindDirectionDegrees(){
        return this.WindDirectionDegrees;
    }
    public void setWindSpeed(String WindSpeed){
        this.WindSpeed = WindSpeed;
    }
    public String getWindSpeed(){
        return this.WindSpeed;
    }
}
