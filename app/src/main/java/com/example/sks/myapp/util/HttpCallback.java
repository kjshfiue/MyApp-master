package com.example.sks.myapp.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 网络请求回调类
 * Created by sks on 2015/11/30.
 */
public class HttpCallback implements Callback {
    private Context context;
    private String code;
    private String msg;

    private Handler mDelivery;

    public HttpCallback() {
        super();
    }


    public HttpCallback(Context context, String code, String msg) {
        super();
        this.context = context;
        this.code = code;
        this.msg = msg;

        mDelivery = new Handler(Looper.getMainLooper());

    }


    @Override
    public void onFailure(Request request, IOException e) {
        Util.e("onFailure - "+e.getMessage());
        onResult(-9999);
    }


    @Override
    public void onResponse(Response response) throws IOException {
        if (response.isSuccessful()) {
            String value = response.body().string();
            int errorCode = -1;
            Util.e("==========================onSuccess==============================");
            Util.e("yih", "value = " + value);
            try {

            } catch (JSONException e) {
                Util.e("JSON 解析错误 ：" + e.getMessage());
            } catch (Exception e) {
                Util.e("Exception = " + e.getMessage());
            } finally {
                onResult(errorCode);
            }
        }else {
            onResult(-2);
            throw new IOException("Unexpected code " + response);
        }
    }


    /**
     * 成功并且 errorCode == 0 时调用
     * @param jsonObject
     * @throws JSONException
     */
    public void onSuccessOk(JSONObject jsonObject) throws JSONException {
    }

    /**
     * 成功、失败都调用
     * @param errorCode
     */
    public void onResult(int errorCode) {
    }


    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<HttpCallback> activityWeakReference;

        public MyHandler(HttpCallback activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HttpCallback activity = activityWeakReference.get();
            if (activity != null) {

            }
        }
    }
}
