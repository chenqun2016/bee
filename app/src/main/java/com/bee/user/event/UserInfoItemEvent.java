package com.bee.user.event;

/**
 * 创建人：进京赶考
 * 创建时间：2021/01/06  21：36
 * 描述：
 */
public class UserInfoItemEvent {

    public static final int TYPE_Name = 0;
    public static final int TYPE_Profession = 1;
    public static final int TYPE_Sign = 2;
    public static final int TYPE_Location = 3;

    private int type;


    private String text;

    public UserInfoItemEvent(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }
}
