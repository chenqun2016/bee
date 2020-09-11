package com.bee.user.rest;

import android.widget.Toast;

import com.bee.user.BeeApplication;
import com.bee.user.BuildConfig;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.NetWorkUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/07  19：37
 * 描述：
 */
public class Api {
    public static Gson gson = new Gson();

    public static RequestBody getRequestBody(Map<String, String> map){

        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),gson.toJson(map));
    }



    public static ApiService getClient() {

        //log 设置请求log拦截器
        okhttp3.logging.HttpLoggingInterceptor interceptor = new okhttp3.logging.HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("httplog",message);
            }
        });
        interceptor.setLevel(BuildConfig.DEBUG ?  okhttp3.logging.HttpLoggingInterceptor.Level.BODY :  okhttp3.logging.HttpLoggingInterceptor.Level.NONE);//NONE,BASIC ,HEADERS, BODY

        File cacheFile = new File(BeeApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //添加证书
        OkHttpClient okClient = new OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(new HttpOkInterceptor(true))
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())

                .cache(cache).build();
//https 秘钥
//.newBuilder().sslSocketFactory(SSLContextUtil.getSslSocketFactory(DemoApp.getInstance().getAssets().open("server.crt"))).build()

        Retrofit client = new Retrofit.Builder()
                .baseUrl(HttpRequest.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okClient)
                .build();

        return client.create(ApiService.class);
    }
    //没有实体公共参数
    public static ApiService getClientNo() {
        //log 设置请求log拦截器
        okhttp3.logging.HttpLoggingInterceptor interceptor = new okhttp3.logging.HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ?  okhttp3.logging.HttpLoggingInterceptor.Level.BODY :  okhttp3.logging.HttpLoggingInterceptor.Level.NONE);//NONE,BASIC ,HEADERS, BODY
          File cacheFile = new File(BeeApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //添加证书
        OkHttpClient okClient = new OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(new HttpOkInterceptor(false))
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();
//https 秘钥
//.newBuilder().sslSocketFactory(SSLContextUtil.getSslSocketFactory(DemoApp.getInstance().getAssets().open("server.crt"))).build()

        Retrofit client = new Retrofit.Builder()
                .baseUrl(HttpRequest.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okClient)
                .build();

        return client.create(ApiService.class);
    }

//    没有缓存
    public static ApiService getClientNoWithNoCache() {
        //log 设置请求log拦截器
        okhttp3.logging.HttpLoggingInterceptor interceptor = new okhttp3.logging.HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ?  okhttp3.logging.HttpLoggingInterceptor.Level.BODY :  okhttp3.logging.HttpLoggingInterceptor.Level.NONE);//NONE,BASIC ,HEADERS, BODY
        //添加证书
        OkHttpClient okClient = new OkHttpClient.Builder()
                .proxy(Proxy.NO_PROXY)
                .addInterceptor(new HttpOkInterceptor(false))
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .build();
//https 秘钥
//.newBuilder().sslSocketFactory(SSLContextUtil.getSslSocketFactory(DemoApp.getInstance().getAssets().open("server.crt"))).build()

        Retrofit client = new Retrofit.Builder()
                .baseUrl(HttpRequest.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okClient)
                .build();

        return client.create(ApiService.class);
    }

    //网络拦截器
    static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(BeeApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtil.d("Okhttp", "no network");
                Toast.makeText(BeeApplication.getInstance(), "网络异常", Toast.LENGTH_SHORT).show();
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(BeeApplication.getInstance())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

}
