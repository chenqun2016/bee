package com.bee.user.rest;

import com.bee.user.bean.UserBean;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {

    /**
     * 一键登陆
     */
    @POST(HttpRequest.login)
    Observable<BaseResult<String>> login(@Body RequestBody info);


    /**
     * 支付宝预下单参数对象
     * bizId	integer($int64)
     * 支付业务ID[米粒配置ID]
     *
     * bizType	string
     * 支付业务类型[1.米粒面值2.配送卡]
     *
     * cardType	string
     * 配送卡类型[a.月度 b.季度 c.年度]
     *
     * deviceType	string
     * 设备类型[安卓、IOS]
     *
     * payChannel	string
     * 支付渠道[ALIPAY.支付宝 WECHATPAY.微信支付]
     *
     *
     */
    @POST(HttpRequest.zhifubao_pay)
    Observable<BaseResult<String>> zhifubao_pay(@Body RequestBody info);

    /**
     * 密码登陆
     * @Query("phone") String phone,@Query("password") String password
     */
    @POST(HttpRequest.login_password)
    Observable<BaseResult<String>> login_password(@Body RequestBody info);


    /**
     * 验证码登陆
     * @Query("phone") String phone,@Query("smsCode") String smsCode
     */
    @POST(HttpRequest.login_code)
    Observable<BaseResult<String>> login_code(@Body RequestBody info);

    /**
     * 重置密码
     * @Query("phone") String phone,@Query("password") String password,@Query("smsCode") String smsCode
     */
    @POST(HttpRequest.resetPassword)
    Observable<BaseResult<String>> resetPassword(@Body RequestBody info);


    /**
     * 发送验证码
     * @Path("mobile") String mobile
     */
    @GET("validata/smsCode/{mobile}")
    Observable<BaseResult<String>> smsCode(@Path("mobile") String mobile);




    /**
     * 搜索附近【店铺商家】
     */
    @POST(HttpRequest.shop_nearby)
    Observable<BaseResult<String>> shop_nearby(@Query("pageNum") int pageNum,
                                          @Query("pageSize") int pageSize,
                                          @Body RequestBody info);


    /**
     * 获取APP【店铺商家】详情
     */
    @GET(HttpRequest.shop_getDetail)
    Observable<BaseResult<String>> shop_getDetail(@Path("id") String id);

    /**
     * APP获取店铺【商品】列表
     */
    @GET(HttpRequest.shop_queryProductList)
    Observable<BaseResult<String>> shop_queryProductList(@Path("id") String id);
}
