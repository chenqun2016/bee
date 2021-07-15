package com.bee.user.event;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/27  18：51
 * 描述：
 */
public class CloseEvent {
    //关闭支付相关页面
    public static final int TYPE_PAY = 1000;
    //关闭登录相关页面
    public static final int TYPE_LOGIN = 1000;

    public int type;

    public CloseEvent(int type) {
        this.type = type;
    }
}
