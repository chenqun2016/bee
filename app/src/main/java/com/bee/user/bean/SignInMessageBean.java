package com.bee.user.bean;

import java.util.List;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/8/22 下午2:25
 */
public class SignInMessageBean {


    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public int getMoreSignIns() {
        return moreSignIns;
    }

    public void setMoreSignIns(int moreSignIns) {
        this.moreSignIns = moreSignIns;
    }

    public List<PointDayBean> getSigns() {
        return signs;
    }

    public void setSigns(List<PointDayBean> signs) {
        this.signs = signs;
    }

    public int getIsNowSignIn() {
        return isNowSignIn;
    }

    public void setIsNowSignIn(int isNowSignIn) {
        this.isNowSignIn = isNowSignIn;
    }

    public String activityCode;

    public  int moreSignIns;

    public List<PointDayBean> signs;

    public int isNowSignIn;
}
