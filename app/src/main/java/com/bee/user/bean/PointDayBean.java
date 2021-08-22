package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/14  16：24
 * 描述：
 */
public class PointDayBean {

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getIsSignIn() {
        return isSignIn;
    }

    public void setIsSignIn(int isSignIn) {
        this.isSignIn = isSignIn;
    }

    public int dayNum;

    public  int points;

    public int isSignIn;
}
