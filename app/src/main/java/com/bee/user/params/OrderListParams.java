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
        public String status;
        public int sourceType = 1;
        public OrderListInnerDataClass(String status) {
            this.status = status;
        }
    }
}
