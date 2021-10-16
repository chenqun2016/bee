package com.bee.user.event;

/**
 * 创建时间：2021/6/6
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class ChartFragmentEvent {
    public static final int TYPE_REFLUSH = 2;

    public final int type;
    public int money;

    public ChartFragmentEvent(int type,int money) {
        this.money = money;
        this.type = type;
    }

    public ChartFragmentEvent(int type) {
        this.type = type;
    }
}
