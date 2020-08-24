package com.huaxiafinance.lc.bottomindicator;

import android.content.res.ColorStateList;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by chenqun on 2017/12/25.
 */

public class MenuItemBean {
    public final int width;
    public final int height;
    public String title;
    public StateListDrawable drawable;
    public ColorStateList textColor;

    public MenuItemBean(String title, StateListDrawable drawable, ColorStateList textColor, int width, int height) {
        this.title = title;
        this.drawable = drawable;
        this.textColor = textColor;

        this.width = width;
        this.height = height;
    }
}
