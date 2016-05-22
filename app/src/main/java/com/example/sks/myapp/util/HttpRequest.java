package com.example.sks.myapp.util;

import android.content.Context;

import com.example.sks.myapp.BuildConfig;

import java.util.Map;

/**
 * HTTP请求的参数实体类
 */
public class HttpRequest {
    //设备唯一编号imei
    public String deviceid;
    public String channel;//渠道号
    public String clientVersion;
    public String machineName;//机器名称
    public String platform;//1-安卓,2-IOS,3-WP
	public String token;
	public String method;
	public String requestType;
	public int userType; // 用户端请求是1，医生端是2.默认1
	public Map<String, Object> params;
	
	public HttpRequest(Context context) {
        super();
        platform = "1";
//        deviceid = Util.getImei(context);
        channel = BuildConfig.FLAVOR;
        clientVersion = BuildConfig.VERSION_NAME;
        machineName = android.os.Build.MODEL;
    }
}
