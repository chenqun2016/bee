package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/21  16：07
 * 描述：
 */
public class OrderGridviewItemBean {
    public  static  final int TYPE_reOrder = 0;//再来一单
    public  static  final int TYPE_comment = 1;//评价得积分
    public  static  final int TYPE_shouhou = 2;//申请售后
    public  static  final int TYPE_contact_shop = 3;//联系商家
    public  static  final int TYPE_contact_rider = 4;//联系骑手
    public  static  final int TYPE_pay_now = 5;//立即支付
    public  static  final int TYPE_cancel_Order = 6;//取消订单
    public  static  final int TYPE_cancel_Order_beihuo = 7;//取消订单_正在备货

    public  static  final int TYPE_cuidan = 8;//催单
    public  static  final int TYPE_change_order = 9;//改订单信息

    public int type = 0;

    public int icon = 0;
    public String text = "";
    public int text_color = 0;

    public OrderGridviewItemBean(int type, int icon, String text, int text_color) {
        this.type = type;
        this.icon = icon;
        this.text = text;
        this.text_color = text_color;
    }
}
