package com.bee.user.bean;

import java.util.List;

/**
 * 创建时间：2021/12/12
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class StoreDetailFullBean {
    public Integer id;
    public String name;
    public String logoUrl;
    public String appBackgroudUrl;
    public String businessTimes;
    public Integer brandId;
    public String brandName;
    public String storeLabel;
    public String contactName;
    public String contactMobile;
    public Object monthSalesCount;
    public Object distance;
    public Object duration;
    public Integer status;
    public String description;
    public String latitude;
    public String longitude;
    public Integer feightTemplateId;
    public Object categoryVOS;
    public List<ShopCategorysBean> shopCategorys;
    public Object pictureVOS;

    public static class ShopCategorysBean {
        public Integer id;
        public Integer storeId;
        public Integer parentId;
        public String name;
        public Integer navStatus;
        public Integer showStatus;
        public Integer indexStatus;
        public Integer sort;
        public Object icon;
    }
}
