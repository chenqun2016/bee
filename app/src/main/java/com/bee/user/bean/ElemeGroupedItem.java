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
        private StoreFoodItemBean bean;

        public ItemInfo(String title, String group,StoreFoodItemBean bean) {
            super(title, group);
            this.bean = bean;
        }

        public StoreFoodItemBean getBean() {
            return bean;
        }
    }
}
