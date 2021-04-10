package com.bee.user.bean;

import java.math.BigDecimal;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/14  21：35
 * 描述：
 */
public class AddChartBean {

    public  int num;//购买数量
    public  int skuId;//商品skuId
    public  int storeId;//店铺Id

    public  BigDecimal money;

    public AddChartBean( int num, int skuId, int storeId, BigDecimal money) {
        this.num = num;
        this.skuId = skuId;
        this.storeId = storeId;
        this.money = money;
    }
}
