package com.zhu.learn.zweather.gson.xiaomi;

import java.util.List;

/**
 * Created by zhu on 2017/3/14.
 */

public class WeatherFromXiaoMi {
    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public Realtime getRealtime() {
        return realtime;
    }

    public void setRealtime(Realtime realtime) {
        this.realtime = realtime;
    }

    public List<Alert> getAlert() {
        return alert;
    }

    public void setAlert(List<Alert> alert) {
        this.alert = alert;
    }

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public List<Index> getIndex() {
        return index;
    }

    public void setIndex(List<Index> index) {
        this.index = index;
    }

    public AccuCC getAccu_cc() {
        return accu_cc;
    }

    public void setAccu_cc(AccuCC accu_cc) {
        this.accu_cc = accu_cc;
    }

    public AccuF5 getAccu_f5() {
        return accu_f5;
    }

    public void setAccu_f5(AccuF5 accu_f5) {
        this.accu_f5 = accu_f5;
    }

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    public Yestoday getYestoday() {
        return yestoday;
    }

    public void setYestoday(Yestoday yestoday) {
        this.yestoday = yestoday;
    }

    private Forecast forecast;

    private Realtime realtime;

    private List<Alert> alert;

    private Aqi aqi;

    private List<Index> index;

    private AccuCC accu_cc;

    private AccuF5 accu_f5;

    private Today today;

    private Yestoday yestoday;


}
