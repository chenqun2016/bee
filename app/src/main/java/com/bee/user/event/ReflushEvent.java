package com.bee.user.event;

/**
 * 创建时间：2021/7/15
 * 编写人： 陈陈陈
 * 功能描述：刷新用的Event
 */
public class ReflushEvent {

    /**
     * 刷新米粒相关页面：
     * from:  1：米粒详情页
     * to: 1：米粒详情页，2：首页我的tab
     */
    public static final int TYPE_REFLUSH_MILI = 0;
    /**
     * 退出登录刷新：
     * from:  1：设置页面。2：设置密码页面
     * to: MineFragment
     */
    public static final int TYPE_REFLUSH_EXIT_LOGIN = 1;
    /**
     * 登录刷新：
     * from:  1：设置页面。2：设置密码页面
     * to: MineFragment
     */
    public static final int TYPE_REFLUSH_LOGIN = 2;


    public int type;

    public String data;

    public ReflushEvent(int type) {
        this.type = type;
    }

    public ReflushEvent(int type, String data) {
        this.type = type;
        this.data = data;
    }
}
