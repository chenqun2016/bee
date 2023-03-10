package com.bee.user.rest;

import com.bee.user.bean.AddressBean;
import com.bee.user.bean.AppUpdateInfoBean;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.CatogoryBean;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.ChooseTimeBean;
import com.bee.user.bean.CityBean;
import com.bee.user.bean.CollectionStoreBean;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.CommentWrapBean;
import com.bee.user.bean.CouponBean;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.bean.FirstBean;
import com.bee.user.bean.FoodDetailBean;
import com.bee.user.bean.GiftcardBean;
import com.bee.user.bean.GoodsBySectionBean;
import com.bee.user.bean.HelpTypeBean;
import com.bee.user.bean.HelpTypeItemBean;
import com.bee.user.bean.HomeCatogoryBean;
import com.bee.user.bean.MemberCenterBean;
import com.bee.user.bean.MiLiChongzhiBean;
import com.bee.user.bean.MyCommentWrapBean;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.bean.NewsBean;
import com.bee.user.bean.OrderDetailBean;
import com.bee.user.bean.OrderListBean;
import com.bee.user.bean.OrderingBean;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.bean.PaymentDetailBean;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.bean.PointDetailBen;
import com.bee.user.bean.SignInMessageBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreDetailFullBean;
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
     * ????????????
     */
    @POST(HttpRequest.login)
    Observable<BaseResult<String>> login(@Body RequestBody info);


    /**
     * ??????????????????????????????
     * bizId	integer($int64)
     * ????????????ID[????????????ID]
     * <p>
     * bizType	string
     * ??????????????????[1.????????????2.?????????]
     * <p>
     * cardType	string
     * ???????????????[a.?????? b.?????? c.??????]
     * <p>
     * deviceType	string
     * ????????????[?????????IOS]
     * <p>
     * payChannel	string
     * ????????????[ALIPAY.????????? WECHATPAY.????????????]
     */
    @POST(HttpRequest.zhifubao_pay)
    Observable<BaseResult<String>> zhifubao_pay(@Body RequestBody info);

    /**
     * ????????????
     *
     * @Query("phone") String phone,@Query("password") String password
     */
    @POST(HttpRequest.login_password)
    Observable<BaseResult<String>> login_password(@Body RequestBody info);


    /**
     * ???????????????
     *
     * @Query("phone") String phone,@Query("smsCode") String smsCode
     */
    @POST(HttpRequest.login_code)
    Observable<BaseResult<String>> login_code(@Body RequestBody info);

    /**
     * ????????????
     *
     * @Query("phone") String phone,@Query("password") String password,@Query("smsCode") String smsCode
     */
    @POST(HttpRequest.resetPassword)
    Observable<BaseResult<String>> resetPassword(@Body RequestBody info);


    /**
     * ???????????????
     *
     * @Path("mobile") String mobile
     */
    @GET("validata/smsCode/{mobile}")
    Observable<BaseResult<String>> smsCode(@Path("mobile") String mobile);


    /**
     * ??????????????????????????????
     */
    @POST(HttpRequest.shop_nearby)
    Observable<BaseResult<StoreListBean>> shop_nearby(@Query("pageNum") int pageNum,
                                                      @Query("pageSize") int pageSize,
                                                      @Body RequestBody info);

    /**
     * ??????APP????????????????????????
     */
    @POST(HttpRequest.shop_getDetail_full)
    Observable<BaseResult<StoreDetailFullBean>> shop_getDetail_full(@Path("storeId") String id);

    /**
     * ??????APP????????????????????????
     */
    @POST(HttpRequest.shop_getDetail)
    Observable<BaseResult<StoreDetailBean>> shop_getDetail(@Path("id") String id);

    /**
     * APP??????????????????????????????
     */
    @GET(HttpRequest.shop_queryProductList)
    Observable<BaseResult<List<StoreFoodItem1Bean>>> shop_queryProductList(@Path("storeId") String storeId);

    /**
     * ?????????????????????????????????????????????
     */
    @POST(HttpRequest.findShopProducts)
    Observable<BaseResult<List<StoreFoodItem2Bean>>> findShopProducts(@Body RequestBody info);

    /**
     * ?????????????????????????????????????????????
     */
    @POST(HttpRequest.productDetail)
    Observable<BaseResult<FoodDetailBean>> productDetail(@Path("shopProductId") Integer shopProductId);


    /**
     * ??????????????????
     *
     * @Path("mobile") String mobile
     */
    @GET(HttpRequest.user_getInfo)
    Observable<BaseResult<UserBean>> getUserInfo();

    /**
     * ??????????????????
     *
     * @Path("mobile") String mobile
     */
    @POST(HttpRequest.modifyMemberInfo)
    Observable<BaseResult<String>> modifyMemberInfo(@Body RequestBody info);

    /**
     * ??????????????????
     */
    @POST(HttpRequest.addToCart)
    Observable<BaseResult<ChartBean>> addToCart(@Body RequestBody info);

    /**
     * ???????????????
     */
    @POST(HttpRequest.submitPreview)
    Observable<BaseResult<OrderingConfirmBean>> submitPreview(@Body RequestBody info);

    /**
     * ??????
     */
    @POST(HttpRequest.ordering)
    Observable<BaseResult<OrderingBean>> ordering(@Body RequestBody info);

    /**
     * ????????????
     */
    @POST(HttpRequest.riceGrainsOrder)
    Observable<BaseResult<Object>> riceGrainsOrder(@Body RequestBody info);

    /**
     * ????????????
     */
    @POST(HttpRequest.orderList)
    Observable<BaseResult<OrderListBean>> orderList(@Body RequestBody info);

    /**
     * ????????????
     */
    @POST(HttpRequest.closeOrder)
    Observable<Boolean> closeOrder(@Query("note") String note, @Query("orderId") Integer orderId);

    /**
     * ????????????
     */
    @GET(HttpRequest.orderDetail)
    Observable<BaseResult<OrderDetailBean>> orderDetail(@Query("id") Integer id);

    /**
     * ????????????????????????
     */
    @POST(HttpRequest.clearCartInfo)
    Observable<BaseResult<String>> clearCartInfo(@Query("storeIds") List<String> storeIds);

    /**
     * ???????????????
     */
    @POST(HttpRequest.deleteCartItem)
    Observable<BaseResult<String>> deleteCartItem(@Query("cartItemIds") List<Integer> cartItemIds);

    /**
     * ?????????????????????
     */
    @GET(HttpRequest.getCart)
    Observable<BaseResult<List<ChartBean>>> getCart(@Query("receiveAddressId") Long receiveAddressId, @Query("storeIds") List<String> storeIds);

    /**
     * ??????????????????????????????
     */
    @GET(HttpRequest.getCartItem)
    Observable<BaseResult<String>> getCartItem(@Query("skuId") String skuId, @Query("storeId") String storeId);

    /**
     * ??????????????????
     */
    @POST(HttpRequest.updateQuantity)
    Observable<BaseResult<String>> updateQuantity(@Query("cartItemId") String cartItemId, @Query("quantity") String quantity);


    /**
     * ??????????????????
     */
