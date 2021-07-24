package com.bee.user.bean;

import java.util.List;

/**
 * 创建时间：2021/7/24
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class OrderingBean {

    public List<OrderListBean> orderList;

    public static class OrderListBean {
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
        public Integer storeId;
        public String storeName;
        public Object comment;
        public String receiverProvince;
        public String receiverCity;
        public String receiverRegion;
        public Integer pickupWay;
        public Integer createBy;
        public String createName;
        public String createTime;
        public Integer updateBy;
        public String updateName;
        public String updateTime;
        public Object blance;
        public List<OrderItemListBean> orderItemList;
        public Object historyList;

        public static class OrderItemListBean {
            public Integer id;
            public Integer orderId;
            public String orderSn;
            public Integer productId;
            public String productPic;
            public String productName;
            public String productBrand;
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
            public String updateTime;
        }
    }
}
