package com.bee.user.rest;

import android.text.TextUtils;
import android.util.Base64;

import com.bee.user.Constants;
import com.bee.user.utils.Base64Utils;
import com.bee.user.utils.DeviceUtils;
import com.bee.user.utils.EncryptUtils;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.NetWorkUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * Created by chenqun on 2018/1/29.
 */

public class HttpOkInterceptor implements Interceptor {
    private final Boolean mAddParams;

    public HttpOkInterceptor(Boolean b) {
        this.mAddParams = b;
    }
    /**
     * 拦截器
     * 添加公共参数
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request oriRequest = chain.request();

        //header添加公共参数
        Request.Builder builder = oriRequest.newBuilder()
                .addHeader("Content-Type", "application/json;charset=utf-8")
//                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("uid", "")//用户id（已登录用户传）
                .addHeader("Authorization", SPUtils.geTinstance().getToken())//用户token（已登录用户传）
                .addHeader("channel", DeviceUtils.getAppMetaData()+"" ) // app下载渠道
                .addHeader("osType", "android")//系统 （ios、android...）
                .addHeader("osVersion", android.os.Build.VERSION.SDK_INT+"") // 系统版本号
                .addHeader("netType", NetWorkUtil.getNetworkType().name()+"") // 网络类型 （4G、WIFI...）
                .addHeader("deviceToken", DeviceUtils.getIMEI()+"") // 唯一设备IDDeviceUtils.getIMEI()+
                .addHeader("model", DeviceUtils.getModel()+"") // 设备型号（iphone8、ipad、xiaomi...）
                .addHeader("support", URLEncoder.encode(NetWorkUtil.getNetworkOperatorName())+" ") // 运营商（移动、联通、电信...）
                .addHeader("appVersion", DeviceUtils.getAppVersionName()+"");// app版本

        Map<String, String> hashMap = new HashMap<>();

        //添加实体 公共参数
        if(mAddParams){
            //Body
//            FormBody responseBody = new FormBody.Builder()
//                    .add("transTime", System.currentTimeMillis() + "")
//                    .add("token", "")
//                    .add("channel", "android")//【0:wap、1:ios、2:android、3:pc】
//                    .build();
//
//            for (int i = 0; i < responseBody.size(); i++) {
//                hashMap.put(responseBody.name(i), URLDecoder.decode(responseBody.value(i)));
//            }
//
//            builder.post(responseBody);
        }
        //获取url后接拼接参数
//        String url = oriRequest.url().toString();
//        LogUtil.d("apiurl", "url==" + url);
//        if (url.contains("?")) {
//            String substring = url.substring(url.indexOf("?") + 1);
//            String[] params = substring.split("&");
//            for (int i = 0; i < params.length; i++) {
//                String param = params[i];
//                if (param.contains("=")) {
//                    String[] split = param.split("=");
//                    if(split.length>1){
//                        hashMap.put(split[0], URLDecoder.decode(split[1])+"");
//                    }else if(split.length>0){
//                        hashMap.put(split[0], "");
//                    }
//                }
//            }
//        }
//
//        //获取实体参数
//        RequestBody requestBody = oriRequest.body();
//        boolean hasRequestBody = requestBody != null;
//        if (hasRequestBody) {
//            Buffer buffer = new Buffer();
//            requestBody.writeTo(buffer);
//
//            Charset charset = Constants.UTF8;
//            MediaType contentType = requestBody.contentType();
//            if (contentType != null) {
//                charset = contentType.charset(Constants.UTF8);
//            }
//            String s = buffer.readString(charset);
//            LogUtil.d("apiurl", "body==" + s);
//
//            //上传文件的参数不加密
//            if (!TextUtils.isEmpty(s) && !s.contains("Content-Type: image/jpg")) {
//                String[] params = s.split("&");
//                for (int i = 0; i < params.length; i++) {
//                    String param = params[i];
//                    if (param.contains("=")) {
//                        String[] split = param.split("=");
//                        if(split.length>1){
//                            hashMap.put(split[0], URLDecoder.decode(split[1])+"");
//                        }else if(split.length>0){
//                            hashMap.put(split[0], "");
//                        }
//                    }
//                }
//            }
//        }
//        LogUtil.d("paramsMap", hashMap.toString());

//        //RSA加密参数
//        String s = new Gson().toJson(hashMap);
//        LogUtil.d("paramsMap", s);
//        byte[] signature = EncryptUtils.encryptRSA(s.getBytes(), Base64.decode(Constants.getRSA(),Base64.NO_WRAP), true, "RSA/ECB/PKCS1Padding");
//        String encode = Base64Utils.encode(signature);
//        LogUtil.d("paramsMapkey", "encode=="+encode);
//        //添加请求头
//        builder.addHeader("signature",encode+"");

        Request request = builder.build();
        return chain.proceed(request);//chain.proceed(chain.request());
    }
}
