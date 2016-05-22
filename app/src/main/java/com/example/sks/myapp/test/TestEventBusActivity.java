package com.example.sks.myapp.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.sks.myapp.BaseActivity;
import com.example.sks.myapp.R;
import com.example.sks.myapp.test.event.ItemEvent;
import com.example.sks.myapp.util.MaterialProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class TestEventBusActivity extends BaseActivity {
    private TextView mText;

    private int index = 0;

    private MaterialProgressBar pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event_bus);

        EventBus.getDefault().register(this);

        mText = (TextView) findViewById(R.id.m_text);

        pd = (MaterialProgressBar) findViewById(R.id.pb);


        findViewById(R.id.m_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ItemEvent("这是点击得来的数据"));
                startActivity(new Intent(TestEventBusActivity.this, TestEventBusNextActivity.class));
            }
        });
        findViewById(R.id.m_pro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mHandler.sendEmptyMessageDelayed(0, 1000);


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe
    public void onEventMainThread(ItemEvent event) {
        if (event != null && mText != null)
            mText.setText(event.text);
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeMessages(0);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        mHandler.removeMessages(0);
        super.onBackPressed();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    pd.setProgress(index / 100f);
                    index += 10;
                    if(index>100){
                        index = 0;
                    }

                        mHandler.sendEmptyMessageDelayed(0, 100);

                    break;
                case 1:

                    break;
            }
        }
    };

}
