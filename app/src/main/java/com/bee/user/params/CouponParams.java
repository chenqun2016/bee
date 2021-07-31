package com.bee.user.params;

/**
 * 创建时间：2021/7/31
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class CouponParams {
    public DataBean data;
    public Integer pageNum;
    public Integer pageSize;

    public static class DataBean {
        //状态 0->未使用；1->已使用;2->已失效
        public Integer status;

        public DataBean(Integer status) {
            this.status = status;
        }
    }
}
