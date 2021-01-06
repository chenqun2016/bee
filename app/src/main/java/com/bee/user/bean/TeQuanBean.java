package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/06  22：35
 * 描述：
 */
public class TeQuanBean {
    private int resouse;
    private String text;

    public TeQuanBean(int resouse, String text) {
        this.resouse = resouse;
        this.text = text;
    }

    public int getResouse() {
        return resouse;
    }

    public String getText() {
        return text;
    }
}
