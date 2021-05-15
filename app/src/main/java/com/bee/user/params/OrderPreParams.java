package com.bee.user.params;

/**
 * 创建人：进京赶考
 * 创建时间：2021/04/10  18：59
 * 描述：
 */
public class OrderPreParams {
//    {
//        addressId	integer($int64)
//            收货地址id
//
//        cartIds	[
//                商品购物车Ids
//
//        integer($int64)]
//        memberId	integer($int64)
//            会员Id
//
//        offline	integer($int32)
//            0.送货;1.自取
//
//        orderType	integer($int32)
//            订单类型:1.普通订单;2.秒杀订单(暂不支持);5.积分订单(暂不支持)
//
//        source	integer($int32)
//            订单来源:0.PC订单;1.商品详情;2.h5;3.微信小程序;4.支付宝小程序;5.app订单
//
//    }

    public Integer addressId = 0;
    public int[] cartIds;
    public Integer memberId;
    public Integer offline;
    public Integer orderType;
    public Integer source;


}
