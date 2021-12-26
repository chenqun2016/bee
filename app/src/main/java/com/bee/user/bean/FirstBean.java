package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/02  16：59
 * 描述：
 */
public class FirstBean {
    public FlashGoodBean getFlashGoods() {
        return flashGoods;
    }

    public void setFlashGoods(FlashGoodBean flashGoods) {
        this.flashGoods = flashGoods;
    }

    public ChoicenesBean getChoiceness() {
        return choiceness;
    }

    public void setChoiceness(ChoicenesBean choiceness) {
        this.choiceness = choiceness;
    }

    public TopBean getTop() {
        return top;
    }

    public void setTop(TopBean top) {
        this.top = top;
    }

    public NearStroeBean getNearStroe() {
        return nearStroe;
    }

    public void setNearStroe(NearStroeBean nearStroe) {
        this.nearStroe = nearStroe;
    }

    public FlashGoodBean flashGoods;
    public ChoicenesBean choiceness;
    public TopBean top;
    public NearStroeBean nearStroe;

    public class FlashGoodBean {
        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        private String productName;
        private String originalPrice;
        private String salePrice;
        private String pic;

        public long getEndTimeSecond() {
            return endTimeSecond;
        }

        public void setEndTimeSecond(long endTimeSecond) {
            this.endTimeSecond = endTimeSecond;
        }

        private long endTimeSecond;
    }

    public class ChoicenesBean {
        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        private String productName;
        private String pic;
        private String price;
    }

    public class TopBean {
        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        private String productName;
        private String pic;
        private String price;
    }

    public class NearStroeBean {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogoUrl() {
            return logoUrl;
        }

        public void setLogoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        private String name;
        private String logoUrl;
        private String distance;
    }
}
