package com.bee.user.rest;

import com.bee.user.bean.UserBean;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /**
     * 一键登陆
     */
    @POST(HttpRequest.login)
    Observable<BaseResult<UserBean>> login(@Query("accessToken") String accessToken, @Query("osType") String osType);

    /**
     * 密码登陆
     */
    @POST(HttpRequest.login_password)
    Observable<BaseResult<UserBean>> login_password(@Query("phone") String phone,@Query("password") String password);


    /**
     * 验证码登陆
     */
    @POST(HttpRequest.login_code)
    Observable<BaseResult<UserBean>> login_code(@Query("phone") String phone,@Query("smsCode") String smsCode);

    /**
     * 重置密码
     */
    @POST(HttpRequest.resetPassword)
    Observable<BaseResult<UserBean>> resetPassword(@Query("phone") String phone,@Query("password") String password,@Query("smsCode") String smsCode);


    /**
     * 重置密码
     */
    @GET("validata/smsCode/{mobile}")
    Observable<BaseResult<String>> smsCode(@Path("mobile") String mobile);
}
