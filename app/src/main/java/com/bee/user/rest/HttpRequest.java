package com.bee.user.rest;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/07  19：47
 * 描述：
 */
public class HttpRequest {
//    http://dev-beeweb.quxianfeng.vip/bee-member-zuul-gateway/api-shop/app-sys-shop-store/getDetail/6


    public static final String baseUrl = "http://dev-beeweb.quxianfeng.vip/bee-member-zuul-gateway/";

    public static final  String baseUrl_user = baseUrl + "api-uaa/";
    public static final String baseUrl_pay = baseUrl + "api-pay/";
    public static final String baseUrl_shop = baseUrl + "api-shop/";
    public static final String baseUrl_goods = baseUrl + "api-goods/";
    public static final String baseUrl_member = baseUrl + "api-member/";
    public static final String baseUrl_order = baseUrl + "api-order/";
    public static final String baseUrl_sys = baseUrl + "api-user/";
    public static final String baseUrl_file = baseUrl + "api-third/";
    public static final String baseUrl_activity = baseUrl + "api-activity/";
    public static final  String baseUrl_eva = baseUrl + "api-eva/";


    public static final String login = "member/login/oneLogin";
    public static final String login_password = "member/login/password";
    public static final String login_code = "member/login/smscode";
    public static final String resetPassword = "member/resetPassword";




    //附件 店铺
    public static final String shop_nearby = "app-sys-shop-store/search/nearby";
    public static final String shop_getDetail = "app-sys-shop-store/getDetail/{id}";
    //APP获取【店铺类目】列表
    public static final String shop_queryProductList = "app-sys-industry-category-shop/queryAppIndustryList/{storeId}";
    //查询指定店铺分类对应的商品列表
    public static final String findShopProducts = "auth/shop/findShopProducts";
    //查询指定店铺商品SKU详情
    public static final String productDetail = "auth/shop/productDetail/{skuId}";
    //获取店铺可选配送时间列表
    public static final String caculateTime = "pms/appFeight/caculate/{shopId}";
    //获取默认收货地址
    public static final String getDefaultArea = "address/getDefaultItem";
    public static final String listAddress = "address/listAddress";

    //获取开通城市列表
    public static final String openCity = "app-sys/openCity";



    //确认单信息
    public static final String submitPreview = "auth/submitPreview";
    //预下单
    public static final String ordering ="auth/generateOrder";
    //米粒下单
    public static final String riceGrainsOrder ="auth/riceGrainsOrder";

    //订单列表
    public static final String orderList ="auth/order/list";
    //订单详情
    public static final String orderDetail ="auth/detail";
    //取消订单
    public static final String closeOrder ="auth/closeOrder";

    //添加至购物车
    public static final String addToCart = "cart/addToCart";
    //清空购物车的数据
    public static final String clearCartInfo = "cart/clearCartInfo";
    //删除购物项
    public static final String deleteCartItem = "cart/deleteCartItem";
    //获取购物车信息
    public static final String getCart = "cart/getCart";
    //获取购物车某个购物项
    public static final String getCartItem = "cart/getCartItem";
    //修改商品数量
    public static final String updateQuantity = "cart/updateQuantity";


    //修改个人信息
    public static final String modifyMemberInfo = "ums/AppUmsMember/modifyMemberInfo";
    public static final String user_getInfo = "ums/AppUmsMember/getMemberInfo";
    //    用户注册协议
    public static final String xieyi_regist = "http://www.quxianfeng.vip/yszcxy.html";
    //    隐私权政策
    public static final String xieyi_yinsi = "http://www.quxianfeng.vip/yhyszc.html";


    //    修改收货地址
    public static final String saveAddress = "address/saveAddress";
    //    修改收货地址
    public static final String deleteAddress = "address/deleteAddress";

    //获取帮助分类列表
    public static final String helpType = "app-help/helpType";

    //获取帮助内容列表
    public static final String helpTypeItem = "app-help/helpList/{typeId}";

    //帮助评价
    public static final String helpApraise = "app-help/helpApraise";

