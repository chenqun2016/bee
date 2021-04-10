package com.bee.user;

import java.nio.charset.Charset;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：常量池
 */
public class Constants {
    public static final float RATE_HOME = 280f / 650f;              //首页banner
    public static final String UID = "uid";
    public static final String TOKEN = "token";


    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String USER_INFO = "userinfo";
    public static final String USER_LOCATION = "location";

    //RSA
    public static String getRSA(){
        return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5zS5NObS9AQDwH5sSUNTA2jY6RyUEMGpu9UqmAWNCuDlFo0GovMQkbRuyNy/qXj3V6+0dElG7nCaFQyQ4quww0eZwPE6Sc7iFu3QdoTFPQ4prqpUcYZlcHRdWTntJaaodsI1fMB67eo/oaI4+97MW1za/6nqcGVECz/Z0rqhQswIDAQAB";
    }



    //订单详情中 订单状态
    public static final int TYPE_ALL = 0;//全部
    public static final int TYPE_PAY_WAITE = 1;//等待支付
    public static final int TYPE_COMPLETE = 2;//订单已送达,已收货
    public static final int TYPE_TO_BE_COMMENTED =3;//待评价
    public static final int TYPE_TUIKUAN = 4;//退款

    public static final int TYPE_READY = 5;//商家正在备货
    public static final int TYPE_PEISONG = 6;//商品配送中
    public static final int TYPE_CANCELED = 7;//订单已取消




    public static final int Delay_Reflush_false = 400;
    public static final int Delay_Reflush_true = 100;
    public static final String PDF_Common = "common";

}
