package com.bee.user.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/05  21：14
 * 描述：
 */
public class YouhuiquanBean implements MultiItemEntity {

    public  static final int type1 = 0;
    public  static final int type2 = 1;

    public int viewType = type1;

    @Override
    public int getItemType() {
        return viewType;
    }


    public int type = 0;

    public YouhuiquanBean() {
    }

    public YouhuiquanBean(int type) {
        this.type = type;
    }
}
