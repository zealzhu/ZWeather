package com.zhu.learn.zweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhu.learn.zweather.R;
import com.zhu.learn.zweather.gson.xiaomi.Forecast;
import com.zhu.learn.zweather.gson.xiaomi.WeatherFromXiaoMi;
import com.zhu.learn.zweather.service.AutoUpdateWeatherService;
import com.zhu.learn.zweather.util.HttpUtil;
import com.zhu.learn.zweather.util.Utility;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zhu on 2017/3/15.
 */

public class WeatherActivity extends AppCompatActivity {
    private TextView titleCity;
    private TextView titleUpdateTime;
    private Toolbar toolbar;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView sunscreenText;
    private TextView dressText;
    private TextView sportText;
    private TextView washCarText;
    private TextView hangClothesText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefreshLayout;
    public DrawerLayout drawerLayout;

    private String weatherCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT > 21) {
            //如果是高版本的进行状态栏沉浸适配
            View decorView = getWindow().getDecorView();
            //设置成全屏
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN           //全屏隐藏状态栏
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION    //隐藏导航栏
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE             //与前面一起用表示主体内容占用隐藏的空间
            );
            //设置状态栏透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            //设置导航栏透明
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        //获取各个控件
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        titleCity = (TextView)findViewById(R.id.title_city);
        titleUpdateTime = (TextView)findViewById(R.id.title_update_time);
        degreeText = (TextView)findViewById(R.id.degree_text);
        weatherInfoText = (TextView)findViewById(R.id.weather_info_text);
        aqiText = (TextView)findViewById(R.id.aqi_text);
        pm25Text = (TextView)findViewById(R.id.pm25_text);
        sunscreenText = (TextView)findViewById(R.id.sunscreen_text);
        washCarText = (TextView)findViewById(R.id.wash_car_text);
        sportText = (TextView)findViewById(R.id.sport_text);
        hangClothesText = (TextView)findViewById(R.id.hang_clothes_text);
        dressText  = (TextView)findViewById(R.id.dress_text);
        forecastLayout = (LinearLayout)findViewById(R.id.forecast_layout);
        bingPicImg = (ImageView)findViewById(R.id.bing_pic_img);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        //设置toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        toggle.syncState();

        //从本地读取记录
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weather = sharedPreferences.getString("weather", null);
        if(weather != null) {
            //有天气记录则直接解析记录显示
            //从本地获取天气信息
            WeatherFromXiaoMi weatherFromXiaoMi = Utility.handleWeatherResponse(weather);
            //记录城市代码
            weatherCode = weatherFromXiaoMi.getForecast().getCityid();
            //显示天气
            showWeatherInfo(weatherFromXiaoMi);
        } else {
            //无记录则从服务器请求数据
            Intent intent = getIntent();
            weatherCode = intent.getStringExtra("WEATHER_CODE");
            requestWeather(weatherCode);
        }
        //下拉刷新天气事件
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherCode);
            }
        });
        //加载背景图
        Glide.with(this).load("https://www.dujin.org/sys/bing/1920.php").into(bingPicImg);
    }

    /**
     * 请求天气
     * @param weatherCode
     */
    public void requestWeather(String weatherCode) {
        this.weatherCode = weatherCode;
        String url = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=" + weatherCode;
        swipeRefreshLayout.setRefreshing(true);
        //请求天气
        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final WeatherFromXiaoMi weather = Utility.handleWeatherResponse(responseText);
                //清除图片缓存
                Glide.get(getApplicationContext()).clearDiskCache();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather != null) {
                            //将读取到的天气存储到本地，用来下次启动时显示
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            //显示数据
                            showWeatherInfo(weather);
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //加载背景图
        Glide.with(getApplicationContext()).load("https://www.dujin.org/sys/bing/1920.php").into(bingPicImg);
    }

    /**
     * 显示天气信息
     * @param weather 天气信息
     */
    public void showWeatherInfo(WeatherFromXiaoMi weather) {
        //启动自动更新服务
        Intent intent = new Intent(this, AutoUpdateWeatherService.class);
        startService(intent);
        //设置数据
        String cityName = weather.getAqi().getCity();
        String updateTime = weather.getRealtime().getTime();
        String degree   = weather.getRealtime().getTemp() + "℃";
        String weatherInfo = weather.getRealtime().getWeather();
        titleCity.setText(cityName);                //设置城市名
        titleUpdateTime.setText(updateTime);        //设置更新时间
        degreeText.setText(degree);                 //设置温度信息
        weatherInfoText.setText(weatherInfo);       //设置天气信息
        //获取未来天气
        Forecast forecast = weather.getForecast();
        forecastLayout.removeAllViews();
        Calendar date = Calendar.getInstance();
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日", Locale.CHINA);
            date.setTime(format.parse(forecast.getDate_y()));
            for(int i = 1; i <= 6; i++) {
                //创建未来天气View
                View view = LayoutInflater.from(WeatherActivity.this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView dateText = (TextView)view.findViewById(R.id.date_text);
                TextView infoText = (TextView)view.findViewById(R.id.info_text);
                TextView maxText = (TextView)view.findViewById(R.id.max_text);
                TextView minText = (TextView)view.findViewById(R.id.min_text);
                //设置日期
                dateText.setText(format.format(date.getTime()));
                //日期加一
                date.add(Calendar.DAY_OF_MONTH, 1);
                //反射获取方法
                Method getWeather = forecast.getClass().getDeclaredMethod("getWeather" + i);
                Method getTemp = forecast.getClass().getDeclaredMethod("getTemp" + i);
                //获取温度
                String temp = (String)getTemp.invoke(forecast);
                infoText.setText((String)getWeather.invoke(forecast));
                maxText.setText(temp.split("~")[0]);
                minText.setText(temp.split("~")[1]);
                forecastLayout.addView(view);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //设置Aqi
        aqiText.setText(weather.getAqi().getAqi());
        pm25Text.setText(weather.getAqi().getPm25());
        //设置生活指数
        if(weather.getIndex().size() > 0) {
            sunscreenText.setText(weather.getIndex().get(0).getName() + ":\n\t"
                    + weather.getIndex().get(0).getDetails());
            dressText.setText(weather.getIndex().get(1).getName() + ":\n\t"
                    + weather.getIndex().get(1).getDetails());
            sportText.setText(weather.getIndex().get(2).getName() + ":\n\t"
                    + weather.getIndex().get(2).getDetails());
            washCarText.setText(weather.getIndex().get(3).getName() + ":\n\t"
                    + weather.getIndex().get(3).getDetails());
            hangClothesText.setText(weather.getIndex().get(4).getName() + ":\n\t"
                    + weather.getIndex().get(4).getDetails());
        }
    }
}
