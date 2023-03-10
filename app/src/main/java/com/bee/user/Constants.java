package com.bee.user;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：常量池
 */
public class Constants {
    public static SimpleDateFormat sdfLong2 = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat sdfLong3 = new SimpleDateFormat("yyyy-MM-dd");

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

/**
 订单状态
 [
 SS.订单提交成功
 WP.订单待支付
 OFP.订单已支付
 WMJ.商家待接单
 OMJ.商家已接单
 JMJ.商家分拣完成（动态顺序）
 WPS.配送员待接单
 OPS.配送员已接单
 QPS.配送员取货中
 GPS.配送员已到店
 HPS.配送员已取货
 SPS.配送员送货中
 DPS.商品已送达
 OF.订单已完成
 OC.订单已取消
 ]
 撤单是在接单之前才能做，申请退款是在收货后才能做
 */
    public static final String TYPE_ORDER_SS = "SS";//订单提交成功
    public static final String TYPE_ORDER_OFP = "OFP";//订单已支付
    public static final String TYPE_ORDER_WMJ = "WMJ";//商家待接单
    public static final String TYPE_ORDER_JMJ = "JMJ";//商家分拣完成
    public static final String TYPE_ORDER_WPS = "WPS";//配送员待接单
    public static final String TYPE_ORDER_OPS = "OPS";//配送员已接单
    public static final String TYPE_ORDER_QPS = "QPS";//配送员取货中
    public static final String TYPE_ORDER_GPS = "GPS";//配送员已到店
    public static final String TYPE_ORDER_HPS = "HPS";//配送员已取货
    public static final String TYPE_ORDER_OF = "OF";//订单已完成

    public static final String TYPE_ORDER_WP = "WP";//订单待支付
    public static final String TYPE_ORDER_DPS = "DPS";//商品已送达
    public static final String TYPE_ORDER_OMJ = "OMJ";//商家已接单
    public static final String TYPE_ORDER_SPS = "SPS";//配送员送货中
    public static final String TYPE_ORDER_OC = "OC";//订单已取消

    //TODO
    public static final String TYPE_ORDER_TK = "S";//待评价
    public static final String TYPE_ORDER_PJ = "PJ";//退款

//    ALL->全部；WAIT_PAY->待支付；RECEIVED->已收货；WAIT_COMMENT->待评价；REFUNDED->退款

    /**
     *
     public static final String TYPE_ORDER_WP = "WP";//订单待支付
     public static final String TYPE_ORDER_DPS = "DPS";//商品已送达

     public static final String TYPE_ORDER_TK = "S";//待评价
     public static final String TYPE_ORDER_PJ = "SS";//退款

     public static final String TYPE_ORDER_OMJ = "OMJ";//商家已接单
     public static final String TYPE_ORDER_SPS = "SPS";//配送员送货中
     public static final String TYPE_ORDER_OC = "OC";//订单已取消
     */
    //全部
    public static final String TYPE_ORDER_ALL = "ALL";
    //待支付
    public static final String TYPE_ORDER_WAIT_PAY = "WAIT_PAY";
    //待收货
    public static final String TYPE_ORDER_WAIT_RECEIVED = "WAIT_RECEIVED";
    //已收货
    public static final String TYPE_ORDER_RECEIVED = "RECEIVED";
    //待评价
    public static final String TYPE_ORDER_WAIT_COMMENT = "WAIT_COMMENT";
    // 退款
    public static final String TYPE_ORDER_REFUNDED = "REFUNDED";



    public static final int Delay_Reflush_false = 400;
    public static final int Delay_Reflush_true = 100;
    public static final String PDF_Common = "common";


    public static final int REQUEST_CODE_ORDERING = 4;//订单备注
    public static final int RESULT_CODE_ORDERING = 5;//订单备注
    public static final String TEXT_BEIZHU = "beizhu";//订单备注


    public static final int REQUEST_CODE_USERINFO = 6;//用户信息
    public static final int RESULT_CODE_USERINFO = 7;//用户信息



    // 首页banner1
    public static final String BANNER_TYPE_HOME_TOP = "app-index-top";
    // 首页banner2
    public static final String BANNER_TYPE_HOME_MIDELE = "app-index-middle";
    // 我的页面banner2
    public static final String BANNER_TYPE_MINE = "app-my";
}
