package com.bee.user.event;

import android.content.Intent;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/30  17：17
 * 描述：
 */
public class StoreEvent {
    public static final int TYPE_START_ACTIVITY = 1;
    public int type;
    public Intent intent;
}
