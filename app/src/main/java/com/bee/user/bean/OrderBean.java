package com.bee.user.bean;

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
        return type2;
//        if(Constants.TYPE_ORDER_WP.equals(status) ||
//                Constants.TYPE_ORDER_OMJ.equals(status) ||
//                Constants.TYPE_ORDER_SPS.equals(status) ){
//            return type2;
//        }
//        return type1;
    }

    public String getOrderItemType(){
        return status;
    }

    public int id;
    public int storeId;
    public Object storeName;
    public Object deliveryTime;
    public String status;
    public String statusMark;
    public int totalAmount;
    public int productNumber;
    public List<OrderItemBean> productList;



    public static class  OrderItemBean implements  Serializable {

        public int id;
        public int productId;
        public String productPic;
        public String productName;

        public int storeId;
        public int type;
    }
}