//    @POST(HttpRequest.saveAddress)
//    Observable<BaseResult<String>> saveAddress(@Body RequestBody info);
    @POST(HttpRequest.saveAddress)
    Observable<BaseResult<String>> saveAddress(@Body RequestBody info);

    @POST(HttpRequest.deleteAddress)
    Observable<BaseResult<String>> deleteAddress(@Body RequestBody info);


    /**
     * ????????????????????????????????????
     */
    @POST(HttpRequest.caculateTime)
    Observable<BaseResult<ChooseTimeBean>> caculateTime(@Path("shopId") String shopId);


    /**
     * ????????????????????????
     */
    @POST(HttpRequest.getDefaultArea)
    Observable<BaseResult<AddressBean>> getDefaultArea();

    /**
     * ????????????????????????
     */
    @POST(HttpRequest.listAddress)
    Observable<BaseResult<List<AddressBean>>> listAddress();


    /**
     * ????????????????????????
     */
    @POST(HttpRequest.openCity)
    Observable<BaseResult<List<CityBean>>> openCity();

    /**
     * ????????????????????????
     */
    @POST(HttpRequest.helpType)
    Observable<BaseResult<List<HelpTypeBean>>> helpType();

    /**
     * ????????????????????????
     */
    @POST(HttpRequest.helpTypeItem)
    Observable<BaseResult<List<HelpTypeItemBean>>> helpTypeItem(@Path("typeId") int typeId);

    /**
     * ????????????
     */
    @POST(HttpRequest.helpApraise)
    Observable<BaseResult<Object>> helpApraise(@Body RequestBody info);

    /**
     * ??????????????????
     */
    @POST(HttpRequest.enterpriseOrder)
    Observable<BaseResult<Object>> enterpriseOrder(@Body RequestBody info);

    /**
     * ????????????key???????????????
     */
    @POST(HttpRequest.getDictByType)
    Observable<BaseResult<List<DictByTypeBean>>> getDictByType(@Path("type") String typeId);

    /**
     * App??????????????????
     */
    @POST(HttpRequest.appUpdateInfo)
    Observable<BaseResult<AppUpdateInfoBean>> appUpdateInfo(@Body RequestBody info);

    /**
     * ????????????????????????????????????
     */
    @Multipart
    @POST(HttpRequest.uploadObj)
    Observable<BaseResult<UploadImageBean>> uploadObj(@Part MultipartBody.Part file);

    /**
     * ??????????????????
     */
    @POST(HttpRequest.submitFeedback)
    Observable<BaseResult<Object>> submitFeedback(@Body RequestBody info);

    /**
     * ?????????????????????
     */
    @GET(HttpRequest.checkSmsCode)
    Observable<BaseResult<Object>> checkSmsCode(@Path("mobile") String mobile, @Path("code") String code);

    /**
     * ????????????????????????
     */
    @POST(HttpRequest.miliList)
    Observable<BaseResult<List<MiLiChongzhiBean>>> miliList();

    /**
     * ??????????????????????????????
     */
    @POST(HttpRequest.getMemberRice)
    Observable<BaseResult<MyMiLiBean>> getMemberRice();

    /**
     * ??????????????????????????????????????????
     * <p>
     * ???????????????????????????????????????????????????
     * ????????????????????????????????????
     * ?????????????????????????????????????????????????????????
     */
    @POST(HttpRequest.getPayList)
    Observable<BaseResult<List<TradeRecordBean>>> getPayList(@Body RequestBody info);

    /**
     * ??????????????????????????????????????????
     */
    @POST(HttpRequest.getPaymentDetail)
    Observable<BaseResult<PaymentDetailBean>> getPaymentDetail(@Path("paymentId") Integer paymentId);

    /**
     * ??????????????????
     */
    @POST(HttpRequest.setPayPassword)
    Observable<BaseResult<Object>> setPayPassword(@Body RequestBody info);

    /**
     * ??????????????????
     */
    @POST(HttpRequest.resetPayPassword)
    Observable<BaseResult<Object>> resetPayPassword(@Body RequestBody info);

    /**
     * check????????????
     */
    @POST(HttpRequest.checkOldPayPassword)
    Observable<BaseResult<Object>> checkOldPayPassword(@Body RequestBody info);


    /**
     * check????????????
     */
    @POST(HttpRequest.fillCardBinding)
    Observable<BaseResult<String>> fillCardBinding(@Body RequestBody info);


    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.giftCard)
    Observable<BaseResult<List<GiftcardBean>>> giftCard();


    /**
     * ????????????
     *
     * @return
     */
    @POST(HttpRequest.nearByBuilding)
    Observable<BaseResult<List<DingWeiBean>>> nearByBuilding(@Body RequestBody info);


    /**
     * ???????????????
     *
     * @return
     */
    @POST(HttpRequest.couponList)
    Observable<BaseResult<CouponBean>> couponList(@Body RequestBody info);


    /**
     * ?????????????????????
     *
     * @return
     */
    @POST(HttpRequest.distributionCard)
    Observable<BaseResult<PeiSongCardBean>> distributionCard();

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.distributionCardOnSale)
    Observable<BaseResult<List<PeiSongCardBean>>> distributionCardOnSale();


    /**
     * ???????????????
     *
     * @return
     */
    @POST(HttpRequest.buyCard)
    Observable<BaseResult<Object>> buyCard(@Body RequestBody info);

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.getUserPoints)
    Observable<BaseResult<UserPointsBean>> getUserPoints();

    /**
     * ????????????
     *
     * @return
     */
    @POST(HttpRequest.userSignIn)
    Observable<BaseResult<UserSigninBean>> userSignIn();

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.getSignInMessage)
    Observable<BaseResult<SignInMessageBean>> getSignInMessage();

    /**
     * ???????????????????????????
     *
     * @Path("mobile") String mobile
     */
    @POST(HttpRequest.sendSmsCode)
    Observable<BaseResult<String>> sendSmsCode(@Body RequestBody info);

    /**
     * ????????????
     *
     * @return
     */
    @POST(HttpRequest.closeAccount)
    Observable<BaseResult<Object>> closeAccount(@Body RequestBody info);

    /**
     * ?????????????????????banner???
     * ???app-index-top???APP????????????banner???app-index-middle???APP????????????banner???app-my???APP??????banner???
     *
     * @return
     */
    @POST(HttpRequest.getBanner)
    Observable<BaseResult<List<BannerBean>>> getBanner(@Path("place") String place);


    /**
     * ????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.commentCreate)
    Observable<BaseResult<Object>> commentCreate(@Body RequestBody info);

    /**
     * ??????????????????????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.commentQueryList)
    Observable<BaseResult<CommentWrapBean>> commentQueryList(@Body RequestBody info, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.pointsRecord)
    Observable<BaseResult<List<PointDetailBen>>> pointsRecord();


    /**
     * ????????????ID????????????
     */
    @POST(HttpRequest.queryCommentByOrder)
    Observable<BaseResult<CommentBean>> queryCommentByOrder(@Path("orderId") Integer orderId);

    /**
     * ????????????ID????????????
     */
    @POST(HttpRequest.queryCommentById)
    Observable<BaseResult<CommentBean>> queryCommentById(@Path("id") Integer id);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.myOrderComment)
    Observable<BaseResult<MyCommentWrapBean>> myOrderComment(@Body RequestBody info);

    /**
     * ?????????????????????
     *
     * @return
     */
    @POST(HttpRequest.goodsTimeSection)
    Observable<BaseResult<List<TimeSectionBean>>> goodsTimeSection();



    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.myOrderCommentDelete)
    Observable<BaseResult<Object>> myOrderCommentDelete(@Path("id") Integer id);

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.queryListBySkuId)
    Observable<BaseResult<CommentWrapBean>> queryListBySkuId(@Query("shopProductId") String shopProductId,@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * ???????????????????????? (header?????????????????????longitude???latitude???)?????????api-shop
     * app-sys-shop-store/index/recommand
     * @return
     */
    @POST(HttpRequest.homeRecommand)
    Observable<BaseResult<List<StoreFoodItem2Bean>>> homeRecommand();

    /**
     * ????????????
     * @return
     */
    @POST(HttpRequest.indexCatogory)
    Observable<BaseResult<List<HomeCatogoryBean>>> indexCatogory();

    /**
     * ????????????????????????
     * @return
     */
    @POST(HttpRequest.getGoodsByCatogory)
    Observable<BaseResult<CatogoryBean>> getGoodsByCatogory(@Body RequestBody info);
    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.getPlatFormMessage)
    Observable<BaseResult<List<NewsBean>>> getPlatFormMessage(@Body RequestBody info);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.getGoodsBySection)
    Observable<BaseResult<GoodsBySectionBean>> getGoodsBySection(@Body RequestBody info);

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.getMemberFavorites)
    Observable<BaseResult<CollectionStoreBean>> getMemberFavorites(@Body RequestBody info);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.addFavorites)
    Observable<BaseResult<Object>> addFavorites(@Body RequestBody info);

    /**
     * ????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.upAndDownFavorites)
    Observable<BaseResult<Object>> upAndDownFavorites(@Body RequestBody info);

    /**
     * ??????????????????
     *
     * @return
     */
    @POST(HttpRequest.cancleFavorites)
    Observable<BaseResult<Object>> cancleFavorites(@Body RequestBody info);

    /**
     * ??????????????????????????????
     *
     * @return
     */
    @POST(HttpRequest.getMemberLevelMessage)
    Observable<BaseResult<MemberCenterBean>> getMemberLevelMessage();

    /**
     * ????????????
     *
     * @return
     */
    @POST(HttpRequest.goodFoodList)
    Observable<BaseResult<List<GoodsBySectionBean.RecordBean>>> goodFoodList();

    /**
     * ????????????
     *
     * @return
     */
    @POST(HttpRequest.salesList)
    Observable<BaseResult<List<GoodsBySectionBean.RecordBean>>> salesList();

    /**
     * ?????????????????????????????????????????????????????? (header?????????????????????longitude???latitude???)
     *
     * @return
     */
    @POST(HttpRequest.first)
    Observable<BaseResult<FirstBean>> first();


}
