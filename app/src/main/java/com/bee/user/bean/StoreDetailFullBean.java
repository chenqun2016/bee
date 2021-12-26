package com.bee.user.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 创建时间：2021/12/12
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class StoreDetailFullBean implements Serializable {
    /**
     * 店铺商家详情信息用这个接口，相比简要信息接口增加这些字段
     *
     *     @ApiModelProperty(value = "口味评分", position = 5)
     *     private String tasteStar;
     *
     *     @ApiModelProperty(value = "包装评分", position = 5)
     *     private String packStar;
     *
     *     @ApiModelProperty(value = "配送满意度", position = 5)
     *     private String deliverySatisfaction;
     *
     *     @ApiModelProperty(value = "商家图片列表", position = 11)
     *     private List<String> shopPicList;
     *
     *     @ApiModelProperty(value = "资质图片列表", position = 11)
     *     private List<String> certificatePicList;
     *
     *     @ApiModelProperty(value = "描述")
     *     private String description;
     *
     *     @ApiModelProperty(value = "联系电话")
     *     private String contactMobile;
     *
     *     @ApiModelProperty(value = "营业时间段")
     *     private JSONArray businessTimes;
     */
    public Integer storeId;
    public String name;
    public String logoUrl;
    public float star;
    public String saleStr;
    public Integer distance;
    public String distanceStr;
    public String latitude;
    public String longitude;
    public Integer status;
    public String appBackgroudUrl;


    public Integer favorites;
    public String tags;
    public String tasteStar;
    public String packStar;
    public String deliverySatisfaction;
    public List<String> shopPicList;
    public List<String> certificatePicList;
    public String description;
    public String contactMobile;
    public List<BusinessTimesBean> businessTimes;

    public static class BusinessTimesBean implements Serializable {
        public Integer weekday;
        public String weekdayName;
        public String startTime;
        public String endTime;
    }
}
