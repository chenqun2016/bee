package com.bee.user.bean;

import java.io.Serializable;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/15  22：52
 * 描述：
 */
public class ChartBean implements Serializable {
    public  boolean isSelected = false;

    public boolean selectedAll = false;

    //1 可以配送，0 不可配送
    public int distributionStatus;
    private Integer id;
    private Integer productId;
    private Integer productSkuId;
    private Integer memberId;
    private String memberNickname;
    private int quantity;
    private Integer price;
    private Double packingFee;
    private Integer buildingAreaId;
    private String buildingAreaName;
    private String sp1;
    private String sp2;
    private String sp3;
    private Object productAttr;
    private String productPic;
    private String productName;
    private Object productSubTitle;
    private String productSkuCode;
    private Integer productCategoryId;
    private String productBrand;
    private String productSn;
    private Integer storeId;
    private String storeName;
    private String createDate;
    private String modifyDate;
    private Integer deleteStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Integer productSkuId) {
        this.productSkuId = productSkuId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberNickname() {
        return memberNickname;
    }

    public void setMemberNickname(String memberNickname) {
        this.memberNickname = memberNickname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Double getPackingFee() {
        return packingFee;
    }

    public void setPackingFee(Double packingFee) {
        this.packingFee = packingFee;
    }

    public Integer getBuildingAreaId() {
        return buildingAreaId;
    }

    public void setBuildingAreaId(Integer buildingAreaId) {
        this.buildingAreaId = buildingAreaId;
    }

    public String getBuildingAreaName() {
        return buildingAreaName;
    }

    public void setBuildingAreaName(String buildingAreaName) {
        this.buildingAreaName = buildingAreaName;
    }

    public String getSp1() {
        return sp1;
    }

    public void setSp1(String sp1) {
        this.sp1 = sp1;
    }

    public String getSp2() {
        return sp2;
    }

    public void setSp2(String sp2) {
        this.sp2 = sp2;
    }

    public String getSp3() {
        return sp3;
    }

    public void setSp3(String sp3) {
        this.sp3 = sp3;
    }

    public Object getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(Object productAttr) {
        this.productAttr = productAttr;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Object getProductSubTitle() {
        return productSubTitle;
    }

    public void setProductSubTitle(Object productSubTitle) {
        this.productSubTitle = productSubTitle;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
