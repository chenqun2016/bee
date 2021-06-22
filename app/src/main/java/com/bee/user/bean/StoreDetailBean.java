package com.bee.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/12  19：16
 * 描述：
 */
public class StoreDetailBean implements Serializable {

    /**
     * id : 9
     * name : 便当达人
     * logoUrl : http://quxianfeng.oss-cn-shanghai.aliyuncs.com/d55a40c6-33bd-46a4-9d34-2b90a13a0117?Expires=33142986800&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=Iq%2B%2FESOv%2B4zlokdMxWIQrc%2Bea2w%3D
     * businessTimes : 12312
     * brandId : 12
     * brandName : 品牌名占位
     * storeLabel : 123123
     * contactName : rwer
     * contactMobile : 1123213
     * monthSalesCount :
     * distance :
     * duration :
     * status : 1
     * description : dsfsdfdsfdsfdsfdsf
     * latitude : 29.538000107
     * longitude : 122.1329422
     * categoryVOS : [{"id":2,"storeId":9,"productCategoryId":12,"productCategoryName":"西餐","sort":null}]
     * pictureVOS : null
     */

    private int id;
    private String name;
    private String logoUrl;
    private String appBackgroudUrl;
    private String businessTimes;
    private int brandId;
    private String brandName;
    private String storeLabel;
    private String contactName;
    private String contactMobile;
    private String monthSalesCount;
    private String distance;
    private String duration;
    private int status;
    private String description;
    private String latitude;
    private String longitude;
    private Object pictureVOS;
    private List<CategoryVOSBean> categoryVOS;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getBusinessTimes() {
        return businessTimes;
    }

    public void setBusinessTimes(String businessTimes) {
        this.businessTimes = businessTimes;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStoreLabel() {
        return storeLabel;
    }

    public void setStoreLabel(String storeLabel) {
        this.storeLabel = storeLabel;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getMonthSalesCount() {
        return monthSalesCount;
    }

    public void setMonthSalesCount(String monthSalesCount) {
        this.monthSalesCount = monthSalesCount;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Object getPictureVOS() {
        return pictureVOS;
    }

    public void setPictureVOS(Object pictureVOS) {
        this.pictureVOS = pictureVOS;
    }

    public List<CategoryVOSBean> getCategoryVOS() {
        return categoryVOS;
    }

    public void setCategoryVOS(List<CategoryVOSBean> categoryVOS) {
        this.categoryVOS = categoryVOS;
    }

    public String getAppBackgroundUrl() {
        return appBackgroudUrl;
    }

    public void setAppBackgroundUrl(String appBackgroundUrl) {
        this.appBackgroudUrl = appBackgroundUrl;
    }

    public static class CategoryVOSBean implements Serializable{
        /**
         * id : 2
         * storeId : 9
         * productCategoryId : 12
         * productCategoryName : 西餐
         * sort : null
         */

        private int id;
        private int storeId;
        private int productCategoryId;
        private String productCategoryName;
        private Object sort;

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

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }
    }
}
