package com.bee.user.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Date;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/05  21：14
 * 描述：
 */
public class YouhuiquanBean implements MultiItemEntity {

    public  static final int type1 = 0;
    public  static final int type2 = 1;

    public int viewType = type1;

    public boolean isSelected = false;

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



    public Integer id;
    public Integer configId;
    public String cardName;
    public String cardType;
    public Integer faceValue;
    public Integer freeValue;
    public Integer minAmount;
    public Integer platform;
    public Object cardNo;
    public Object password;
    public Integer memberId;
    public Date expireTime;
    public String memo;
    public Integer status;
    public Object createBy;
    public Object createName;
    public String createTime;
    public Object updateBy;
    public Object updateName;
    public String updateTime;
}
