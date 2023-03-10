package com.bee.user.bean;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2021/05/08  15：30
 * 描述：
 */
public class StoreFoodItem2Bean {
    public String name;
    public List<AttributeListBean> attributeList;
    public int cartItemId;
    public int cartQuantity;
    public String description;
    public String endTime;
    public Integer endTimeSecond;
    public Integer flashSaleGoodsId;
    public Integer flashSaleStatus;
    public String pic;
    public int price;
    public Integer originalPrice;
    public Integer productAttributeCategoryId;
    public Integer productId;
    public int sale;
    public String salePrice;
    public Integer shopProductCategoryId;
    public String shopProductCategoryName;
    public int shopProductId;
    public int storeId;
    //    所属店铺
    public int skuId;
    public List<SkuListBean> skuList;
    public String skuName;
    public String startTime;
    public Integer stock;
    public String subTitle;
    public String unit;
    public Integer weight;
    public String tags;

    public static class AttributeListBean {
        public String createTime;
        public Integer filterType;
        public Integer handAddStatus;
        public Integer id;
        public String inputList;
        public Integer inputType;
        public String name;
        public Integer productAttributeCategoryId;
        public Integer relatedStatus;
        public Integer searchType;
        public Integer selectType;
        public Integer sort;
        public Integer status;
        public Integer type;
    }

    public static class SkuListBean {
        public int cartItemId;
        public int cartQuantity;
        public String endTime;
        public int endTimeSecond;
        public int flashSaleGoodsId;
        public int flashSaleStatus;
        public int orginPrice;
        public int packingFee;
        public int price;
        public int productId;
        public String salePrice;
        public int shopProductId;
        public String skuCode;
        public int skuId;
        public String skuName;
        public int skuStockId;
        public String startTime;
        public int stock;
    }
}
