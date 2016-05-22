package com.example.sks.myapp;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 *  自定义 Activity 父类
 * Created by sks on 2016/2/15.
 */
public class BaseActivity extends FragmentActivity {

    protected void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatusBarCompat.compat(this);
    }
}
