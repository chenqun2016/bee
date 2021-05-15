package com.bee.user.event;

/**
 * 创建时间：2021/5/15
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class OrderingEvent {
    public final String storeId;

    public OrderingEvent(String storeId) {
        this.storeId = storeId;
    }
}
