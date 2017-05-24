package com.zhu.learn.zweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.support.annotation.IntDef;

import com.zhu.learn.zweather.gson.xiaomi.WeatherFromXiaoMi;
import com.zhu.learn.zweather.util.HttpUtil;
import com.zhu.learn.zweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 后台自动更新天气服务
 */
public class AutoUpdateWeatherService extends Service {
    public AutoUpdateWeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();    //更新天气
        //设置定时任务
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //设置定时时间8小时
        long time = SystemClock.elapsedRealtime() + 8 * 1000 * 60 * 60;
        PendingIntent pendingIntent =
                PendingIntent.getService(this, 0,
                        new Intent(this, AutoUpdateWeatherService.class), 0);
        //先取消定时任务
        alarmManager.cancel(pendingIntent);
        //设置定时任务
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    public void updateWeather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sharedPreferences.getString("weather", null);
        if(weatherString != null) {
            //如果有缓存直接解析
            WeatherFromXiaoMi weatherFromXiaoMi = Utility.handleWeatherResponse(weatherString);
            String weatherCode = weatherFromXiaoMi.getForecast().getCityid();
            String url = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=" + weatherCode;
            HttpUtil.sendOKHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    WeatherFromXiaoMi weatherFromXiaoMi = Utility.handleWeatherResponse(responseText);
                    if(weatherFromXiaoMi != null) {
                        SharedPreferences.Editor editor =
                                PreferenceManager.getDefaultSharedPreferences(AutoUpdateWeatherService.this).edit();
                        editor.putString("weather", responseText);
                        editor.apply();
                    }
                }
            });
        }
    }
}
