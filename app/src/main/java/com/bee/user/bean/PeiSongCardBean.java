package com.bee.user.bean;

import java.util.Date;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/21  12：38
 * 描述：
 */
public class PeiSongCardBean {

    public long id;
    public String cardName;
    public String cardType;
//coupon_type '卡类型[M.月卡 Q.季卡 Y.年卡 SM.连续包月卡 SQ.连续包季卡 SY.连续包年卡]',
    public String couponType;
    public Integer faceValue;
    public Integer freeValue;
    public Object minAmount;
    public String exchangeDays;
    public Integer platform;
    public Integer publishNumber;
    public Integer personLimit;
    public Integer sort;
    public Object memo;
    public String backgroudImageUrl;
    public Date expireTime;
    public String expireTimeDesc;
    public String remainDays;
//    0-正常，1-已过期
    public Integer status;
    public Integer delFlag;
    public Integer createBy;
    public String createName;
    public String createTime;
    public Integer updateBy;
    public String updateName;
    public String updateTime;

    public String averagePrice;
}
