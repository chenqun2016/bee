package com.bee.user.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  14：19
 * 描述：
 */
public class OrderBean implements MultiItemEntity, Serializable {

    public  static final int type1 = 0;
    public  static final int type2 = 1;

    public int type = type1;

    public OrderBean(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public static class OrderItemBean implements  Serializable{

    }
}
