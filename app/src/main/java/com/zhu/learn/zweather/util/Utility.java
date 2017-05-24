package com.zhu.learn.zweather.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.zhu.learn.zweather.db.City;
import com.zhu.learn.zweather.db.Country;
import com.zhu.learn.zweather.db.Province;
import com.zhu.learn.zweather.gson.xiaomi.WeatherFromXiaoMi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhu on 2017/3/13.
 * 工具类
 */

public class Utility {
    /**
     * 解析省份数据并保存到数据库中国
     * @param response 请求省份的返回数据
     * @return 是否解析成功
     */
    public static boolean handleProvinceResponse(String response) {
        if(!TextUtils.isEmpty(response)) {
            try {
                //获得所有省份的JSON数组
                JSONArray provinces = new JSONArray(response);
                for(int i = 0; i < provinces.length(); i++) {
                    //获取省份JSON对象
                    JSONObject provincesJSONObject = provinces.getJSONObject(i);
                    //创建Province并保存到数据库中
                    Province province = new Province();
                    province.setProvinceName(provincesJSONObject.getString("name"));
                    province.setProvinceCode(provincesJSONObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析市级数据
     * @param response 请求市级返回的数据
     * @param provinceId 查询省级的id
     * @return 返回是否解析成功
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if(!TextUtils.isEmpty(response)) {
            try {
                //获得所有省份的JSON数组
                JSONArray cities = new JSONArray(response);
                for(int i = 0; i < cities.length(); i++) {
                    //获取省份JSON对象
                    JSONObject cityJSONObject = cities.getJSONObject(i);
                    //创建City并保存到数据库中
                    City city = new City();
                    city.setCityName(cityJSONObject.getString("name"));
                    city.setCityCode(cityJSONObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountryResponse(String response, int cityId) {
        if(!TextUtils.isEmpty(response)) {
            try {
                //获得所有省份的JSON数组
                JSONArray countries = new JSONArray(response);
                for(int i = 0; i < countries.length(); i++) {
                    //获取省份JSON对象
                    JSONObject countryJSONObject = countries.getJSONObject(i);
                    //创建Country并保存到数据库中
                    Country country = new Country();
                    country.setCountryName(countryJSONObject.getString("name"));
                    country.setWeatherCode(countryJSONObject.getString("weather_id"));
                    country.setCityId(cityId);
                    country.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析天气数据
     * @param response 请求天气的响应
     * @return 返回天气实体
     */
    public static WeatherFromXiaoMi handleWeatherResponse(String response) {
        return new Gson().fromJson(response, WeatherFromXiaoMi.class);
    }
}
