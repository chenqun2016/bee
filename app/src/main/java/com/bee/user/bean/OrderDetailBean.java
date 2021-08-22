package com.bee.user.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建时间：2021/6/19
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class OrderDetailBean implements Serializable {

    public Integer id;
    public Integer memberId;
    public String orderSn;
    public Integer sourceType;
    public Integer orderType;
    public Integer totalAmount;
    public Integer payAmount;
    public Integer feightTemplateDetailId;
    public Integer freightAmount;
    public String status;
    public Object isPay;
    public Object paymentTime;
    public Integer payType;
    public Object promotionInfo;
    public Object couponId;
    public Integer promotionAmount;
    public Integer integrationAmount;
    public Integer couponAmount;
    public Integer discountAmount;
    public Object autoConfirmDay;
    public Integer integration;
    public Integer growth;
    public Integer billType;
    public Object billHeader;
    public Object billContent;
    public Object billReceiverPhone;
    public Object billReceiverEmail;
    public String receiverName;
    public String receiverPhone;
    public String receiverPostCode;
    public String receiverDetailAddress;
    public String note;
    public Integer deleteStatus;
    public Object useIntegration;
    public Object deliverySn;
    public Object deliveryTime;
    public Integer confirmStatus;
    public Object receiveTime;
    public Object commentTime;
    public Object modifyTime;
    public Integer buildingId;
    public String buildingName;
    public Object storeId;
    public String storeName;
    public Boolean comment;
    public String receiverProvince;
    public String receiverCity;
    public String receiverRegion;
    public Integer pickupWay;
    public Integer createBy;
    public String createName;
    public Date createTime;
    public Integer updateBy;
    public String updateName;
    public String updateTime;
    public Object blance;
    public List<FoodBean> orderItemList;
    public Object historyList;

}
