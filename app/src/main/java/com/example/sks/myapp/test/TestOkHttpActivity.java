package com.example.sks.myapp.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.sks.myapp.R;
import com.example.sks.myapp.StatusBarCompat;
import com.example.sks.myapp.util.Http;
import com.example.sks.myapp.util.HttpCallback;
import com.example.sks.myapp.util.Util;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

public class TestOkHttpActivity extends AppCompatActivity {
    private TextView textView;

    private String URL = "http://weibo.com/u/2492602735/home?topnav=1&wvr=6#1461497200118";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ok_http);
        StatusBarCompat.compat(this);

        findViewById(R.id.btn_123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getHtml(URL);
                getData(URL);
            }
        });

        textView = (TextView) findViewById(R.id.text_view_http);


    }

    private MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private WeakReference<TestOkHttpActivity> activityWeakReference;

        public MyHandler(TestOkHttpActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            TestOkHttpActivity activity = activityWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        String value = (String) msg.obj;
                        activity.textView.setText(value);
                        break;
                }
            }
        }
    }


    private void getHtml(String url) {
        Http.get(url, new HttpCallback() {
            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String value = response.body().string();
//                    value = new String(value.getBytes(), "GBK");
                    value = getUTF8StringFromGBKString(value);
                    Util.e("==========================onResponse==============================");
                    Util.e("value = " + value);
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = value;
                    mHandler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                super.onFailure(request, e);
            }
        });
    }

    private void getData(String url) {
        Util.e("1是UI线程吗？ = " + Thread.interrupted());
        Http.post(url, null, new HttpCallback() {

            @Override
            public void onResponse(Response response) throws IOException {
                super.onResponse(response);
//                if (response.isSuccessful()) {
//                    String value = response.body().string();
//                    Util.e("==========================getData onResponse==============================");
//                    Util.e("value = " + value);
//                    Message msg = new Message();
//                    msg.what = 0;
//                    msg.obj = value;
//                    mHandler.sendMessage(msg);
//
//                    Util.e("2是UI线程吗？ = " + Thread.interrupted());
////                    textView.setText(value);
//                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                super.onFailure(request, e);
                Util.e("==========================getData onFailure==============================");
            }
        });
    }

    public static String getUTF8StringFromGBKString(String gbkStr) {
        try {
            return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalError();
        }
    }

    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            return tmp;
        }
        return utfBytes;
    }
}
