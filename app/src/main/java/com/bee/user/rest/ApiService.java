package com.bee.user.rest;

import com.bee.user.bean.AddCartBean;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.AppUpdateInfoBean;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.ChooseTimeBean;
import com.bee.user.bean.CityBean;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.CommentWrapBean;
import com.bee.user.bean.CouponBean;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.bean.FoodDetailBean;
import com.bee.user.bean.GiftcardBean;
import com.bee.user.bean.HelpTypeBean;
import com.bee.user.bean.HelpTypeItemBean;
import com.bee.user.bean.MiLiChongzhiBean;
import com.bee.user.bean.MyCommentWrapBean;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.bean.OrderDetailBean;
import com.bee.user.bean.OrderListBean;
import com.bee.user.bean.OrderingBean;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.bean.PaymentDetailBean;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.bean.PointDetailBen;
import com.bee.user.bean.SignInMessageBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreFoodItem1Bean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.bean.TimeSectionBean;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.bean.UploadImageBean;
import com.bee.user.bean.UserBean;
import com.bee.user.bean.UserPointsBean;
import com.bee.user.bean.UserSigninBean;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * <p>
     * bizType	string
     * 支付业务类型[1.米粒面值2.配送卡]
     * <p>
     * cardType	string
     * 配送卡类型[a.月度 b.季度 c.年度]
     * <p>
     * deviceType	string
     * 设备类型[安卓、IOS]
     * <p>
     * payChannel	string
     * 支付渠道[ALIPAY.支付宝 WECHATPAY.微信支付]
     */
    @POST(HttpRequest.zhifubao_pay)
    Observable<BaseResult<String>> zhifubao_pay(@Body RequestBody info);

    /**
     * 密码登陆
     *
     * @Query("phone") String phone,@Query("password") String password
     */
    @POST(HttpRequest.login_password)
    Observable<BaseResult<String>> login_password(@Body RequestBody info);


    /**
     * 验证码登陆
     *
     * @Query("phone") String phone,@Query("smsCode") String smsCode
     */
    @POST(HttpRequest.login_code)
    Observable<BaseResult<String>> login_code(@Body RequestBody info);

    /**
     * 重置密码
     *
     * @Query("phone") String phone,@Query("password") String password,@Query("smsCode") String smsCode
     */
    @POST(HttpRequest.resetPassword)
    Observable<BaseResult<String>> resetPassword(@Body RequestBody info);


    /**
     * 发送验证码
     *
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
    Observable<BaseResult<List<StoreFoodItem1Bean>>> shop_queryProductList(@Path("storeId") String storeId);

    /**
     * 查询指定店铺分类对应的商品列表
     */
    @POST(HttpRequest.findShopProducts)
    Observable<BaseResult<List<StoreFoodItem2Bean>>> findShopProducts(@Body RequestBody info);

    /**
     * 查询指定店铺分类对应的商品列表
     */
    @POST(HttpRequest.productDetail)
    Observable<BaseResult<FoodDetailBean>> productDetail(@Path("skuId") Integer skuId);


    /**
     * 获取用户信息
     *
     * @Path("mobile") String mobile
     */
    @GET(HttpRequest.user_getInfo)
    Observable<BaseResult<UserBean>> getUserInfo();

    /**
     * 修改个人信息
     *
     * @Path("mobile") String mobile
     */
    @POST(HttpRequest.modifyMemberInfo)
    Observable<BaseResult<String>> modifyMemberInfo(@Body RequestBody info);

    /**
     * 添加至购物车
     */
    @POST(HttpRequest.addToCart)
    Observable<BaseResult<AddCartBean>> addToCart(@Body RequestBody info);

    /**
     * 确认单信息
     */
    @POST(HttpRequest.submitPreview)
    Observable<BaseResult<OrderingConfirmBean>> submitPreview(@Body RequestBody info);

    /**
     * 下单
     */
    @POST(HttpRequest.ordering)
    Observable<BaseResult<OrderingBean>> ordering(@Body RequestBody info);

    /**
     * 米粒下单
     */
    @POST(HttpRequest.riceGrainsOrder)
    Observable<BaseResult<Object>> riceGrainsOrder(@Body RequestBody info);

    /**
     * 订单列表
     */
    @POST(HttpRequest.orderList)
    Observable<BaseResult<OrderListBean>> orderList(@Body RequestBody info);

    /**
     * 订单列表
     */
    @POST(HttpRequest.closeOrder)
    Observable<Boolean> closeOrder(@Query("note") String note, @Query("orderId") Integer orderId);

    /**
     * 订单列表
     */
    @GET(HttpRequest.orderDetail)
    Observable<BaseResult<OrderDetailBean>> orderDetail(@Query("id") Integer id);

    /**
     * 清空购物车的数据
     */
    @POST(HttpRequest.clearCartInfo)
    Observable<BaseResult<String>> clearCartInfo(@Query("storeIds") List<String> storeIds);

    /**
     * 删除购物项
     */
    @GET(HttpRequest.deleteCartItem)
    Observable<BaseResult<String>> deleteCartItem(@Query("cartItemIds") List<Integer> cartItemIds);

    /**
     * 获取购物车信息
     */
    @GET(HttpRequest.getCart)
    Observable<BaseResult<List<ChartBean>>> getCart(@Query("receiveAddressId") Long receiveAddressId, @Query("storeIds") List<String> storeIds);

    /**
     * 获取购物车某个购物项
     */
    @GET(HttpRequest.getCartItem)
    Observable<BaseResult<String>> getCartItem(@Query("skuId") String skuId, @Query("storeId") String storeId);

    /**
     * 修改商品数量
     */
    @POST(HttpRequest.updateQuantity)
    Observable<BaseResult<String>> updateQuantity(@Query("cartItemId") String cartItemId, @Query("quantity") String quantity);


    /**
     * 修改收货地址
     */
