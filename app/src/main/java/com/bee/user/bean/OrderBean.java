package com.bee.user.bean;

import com.bee.user.Constants;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  14：19
 * 描述：
 */
public class OrderBean implements MultiItemEntity, Serializable {

    public  static final int type1 = 0;
    public  static final int type2 = 1;

    @Override
    public int getItemType() {
        if(status == Constants.TYPE_PAY_WAITE ||
                status == Constants.TYPE_READY ||
                status == Constants.TYPE_PEISONG ){
            return type2;
        }
        return type1;
    }

    public int getOrderItemType(){
        return status;
    }

    /**
     * id : 14
     * memberId : 38
     * orderSn : 20210520173621117050138
     * sourceType : 5
     * orderType : 1
     * totalAmount : 19
     * payAmount : 19
     * feightTemplateDetailId : null
     * freightAmount : null
     * status : 0
     * isPay : null
     * paymentTime : null
     * payType : 1
     * promotionInfo : null
     * couponId : null
     * promotionAmount : null
     * integrationAmount : null
     * couponAmount : null
     * discountAmount : null
     * autoConfirmDay : null
     * integration : null
     * growth : null
     * billType : null
     * billHeader : null
     * billContent : null
     * billReceiverPhone : null
     * billReceiverEmail : null
     * receiverName : 18721893801
     * receiverPhone : 18721893801
     * receiverPostCode : 021
     * receiverDetailAddress : 上海市长宁区玛瑙路1438-4-48号靠近上海高岛屋百货
     * note : null
     * deleteStatus : null
     * useIntegration : null
     * deliverySn : null
     * deliveryTime : null
     * confirmStatus : null
     * receiveTime : null
     * commentTime : null
     * modifyTime : null
     * buildingId : null
     * buildingName : null
     * storeId : null
     * storeName : null
     * comment : null
     * receiverProvince : null
     * receiverCity : null
     * receiverRegion : null
     * pickupWay : null
     * createBy : null
     * createName : null
     * createTime : 2021-05-20T09:36:21.000+00:00
     * updateBy : null
     * updateName : null
     * updateTime : null
     * blance : null
     * orderItemList : [{"id":27,"orderId":14,"orderSn":"20210520173621117050138","productId":26,"productPic":"http://quxianfeng.oss-cn-shanghai.aliyuncs.com/13d25a3e-499e-445d-ba87-0e14d8ff797b?Expires=33147301674&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=fLzy5PIEEkNPoeJ9k9OUQs1El3A%3D","productName":"石锅拌饭","productBrand":"","productSn":"sgbf123213","productPrice":19,"productQuantity":1,"productSkuId":1134,"productSkuCode":"sgbf001","productCategoryId":11,"sp1":null,"sp2":null,"sp3":null,"productAttr":null,"promotionName":null,"promotionAmount":0,"couponAmount":0,"integrationAmount":0,"realAmount":0,"giftIntegration":0,"giftGrowth":0,"storeId":17,"storeName":"东北水饺","status":null,"type":1,"createBy":38,"createName":"admin","createTime":"2021-05-20T09:36:21.117+00:00","updateBy":38,"updateName":"admin","updateTime":"2021-05-20T09:36:21.000+00:00"}]
     * historyList : null
     */

    public int id;
    public int memberId;
    public String orderSn;
    public int sourceType;
    public int orderType;
    public int totalAmount;
    public int payAmount;
    public Object feightTemplateDetailId;
    public Object freightAmount;
    public int status;
    public Object isPay;
    public Object paymentTime;
    public int payType;
    public Object promotionInfo;
    public Object couponId;
    public Object promotionAmount;
    public Object integrationAmount;
    public Object couponAmount;
    public Object discountAmount;
    public Object autoConfirmDay;
    public Object integration;
    public Object growth;
    public Object billType;
    public Object billHeader;
    public Object billContent;
    public Object billReceiverPhone;
    public Object billReceiverEmail;
    public String receiverName;
    public String receiverPhone;
    public String receiverPostCode;
    public String receiverDetailAddress;
    public Object note;
    public Object deleteStatus;
    public Object useIntegration;
    public Object deliverySn;
    public Object deliveryTime;
    public Object confirmStatus;
    public Object receiveTime;
    public Object commentTime;
    public Object modifyTime;
    public Object buildingId;
    public Object buildingName;
    public Object storeId;
    public Object storeName;
    public Object comment;
    public Object receiverProvince;
    public Object receiverCity;
    public Object receiverRegion;
    public Object pickupWay;
    public Object createBy;
    public Object createName;
    public String createTime;
    public Object updateBy;
    public Object updateName;
    public Object updateTime;
    public Object blance;
    public Object historyList;
    public List<OrderItemBean> orderItemList;



    public static class  OrderItemBean implements  Serializable {
        /**
         * id : 27
         * orderId : 14
         * orderSn : 20210520173621117050138
         * productId : 26
         * productPic : http://quxianfeng.oss-cn-shanghai.aliyuncs.com/13d25a3e-499e-445d-ba87-0e14d8ff797b?Expires=33147301674&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=fLzy5PIEEkNPoeJ9k9OUQs1El3A%3D
         * productName : 石锅拌饭
         * productBrand :
         * productSn : sgbf123213
         * productPrice : 19
         * productQuantity : 1
         * productSkuId : 1134
         * productSkuCode : sgbf001
         * productCategoryId : 11
         * sp1 : null
         * sp2 : null
         * sp3 : null
         * productAttr : null
         * promotionName : null
         * promotionAmount : 0
         * couponAmount : 0
         * integrationAmount : 0
         * realAmount : 0
         * giftIntegration : 0
         * giftGrowth : 0
         * storeId : 17
         * storeName : 东北水饺
         * status : null
         * type : 1
         * createBy : 38
         * createName : admin
         * createTime : 2021-05-20T09:36:21.117+00:00
         * updateBy : 38
         * updateName : admin
         * updateTime : 2021-05-20T09:36:21.000+00:00
         */

        public int id;
        public int orderId;
        public String orderSn;
        public int productId;
        public String productPic;
        public String productName;
        public String productBrand;
        public String productSn;
        public int productPrice;
        public int productQuantity;
        public int productSkuId;
        public String productSkuCode;
        public int productCategoryId;
        public Object sp1;
        public Object sp2;
        public Object sp3;
        public Object productAttr;
        public Object promotionName;
        public int promotionAmount;
        public int couponAmount;
        public int integrationAmount;
        public int realAmount;
        public int giftIntegration;
        public int giftGrowth;
        public int storeId;
        public String storeName;
        public Object status;
        public int type;
        public int createBy;
        public String createName;
        public String createTime;
        public int updateBy;
        public String updateName;
        public String updateTime;

    }
}
