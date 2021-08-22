package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/14  19：19
 * 描述：
 */
public class PointDetailBen {
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getPointsType() {
        return pointsType;
    }

    public void setPointsType(int pointsType) {
        this.pointsType = pointsType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPointsDate() {
        return pointsDate;
    }

    public void setPointsDate(String pointsDate) {
        this.pointsDate = pointsDate;
    }

    public String activityName;
    public int pointsType;
    public String createTime;
    public int points;
    public String pointsDate;

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String iconUrl;

}
