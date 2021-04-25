package com.bee.user.bean;

import java.math.BigDecimal;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/12  19：41
 * 描述：
 */
public class StoreFoodItemBean {

    /**
     * id : 100
     * storeId : 9
     * productId : 52
     * productName : 红烧羊肉排骨面
     * productCategoryId : 9
     * productCategoryName : 美食 > 面
     * productPicUrl : http://quxianfeng.oss-cn-shanghai.aliyuncs.com/f5e1d9ed-9b33-4a17-bc84-84eec468e3fd?Expires=33142301301&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=MV14AtZqq9F0coSLDPeTrAOX3hI%3D
     * price : 50
     * monthSalesCount : 60
     * praiseRate : 90.6
     */

    public int quantity;

    private int id;
    private int storeId;
    private int productId;
    private String productName;
    private int productCategoryId;
    private String productCategoryName;
    private String productPicUrl;
    private BigDecimal price;
    private int monthSalesCount;
    private double praiseRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getProductPicUrl() {
        return productPicUrl;
    }

    public void setProductPicUrl(String productPicUrl) {
        this.productPicUrl = productPicUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMonthSalesCount() {
        return monthSalesCount;
    }

    public void setMonthSalesCount(int monthSalesCount) {
        this.monthSalesCount = monthSalesCount;
    }

    public double getPraiseRate() {
        return praiseRate;
    }

    public void setPraiseRate(double praiseRate) {
        this.praiseRate = praiseRate;
    }
}
