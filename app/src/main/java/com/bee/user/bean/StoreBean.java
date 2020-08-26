package com.bee.user.bean;

import java.io.Serializable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/25  22：47
 * 描述：
 */
public class StoreBean implements Serializable {

    public static class StoreTag implements Serializable {
        public String tag;
        public int type;

        public StoreTag(String tag, int type) {
            this.tag = tag;
            this.type = type;
        }
    }

    public static class StoreFood implements Serializable {
        public String name;
        public String money;

        public StoreFood(String name, String money) {
            this.name = name;
            this.money = money;
        }
    }
}
