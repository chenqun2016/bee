package com.bee.user.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/14  21：35
 * 描述：
 */
public class AddChartBean implements Serializable {
    public  boolean isTagStyle;//选规格模式
    public  int num;//购买数量
    public  int skuId;//商品skuId
    public  int storeId;//店铺Id
    public  int cartItemId;//购物车id
    public  BigDecimal money;
    public  ChartBean data;
    public  StoreFoodItem2Bean data2;
   public  String tags;
   public int indexForList;


    public AddChartBean(boolean isTagStyle ,int num, int skuId, int storeId, BigDecimal money,int cartItemId,ChartBean data,StoreFoodItem2Bean data2) {
        this.isTagStyle = isTagStyle;
        this.num = num;
        this.skuId = skuId;
        this.storeId = storeId;
        this.money = money;
        this.cartItemId = cartItemId;
        this.data = data;
        this.data2 = data2;
    }
}
