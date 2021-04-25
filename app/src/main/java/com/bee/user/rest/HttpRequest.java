package com.bee.user.rest;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/07  19：47
 * 描述：
 */
public class HttpRequest {
//    http://dev-beeweb.quxianfeng.vip/bee-member-zuul-gateway/api-shop/app-sys-shop-store/getDetail/6


    public static final  String baseUrl = "http://dev-beeweb.quxianfeng.vip/bee-member-zuul-gateway/";



    //    public static final  String baseUrl_user = baseUrl + "api-user/";
    public static final  String baseUrl_user = "http://dev-bee-uhs-uaa.quxianfeng.vip/";

    //    public static final  String baseUrl_pay = baseUrl + "api-pay/";
    public static final  String baseUrl_pay = "http://dev-bee-biz-pay-center.quxianfeng.vip/";
    public static final  String baseUrl_shop = baseUrl + "api-shop/";





        public static final  String baseUrl_member = baseUrl + "api-member/";

    public static final  String baseUrl_order = "http://dev-bee-biz-order-center.quxianfeng.vip/";





    public static final  String login = "member/login/oneLogin";
    public static final  String login_password = "member/login/password";
    public static final  String login_code = "member/login/smscode";
    public static final  String resetPassword = "member/resetPassword";

    public static final  String zhifubao_pay = "app-t-pay-order/prePaySign";


    //附件 店铺
    public static final  String shop_nearby = "app-sys-shop-store/search/nearby";
    public static final  String shop_getDetail = "app-sys-shop-store/getDetail/{id}";
    public static final  String shop_queryProductList = "app-sys-permission-category/queryAppCategoryList/{id}";

    //确认单信息
    public static final  String submitPreview = "auth/submitPreview";

    //添加至购物车
    public static final  String addToCart = "cart/addToCart";
    //清空购物车的数据
    public static final  String clearCartInfo = "cart/clearCartInfo";
    //删除购物项
    public static final  String deleteCartItem = "cart/deleteCartItem";
    //获取购物车信息
    public static final  String getCart = "cart/getCart";
    //获取购物车某个购物项
    public static final  String getCartItem = "cart/getCartItem";
    //修改商品数量
    public static final  String updateQuantity = "cart/updateQuantity";


    public static final  String user_getInfo = "ums/AppUmsMember/getMemberInfo";
//    用户注册协议
    public static final  String xieyi_regist = "http://www.quxianfeng.vip/yszcxy.html";
//    隐私权政策
    public static final  String xieyi_yinsi = "http://www.quxianfeng.vip/yhyszc.html";


//    修改收货地址
    public static final  String saveAddress =  "auth/saveAddress";
}
