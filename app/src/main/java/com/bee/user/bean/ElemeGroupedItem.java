package com.bee.user.bean;

import com.kunminx.linkage.bean.BaseGroupedItem;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/27  17：23
 * 描述：
 */
public class ElemeGroupedItem extends BaseGroupedItem<ElemeGroupedItem.ItemInfo> {

    public ElemeGroupedItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public ElemeGroupedItem(ItemInfo item) {
        super(item);
    }

    public static class ItemInfo extends BaseGroupedItem.ItemInfo {
        private final StoreFoodItem2Bean bean;
        public int num = 0;

        public ItemInfo(String title, String group,StoreFoodItem2Bean bean) {
            super(title, group);
            this.bean = bean;
        }

        public StoreFoodItem2Bean getBean() {
            return bean;
        }
    }
}
