package com.bee.user.rest;

import com.bee.user.bean.AddCartBean;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreFoodItemBean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.bean.UserBean;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;

import java.util.List;
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
    Observable<BaseResult<StoreListBean>> shop_nearby(@Query("pageNum") int pageNum,
                                                      @Query("pageSize") int pageSize,
                                                      @Body RequestBody info);


    /**
     * 获取APP【店铺商家】详情
     */
    @GET(HttpRequest.shop_getDetail)
    Observable<BaseResult<StoreDetailBean>> shop_getDetail(@Path("id") String id);

    /**
     * APP获取店铺【商品】列表
     */
    @GET(HttpRequest.shop_queryProductList)
    Observable<BaseResult<List<StoreFoodItemBean>>> shop_queryProductList(@Path("id") String id);



    /**
     * 获取用户信息
     * @Path("mobile") String mobile
     */
    @GET(HttpRequest.user_getInfo)
    Observable<BaseResult<UserBean>> getUserInfo();


    /**
     * 添加至购物车
     */
    @POST(HttpRequest.addToCart)
    Observable<BaseResult<AddCartBean>> addToCart(@Body RequestBody info);

    /**
     * 确认单信息
     */
    @POST(HttpRequest.submitPreview)
    Observable<BaseResult<String>> submitPreview(@Body RequestBody info);


    /**
     * 清空购物车的数据
     */
    @GET(HttpRequest.clearCartInfo)
    Observable<BaseResult<String>> clearCartInfo(@Query("storeIds") List<Integer> storeIds);
    /**
     * 删除购物项
     */
    @GET(HttpRequest.deleteCartItem)
    Observable<BaseResult<String>> deleteCartItem(@Query("cartItemIds") List<Integer> cartItemIds);
    /**
     * 获取购物车信息
     */
    @GET(HttpRequest.getCart)
    Observable<BaseResult<List<ChartBean>>> getCart( @Query("storeIds") List<Integer> storeIds);
    /**
     * 获取购物车某个购物项
     */
    @GET(HttpRequest.getCartItem)
    Observable<BaseResult<String>> getCartItem(@Query("skuId") String skuId,@Query("storeId") String storeId);
    /**
     * 修改商品数量
     */
    @GET(HttpRequest.updateQuantity)
    Observable<BaseResult<String>> updateQuantity(@Query("cartItemId") String cartItemId,@Query("quantity") String quantity);



    /**
     * 修改收货地址
     */
    @POST(HttpRequest.saveAddress)
    Observable<BaseResult<String>> saveAddress(@Body RequestBody info);
}
