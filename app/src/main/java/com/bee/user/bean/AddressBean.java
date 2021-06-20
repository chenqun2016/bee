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


    /**
     * id : 131
     * memberId : 38
     * name : 李四
     * phoneNumber : 21313
     * defaultStatus : 1
     * postCode : 213123
     * province : 324
     * city : 北京市
     * district : df
     * detailAddress : 第三方老师的反馈的说法
     * houseNumber : null
     * gender : null
     * tag : null
     * latitude : 123
     * longitude : 123
     */

    public int id;
    public int memberId;
    public String name;
    public String phoneNumber;
    public int defaultStatus;
    public String postCode;
    public String province;
    public String city;
    public String district;
    public String detailAddress;
    public String houseNumber;
    //性别 1：男 2：女
    public int gender;
    //标签 1：家 2：公司 3：学校
    public int tag;
    public String latitude;
    public String longitude;
}