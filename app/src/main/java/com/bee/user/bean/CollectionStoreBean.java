package com.bee.user.bean;

import java.util.List;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/28 下午3:25
 */
public class CollectionStoreBean {
    public List<RecordBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordBean> records) {
        this.records = records;
    }

    private List<RecordBean> records;
    public class RecordBean{
        public String getArriveDistance() {
            return arriveDistance;
        }

        public void setArriveDistance(String arriveDistance) {
            this.arriveDistance = arriveDistance;
        }

        public String getArriveMinutes() {
            return arriveMinutes;
        }

        public void setArriveMinutes(String arriveMinutes) {
            this.arriveMinutes = arriveMinutes;
        }

        public int getBizId() {
            return bizId;
        }

        public void setBizId(int bizId) {
            this.bizId = bizId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPictureUrl() {
            return pictureUrl;
        }

        public void setPictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSales() {
            return sales;
        }

        public void setSales(String sales) {
            this.sales = sales;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }

        private String arriveDistance;
        private String arriveMinutes;
        private int bizId;
        private String desc;
        private String name;
        private String pictureUrl;
        private String price;
        private String sales;
        private float score;

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        private int top;
    }

}
