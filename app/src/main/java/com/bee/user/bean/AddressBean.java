package com.bee.user.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  18：19
 * 描述：
 */
public class AddressBean implements MultiItemEntity, Serializable {

    public  static final int type1 = 0;
    public  static final int type2 = 1;

    public int viewType = type1;

    public boolean isSelected = false;

    @Override
    public int getItemType() {
        return viewType;
    }


    public int type = 0;

    public AddressBean() {
    }

    public AddressBean(int type) {
        this.type = type;
    }
}