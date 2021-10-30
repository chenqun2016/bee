package com.bee.user.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/14  21：35
 * 描述：
 */
public class AddChartBean implements Serializable {

    public  int num;//购买数量
    public  int skuId;//商品skuId
    public  int storeId;//店铺Id
    public  int cartItemId;//购物车id
    public  BigDecimal money;
    public  StoreFoodItem2Bean data;

    public AddChartBean( int num, int skuId, int storeId, BigDecimal money,int cartItemId) {
        this.num = num;
        this.skuId = skuId;
        this.storeId = storeId;
        this.money = money;
        this.cartItemId = cartItemId;
    }

    public AddChartBean( int num, int skuId, int storeId, BigDecimal money,int cartItemId,StoreFoodItem2Bean data) {
        this.num = num;
        this.skuId = skuId;
        this.storeId = storeId;
        this.money = money;
        this.cartItemId = cartItemId;
        this.data = data;
    }
}
