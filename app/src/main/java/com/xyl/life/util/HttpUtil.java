package com.xyl.life.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * -------------------------
 * Author：doraemon
 * Created by xyl on 2018/1/9.
 * ---------------------------
 * This class is used for:
 * 用户获取服务端的省市县数据，负责与服务端的交互
 */



public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback){
        /**
         * 调用sendOkHttpRequest方法，传入请求地址，并注册一个回调处理服务器响应就可以了。
         * 由于服务器返回的省市县数据都是Json类型的，所以我们最好再提供一个工具类来解析和处理这种数据。
         */
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);

    }
}
