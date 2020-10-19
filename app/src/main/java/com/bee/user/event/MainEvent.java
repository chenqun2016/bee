package com.bee.user.event;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/22  17：05
 * 描述：
 */
public class MainEvent {
    public static final int TYPE_login = 0;
    public static final int TYPE_set_index = 1;

    public int index = 0;
    public int TYPE = 0;

    public MainEvent(int TYPE) {
        this.TYPE = TYPE;
    }
}
