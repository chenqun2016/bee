package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/15  20：25
 * 描述：
 */
public class ChooseTimeBean {
    public boolean choosed = false;

    public ChooseTimeBean(String time) {
        this.time = time;
    }

    public String time;
}
