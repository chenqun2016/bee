package com.bee.user.params;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间：2021/5/15
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class OrderingParams implements Serializable {

    //支付密码
    public String payPassword;

    //收货地址id
    public long addressId;
    //商品购物车Id（操作类型:[1.商品详情]时为必要参数）
    public int cartItemId;
    //商品购物车Ids（操作类型:[2.商品详情]时为必要参数）
    public Integer[] cartItemIds;
    //配送模板 明细Id
    public List<FeightTemplateModel> feightTemplateModels ;
    //订单备注
    public String note;
    //操作类型:[1.商品详情;2.勾选购物车;3.全部购物车的商品;6.秒杀(暂不支持)]
    //1：cartItemId是必传参数
    //2：cartItemIds是必传参数
    //3：cartItemId，cartItemIds都不用传
    public String operationType ;
    //订单id: 待支付中用到
    public int orderId;
    //订单类型:[1.普通订单;2.秒杀订单(暂不支持);5.积分订单(暂不支持)]
    public int orderType ;
    //支付方式:[1.米粒]
    public int payType ;
    //取货方式:[1.配送 2.到店取货(暂不支持)]
    public int pickupWay;
    //订单来源:[0.PC订单;1.商品详情;2.h5;3.微信小程序;4.支付宝小程序;5.app订单]
    public int sourceType ;


    public static class FeightTemplateModel implements Serializable{
        public int feightTemplateDetailId;
        public int storeId;

        public FeightTemplateModel(int feightTemplateDetailId, int storeId) {
            this.feightTemplateDetailId = feightTemplateDetailId;
            this.storeId = storeId;
        }
    }
}