//    @POST(HttpRequest.saveAddress)
//    Observable<BaseResult<String>> saveAddress(@Body RequestBody info);
    @POST(HttpRequest.saveAddress)
    Observable<BaseResult<String>> saveAddress(@Body RequestBody info);

    @POST(HttpRequest.deleteAddress)
    Observable<BaseResult<String>> deleteAddress(@Body RequestBody info);


    /**
     * 获取店铺可选配送时间列表
     */
    @POST(HttpRequest.caculateTime)
    Observable<BaseResult<ChooseTimeBean>> caculateTime(@Path("shopId") String shopId);


    /**
     * 获取默认收货地址
     */
    @POST(HttpRequest.getDefaultArea)
    Observable<BaseResult<AddressBean>> getDefaultArea();

    /**
     * 获取默认收货地址
     */
    @POST(HttpRequest.listAddress)
    Observable<BaseResult<List<AddressBean>>> listAddress();


    /**
     * 获取默认收货地址
     */
    @POST(HttpRequest.openCity)
    Observable<BaseResult<List<CityBean>>> openCity();

    /**
     * 获取帮助分类列表
     */
    @POST(HttpRequest.helpType)
    Observable<BaseResult<List<HelpTypeBean>>> helpType();

    /**
     * 获取帮助内容列表
     */
    @POST(HttpRequest.helpTypeItem)
    Observable<BaseResult<List<HelpTypeItemBean>>> helpTypeItem(@Path("typeId") int typeId);

    /**
     * 帮助评价
     */
    @POST(HttpRequest.helpApraise)
    Observable<BaseResult<Object>> helpApraise(@Body RequestBody info);

    /**
     * 企业订餐提交
     */
    @POST(HttpRequest.enterpriseOrder)
    Observable<BaseResult<Object>> enterpriseOrder(@Body RequestBody info);

    /**
     * 获取指定key的字典配置
     */
    @POST(HttpRequest.getDictByType)
    Observable<BaseResult<List<DictByTypeBean>>> getDictByType(@Path("type") String typeId);

    /**
     * App版本更新信息
     */
    @POST(HttpRequest.appUpdateInfo)
    Observable<BaseResult<AppUpdateInfoBean>> appUpdateInfo(@Body RequestBody info);

    /**
     * 文件上传阿里云，返回对象
     */
    @Multipart
    @POST(HttpRequest.uploadObj)
    Observable<BaseResult<UploadImageBean>> uploadObj(@Part MultipartBody.Part file);

    /**
     * 提交意见反馈
     */
    @POST(HttpRequest.submitFeedback)
    Observable<BaseResult<Object>> submitFeedback(@Body RequestBody info);

    /**
     * 获取购物车信息
     */
    @GET(HttpRequest.checkSmsCode)
    Observable<BaseResult<Object>> checkSmsCode(@Path("mobile") String mobile, @Path("code") String code);

    /**
     * 【米粒面值配置】
     */
    @POST(HttpRequest.miliList)
    Observable<BaseResult<List<MiLiChongzhiBean>>> miliList();

    /**
     * 查询【用户米粒额度】
     */
    @POST(HttpRequest.getMemberRice)
    Observable<BaseResult<MyMiLiBean>> getMemberRice();

    /**
     * 根据条件查询【交易流水】列表
     * <p>
     * 充值：购买米粒、充值卡或电子券充值
     * 收入：系统赠送、系统退款
     * 支出：购买商品、购买配送卡、购买礼品卡
     */
    @POST(HttpRequest.getPayList)
    Observable<BaseResult<List<TradeRecordBean>>> getPayList(@Body RequestBody info);

    /**
     * 根据条件查询【交易流水】列表
     */
    @POST(HttpRequest.getPaymentDetail)
    Observable<BaseResult<PaymentDetailBean>> getPaymentDetail(@Path("paymentId") Integer paymentId);

    /**
     * 设置支付密码
     */
    @POST(HttpRequest.setPayPassword)
    Observable<BaseResult<Object>> setPayPassword(@Body RequestBody info);

    /**
     * 重置支付密码
     */
    @POST(HttpRequest.resetPayPassword)
    Observable<BaseResult<Object>> resetPayPassword(@Body RequestBody info);

    /**
     * check支付密码
     */
    @POST(HttpRequest.checkOldPayPassword)
    Observable<BaseResult<Object>> checkOldPayPassword(@Body RequestBody info);


    /**
     * check支付密码
     */
    @POST(HttpRequest.fillCardBinding)
    Observable<BaseResult<String>> fillCardBinding(@Body RequestBody info);


    /**
     * 获取可购买礼品卡列表
     *
     * @return
     */
    @POST(HttpRequest.giftCard)
    Observable<BaseResult<List<GiftcardBean>>> giftCard();


    /**
     * 附近地址
     *
     * @return
     */
    @POST(HttpRequest.nearByBuilding)
    Observable<BaseResult<List<DingWeiBean>>> nearByBuilding(@Body RequestBody info);


    /**
     * 优惠券列表
     *
     * @return
     */
    @POST(HttpRequest.couponList)
    Observable<BaseResult<CouponBean>> couponList(@Body RequestBody info);


    /**
     * 用户配送卡列表
     *
     * @return
     */
    @POST(HttpRequest.distributionCard)
    Observable<BaseResult<PeiSongCardBean>> distributionCard();

    /**
     * 获取可购买配送卡列表
     *
     * @return
     */
    @POST(HttpRequest.distributionCardOnSale)
    Observable<BaseResult<List<PeiSongCardBean>>> distributionCardOnSale();


    /**
     * 优惠券列表
     *
     * @return
     */
    @POST(HttpRequest.buyCard)
    Observable<BaseResult<Object>> buyCard(@Body RequestBody info);

    /**
     * 查询会员积分信息
     *
     * @return
     */
    @POST(HttpRequest.getUserPoints)
    Observable<BaseResult<UserPointsBean>> getUserPoints();

    /**
     * 会员签到
     *
     * @return
     */
    @POST(HttpRequest.userSignIn)
    Observable<BaseResult<UserSigninBean>> userSignIn();

    /**
     * 获取活动信息
     *
     * @return
     */
    @POST(HttpRequest.getSignInMessage)
    Observable<BaseResult<SignInMessageBean>> getSignInMessage();

    /**
     * 注销账户发送验证码
     *
     * @Path("mobile") String mobile
     */
    @POST(HttpRequest.sendSmsCode)
    Observable<BaseResult<String>> sendSmsCode(@Body RequestBody info);

    /**
     * 注销账号
     *
     * @return
     */
    @POST(HttpRequest.closeAccount)
    Observable<BaseResult<Object>> closeAccount(@Body RequestBody info);

    /**
     * 获取指定位置的banner，
     * 【app-index-top：APP首页顶部banner；app-index-middle：APP首页中部banner；app-my：APP我的banner】
     *
     * @return
     */
    @POST(HttpRequest.getBanner)
    Observable<BaseResult<List<BannerBean>>> getBanner(@Path("place") String place);


    /**
     * 新增店铺订单评价
     *
     * @return
     */
    @POST(HttpRequest.commentCreate)
    Observable<BaseResult<Object>> commentCreate(@Body RequestBody info);

    /**
     * 根据条件查询店铺订单评价列表
     *
     * @return
     */
    @POST(HttpRequest.commentQueryList)
    Observable<BaseResult<CommentWrapBean>> commentQueryList(@Body RequestBody info, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 获取积分记录
     *
     * @return
     */
    @POST(HttpRequest.pointsRecord)
    Observable<BaseResult<List<PointDetailBen>>> pointsRecord();


    /**
     * 根据订单ID订单评价
     */
    @POST(HttpRequest.queryCommentByOrder)
    Observable<BaseResult<CommentBean>> queryCommentByOrder(@Path("orderId") Integer orderId);

    /**
     * 根据订单ID订单评价
     */
    @POST(HttpRequest.queryCommentById)
    Observable<BaseResult<CommentBean>> queryCommentById(@Path("id") Integer id);

    /**
     * 获取积分记录
     *
     * @return
     */
    @POST(HttpRequest.myOrderComment)
    Observable<BaseResult<MyCommentWrapBean>> myOrderComment(@Body RequestBody info);

    /**
     * 今日秒杀时间段
     *
     * @return
     */
    @POST(HttpRequest.goodsTimeSection)
    Observable<BaseResult<List<TimeSectionBean>>> goodsTimeSection();



    /**
     * 删除店铺订单评价信息
     *
     * @return
     */
    @POST(HttpRequest.myOrderCommentDelete)
    Observable<BaseResult<Object>> myOrderCommentDelete(@Path("id") Integer id);

    /**
     * 删除店铺订单评价信息
     *
     * @return
     */
    @POST(HttpRequest.queryListBySkuId)
    Observable<BaseResult<CommentWrapBean>> queryListBySkuId(@Query("skuId") String skuId,@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}
