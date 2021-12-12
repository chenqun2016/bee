package com.bee.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 - @Description: 
 - @Author: bxy
 - @Time:  2021/11/7 下午3:33
 */
public class GoodsBySectionBean implements Serializable {

    public List<RecordBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordBean> records) {
        this.records = records;
    }

    private List<RecordBean> records;
    public class RecordBean{
        public String getFlashSaleGoodsId() {
            return flashSaleGoodsId;
        }

        public void setFlashSaleGoodsId(String flashSaleGoodsId) {
            this.flashSaleGoodsId = flashSaleGoodsId;
        }

        public long getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(long goodsId) {
            this.goodsId = goodsId;
        }

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public long getSkuStockId() {
            return skuStockId;
        }

        public void setSkuStockId(long skuStockId) {
            this.skuStockId = skuStockId;
        }

        public int getShopProductId() {
            return shopProductId;
        }

        public void setShopProductId(int shopProductId) {
            this.shopProductId = shopProductId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getStoreLogo() {
            return storeLogo;
        }

        public void setStoreLogo(String storeLogo) {
            this.storeLogo = storeLogo;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public long getEndTimeSecond() {
            return endTimeSecond;
        }

        public void setEndTimeSecond(long endTimeSecond) {
            this.endTimeSecond = endTimeSecond;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getRemainNumber() {
            return remainNumber;
        }

        public void setRemainNumber(long remainNumber) {
            this.remainNumber = remainNumber;
        }

        public String getDistanceAndTime() {
            return distanceAndTime;
        }

        public void setDistanceAndTime(String distanceAndTime) {
            this.distanceAndTime = distanceAndTime;
        }

        public String getSale() {
            return sale;
        }

        public void setSale(String sale) {
            this.sale = sale;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public int getBuyMax() {
            return buyMax;
        }

        public void setBuyMax(int buyMax) {
            this.buyMax = buyMax;
        }

        private String flashSaleGoodsId;
        private long goodsId;
        private long skuId;
        private long skuStockId;
        private int shopProductId;
        private String productName;
        private String originalPrice;
        private String salePrice;
        private String pic;
        private String storeLogo;
        private String startTime;
        private String endTime;
        private long endTimeSecond;
        private int status;
        private long remainNumber;
        private String distanceAndTime;
        private String sale;
        private long amount;
        private int buyMax;

        public String getPrice() {
            return price;
        }

        private String price;

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        private String subTitle;

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        private int stock;
    }
/*    public  boolean isSelected = false;

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
    public String updateTime;*/
}
