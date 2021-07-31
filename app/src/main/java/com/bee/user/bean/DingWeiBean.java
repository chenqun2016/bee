package com.bee.user.bean;

import java.io.Serializable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  17：10
 * 描述：
 */
public class DingWeiBean implements Serializable {

//    {
//        "id":12,
//            "buildingAreaId":null,
//            "name":"国际广场B座",
//            "address":"上海黄浦区东台路279号",
//            "leader":"王可",
//            "phone":"123123123",
//            "cityCode":"",
//            "nearbyLandmarks":"",
//            "latitude":"31.240072",
//            "longitude":"121.519789",
//            "remark":"",
//            "sort":1,
//            "delFlag":0,
//            "createBy":-2,
//            "createName":"admin",
//            "createTime":"2021-03-10T08:14:45.000+00:00",
//            "updateBy":-2,
//            "updateName":"admin",
//            "updateTime":"2021-03-10T08:14:45.000+00:00"
//    }
    public Integer id;
    public Object buildingAreaId;
    public String name;
    public String address;
    public String leader;
    public String phone;
    public String cityCode;
    public String nearbyLandmarks;
    public String latitude;
    public String longitude;
    public String remark;
    public Integer sort;
    public Integer delFlag;
    public Integer createBy;
    public String createName;
    public String createTime;
    public Integer updateBy;
    public String updateName;
    public String updateTime;
}
