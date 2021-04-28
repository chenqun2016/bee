package com.bee.user.bean;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/12  19：06
 * 描述：
 */
public class StoreListBean {


    private List<RecordsBean> records;
    private Integer total;
    private Integer size;
    private Integer current;
    private List<?> orders;
    private Boolean hitCount;
    private Boolean searchCount;
    private Integer pages;

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public List<?> getOrders() {
        return orders;
    }

    public void setOrders(List<?> orders) {
        this.orders = orders;
    }

    public Boolean getHitCount() {
        return hitCount;
    }

    public void setHitCount(Boolean hitCount) {
        this.hitCount = hitCount;
    }

    public Boolean getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Boolean searchCount) {
        this.searchCount = searchCount;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public static class RecordsBean {
        public Integer id;
        public String name;
        public String logoUrl;
        public String brandId;
        public String brandName;
        public String monthSalesCount;
        public String distance;
        public String duration;
        public String status;
        public Object permissionCategoryList;
        public List<ProductsBean> products;


        public static class ProductsBean {
            public Integer brandId;
            public String brandName;
            public Object feightTemplateId;
            public Integer productAttributeCategoryId;
            public String name;
            public String pic;
            public String productSn;
            public Integer deleteStatus;
            public Integer publishStatus;
            public Integer newStatus;
            public Integer recommandStatus;
            public Integer verifyStatus;
            public Integer sort;
            public Integer sale;
            public Integer price;
            public Integer originalPrice;
            public Integer giftGrowth;
            public Integer giftPoint;
            public Integer usePointLimit;
            public String subTitle;
            public String description;
            public Integer stock;
            public Integer lowStock;
            public String unit;
            public Integer weight;
            public Integer previewStatus;
            public String serviceIds;
            public String note;
            public String detailTitle;
            public String detailDesc;
            public String keywords;
            public Integer promotionType;
            public String createTime;
            public Integer type;
            public Object expireTime;
            public Integer id;
            public Integer shopProductId;
            public Integer shopProductCategoryId;
            public String shopProductCategoryName;
            public String showStatus;


        }
    }
}
