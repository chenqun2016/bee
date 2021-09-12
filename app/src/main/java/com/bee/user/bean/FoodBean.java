package com.bee.user.bean;

import java.io.Serializable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/25  21：21
 * 描述：
 */
public class FoodBean implements Serializable {
    public  boolean isSelected = false;

    public int commentType = -1;


    public Integer id;
    public Integer orderId;
    public String orderSn;
    public Integer productId;
    public String productPic;
    public String productName;
    public Object productBrand;
    public String productSn;
    public Integer productPrice;
    public Integer productQuantity;
    public Integer productSkuId;
    public String productSkuCode;
    public Integer productCategoryId;
    public Object sp1;
    public Object sp2;
    public Object sp3;
    public Object productAttr;
    public Object promotionName;
    public Integer promotionAmount;
    public Integer couponAmount;
    public Integer integrationAmount;
    public Integer realAmount;
    public Integer giftIntegration;
    public Integer giftGrowth;
    public Integer storeId;
    public String storeName;
    public Object status;
    public Integer type;
    public Integer createBy;
    public String createName;
    public String createTime;
    public Integer updateBy;
    public String updateName;
    public String updateTime;
}
