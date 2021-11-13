package com.bee.user.bean;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2021/05/08  17：05
 * 描述：
 */
public class FoodDetailBean {

    public Integer skuId;
    public String skuName;
    public String pic;
    public Integer sale;
    public Integer price;
    public Integer originalPrice;
    public String subTitle;
    public String description;
    public Object distance;
    public String saleStr;
    public Object tags;
    public String stockStr;
    public Integer stock;
    public Object unit;
    public Object weight;
    public Integer storeId;
    public Integer productId;
    public Integer shopProductId;
    public Integer shopProductCategoryId;
    public String shopProductCategoryName;
    public Integer productAttributeCategoryId;
    public Integer cartItemId;
    public Integer cartQuantity;
    public Integer flashSaleStatus;
    public Integer flashSaleGoodsId;
    public Integer salePrice;
    public Integer startTime;
    public Integer endTime;
    public Integer endTimeSecond;
    public List<StoreFoodItem2Bean.AttributeListBean> attributeList;
    public List<StoreFoodItem2Bean.SkuListBean> skuList;
    public Integer newStatus;
    public Integer recommandStatus;
    public String detailTitle;
    public String detailDesc;
    public String storeLogo;
}
