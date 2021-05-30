package com.bee.user.params;

/**
 * 创建时间：2021/5/30
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class OrderListParams {
    public int pageNum;
    public int pageSize;
    public OrderListInnerDataClass data;

    public static class OrderListInnerDataClass{
        public int status;

        public OrderListInnerDataClass(int status) {
            this.status = status;
        }
    }
}