    //企业订餐提交
    public static final String enterpriseOrder = "app-sys/enterpriseOrder";

    //获取指定key的字典配置
    public static final String getDictByType = "app-sys/getDictByType/{type}";

    //App版本更新信息
    public static final String appUpdateInfo = "app-sys/appUpdateInfo";

    //文件上传阿里云，返回对象
    public static final String uploadObj = "oss/file/uploadObj";

    //提交意见反馈
    public static final String submitFeedback = "app-sys/submitFeedback";

    //提交意见反馈
    public static final String checkSmsCode = "validata/checkSmsCode/{mobile}/{code}";

    //米粒面值配置
    public static final String miliList = "app-t-pay-order/riceFaceConfig";

    //【米粒配送卡充值app预下单】
    public static final String zhifubao_pay = "app-t-pay-order/prePaySign";

    //查询【用户米粒额度】
    public static final String getMemberRice = "app-t-pay-payments-tran/getMemberRice";

    //根据条件查询【交易流水】列表
    public static final String getPayList = "app-t-pay-payments-tran/list";
    //查询交易明细
    public static final String getPaymentDetail = "app-t-pay-payments-tran/getPaymentDetail/{paymentId}";

    //设置支付密码
    public static final String setPayPassword = "ums/AppUmsMember/setPayPassword";

    //重置支付密码
    public static final String resetPayPassword = "ums/AppUmsMember/resetPayPassword";

    //check支付密码
    public static final String checkOldPayPassword = "ums/AppUmsMember/checkOldPayPassword";

    //通过充值卡绑定充值
    public static final String fillCardBinding = "app-coupon/fillCardBinding";


    //获取可购买礼品卡列表
    public static final String giftCard = "app-coupon/giftCard";
    //附近地址
    public static final String nearByBuilding = "app-area-building/nearByBuilding";


    //优惠券列表app-coupon/distributionCard
    public static final String couponList = "app-coupon/couponList";
    //用户配送卡列表
    public static final String distributionCard = "app-coupon/distributionCard";
    //获取可购买配送卡列表
    public static final String distributionCardOnSale = "app-coupon/distributionCardOnSale";


    //购买礼品卡、配送卡
    public static final String buyCard = "app-coupon/buyCard";
    //查询会员积分信息
    public static final String getUserPoints = "member/points/getUserPoints";

    //会员签到
    public static final String userSignIn = "member/points/userSignIn";

    //获取签到列表信息
    public static final String getSignInMessage = "member/points/getSignInMessage";

    //注销账户获取短信验证码
    public static final String sendSmsCode = "validata/sendSmsCode";

    //注销账号
    public static final String closeAccount = "ums/AppUmsMember/closeAccount";

    //获取指定位置的banner，【app-index-top：APP首页顶部banner；app-index-middle：APP首页中部banner；app-my：APP我的banner】
    public static final String getBanner = "app-sys/getBanner/{place}";


    //新增店铺订单评价
    public static final String commentCreate = "evaShopStoreOrder/create";

    //根据条件查询店铺订单评价列表
    public static final String commentQueryList = "evaShopStoreOrder/queryList";
    //获取积分记录
    public static final String pointsRecord = "member/points/pointsRecord";

    //根据订单ID订单评价
    public static final String queryCommentByOrder = "evaShopStoreOrder/queryByOrder/{orderId}";

    //根据订单ID订单评价
    public static final String queryCommentById = "evaShopStoreOrder/queryById/{id}";

    //查询我的评价
    public static final String myOrderComment = "evaShopStoreOrder/myOrderComment";

    //今日秒杀时间段
    public static final String goodsTimeSection= "app-flash-sale-goods/timeSection";

    //删除店铺订单评价信息
    public static final String myOrderCommentDelete = "evaShopStoreOrder/delete/{id}";

    public static final String queryListBySkuId = "evaShopStoreOrder/queryListBySkuId";

    //首页推荐
    public static final String homeRecommand= "app-sys-shop-store/index/recommand";

}
