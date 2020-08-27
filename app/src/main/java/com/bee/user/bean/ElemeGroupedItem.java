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
        private String content;
        private String imgUrl;
        private String cost;

        public ItemInfo(String title, String group, String content) {
            super(title, group);
            this.content = content;
        }

        public ItemInfo(String title, String group, String content, String imgUrl) {
            this(title, group, content);
            this.imgUrl = imgUrl;
        }

        public ItemInfo(String title, String group, String content, String imgUrl, String cost) {
            this(title, group, content, imgUrl);
            this.cost = cost;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }
    }
}
