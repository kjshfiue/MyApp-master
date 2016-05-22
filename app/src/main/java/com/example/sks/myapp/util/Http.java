package com.example.sks.myapp.util;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 网络请求工具类
 * Created by sks on 2015/11/30.
 */
public class Http {
    private static final OkHttpClient mOkHttpClient;
    private static final String URL = "http://182.92.216.40/adapter/api/requestEncrypt";

    static {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
    }


    public static void get(String url, HttpCallback callback) {
        if (TextUtils.isEmpty(url)) {
            url = URL;
        }
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
    

    /**
     *
     * @param url
     * @param params
     * @param responseCallback
     */
    public static void post(String url, Param[] params, HttpCallback responseCallback) {
        if (params == null) {
            params = new Param[]{new Param()};
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.getKey(), param.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        mOkHttpClient.newCall(request).enqueue(responseCallback);
    }


    public static void post(Context context, String method, Map<String, Object> params, HttpCallback responseCallback) {

        String body = JSON.toJSONString(params);
        post(URL, new Param[]{new Param("body", body)}, responseCallback);
    }

    private static class Param {
        private String key;
        private String value;

        public Param() {
            this.key = "";
            this.value = "";
        }

        public Param(String key, String value) {
            this.key = key != null ? key : "";
            this.value = value != null ? value : "";
        }

        public String getKey() {
            return this.key;
        }

        public String getValue() {
            return this.value;
        }

    }


}
