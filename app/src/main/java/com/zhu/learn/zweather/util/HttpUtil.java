package com.zhu.learn.zweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhu on 2017/3/13.
 * Http工具
 */

public class HttpUtil {
    /**
     * 发送Http请求
     * @param address 请求地址
     * @param callback 回调函数
     */
    public static void sendOKHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
