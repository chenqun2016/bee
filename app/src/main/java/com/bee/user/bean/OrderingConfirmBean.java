package com.bee.user.bean;

import java.util.List;

/**
 * 创建时间：2021/5/15
 * 编写人： 陈陈陈
 * 功能描述：
 */
public class OrderingConfirmBean {

    /**
     * calcAmount : null
     * address : {"id":249,"memberId":37,"name":"15250464603","phoneNumber":"15250464603","defaultStatus":1,"postCode":"021","province":"上海市","city":"上海市","district":"浦东新区","detailAddress":"上海市浦东新区滨江大道1588号靠近凡墨健康管理咨询(上海)有限公司然健康中心","houseNumber":"路上的风景啦","gender":1,"tag":1,"latitude":"31.243841","longitude":"121.524651"}
     * memberReceiveAddressList : []
     * storeOrderConfirmItems : [{"storeId":17,"storeName":null,"calcAmount":{"packingFeeAmount":0,"totalAmount":38,"freightAmount":0,"payAmount":38},"products":[{"productSkuId":1135,"productId":26,"productName":"石锅拌饭","price":19,"quantity":null,"productPic":"http://quxianfeng.oss-cn-shanghai.aliyuncs.com/13d25a3e-499e-445d-ba87-0e14d8ff797b?Expires=33147301674&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=fLzy5PIEEkNPoeJ9k9OUQs1El3A%3D","productAttr":null}]},{"storeId":8,"storeName":null,"calcAmount":{"packingFeeAmount":0,"totalAmount":65,"freightAmount":0,"payAmount":65},"products":[{"productSkuId":1169,"productId":38,"productName":"兰州拉面","price":13,"quantity":null,"productPic":"http://quxianfeng.oss-cn-shanghai.aliyuncs.com/de91c47d-f20d-4d82-9cd5-ac998302e003?Expires=33147301492&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=hRSonJgn%2FGPVThopQYI2xnhMLdg%3D","productAttr":null}]}]
     */

    private String calcAmount;
    private AddressBean address;
    private List<?> memberReceiveAddressList;
    private List<StoreOrderConfirmItemsBean> storeOrderConfirmItems;

    public String getCalcAmount() {
        return calcAmount;
    }

    public void setCalcAmount(String calcAmount) {
        this.calcAmount = calcAmount;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public List<?> getMemberReceiveAddressList() {
        return memberReceiveAddressList;
    }

    public void setMemberReceiveAddressList(List<?> memberReceiveAddressList) {
        this.memberReceiveAddressList = memberReceiveAddressList;
    }

    public List<StoreOrderConfirmItemsBean> getStoreOrderConfirmItems() {
        return storeOrderConfirmItems;
    }

    public void setStoreOrderConfirmItems(List<StoreOrderConfirmItemsBean> storeOrderConfirmItems) {
        this.storeOrderConfirmItems = storeOrderConfirmItems;
    }

    public static class AddressBean {
        /**
         * id : 249
         * memberId : 37
         * name : 15250464603
         * phoneNumber : 15250464603
         * defaultStatus : 1
         * postCode : 021
         * province : 上海市
         * city : 上海市
         * district : 浦东新区
         * detailAddress : 上海市浦东新区滨江大道1588号靠近凡墨健康管理咨询(上海)有限公司然健康中心
         * houseNumber : 路上的风景啦
         * gender : 1
         * tag : 1
         * latitude : 31.243841
         * longitude : 121.524651
         */

        private int id;
        private int memberId;
        private String name;
        private String phoneNumber;
        private int defaultStatus;
        private String postCode;
        private String province;
        private String city;
        private String district;
        private String detailAddress;
        private String houseNumber;
        private int gender;
        private int tag;
        private String latitude;
        private String longitude;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMemberId() {
            return memberId;
        }

        public void setMemberId(int memberId) {
            this.memberId = memberId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getDefaultStatus() {
            return defaultStatus;
        }

        public void setDefaultStatus(int defaultStatus) {
            this.defaultStatus = defaultStatus;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }

    public static class StoreOrderConfirmItemsBean {
        /**
         * storeId : 17
         * storeName : null
         * calcAmount : {"packingFeeAmount":0,"totalAmount":38,"freightAmount":0,"payAmount":38}
         * products : [{"productSkuId":1135,"productId":26,"productName":"石锅拌饭","price":19,"quantity":null,"productPic":"http://quxianfeng.oss-cn-shanghai.aliyuncs.com/13d25a3e-499e-445d-ba87-0e14d8ff797b?Expires=33147301674&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=fLzy5PIEEkNPoeJ9k9OUQs1El3A%3D","productAttr":null}]
         */

        //0:今天。1：明天
        public int currentDay;
        public int feightTemplateDetailId;
        public String feightTemplateDetail;
        private int storeId;
        private Object storeName;
        private CalcAmountBean calcAmount;
        private List<ProductsBean> products;

        public int getStoreId() {
            return storeId;
        }

        public void setStoreId(int storeId) {
            this.storeId = storeId;
        }

        public Object getStoreName() {
            return storeName;
        }

        public void setStoreName(Object storeName) {
            this.storeName = storeName;
        }

        public CalcAmountBean getCalcAmount() {
            return calcAmount;
        }

        public void setCalcAmount(CalcAmountBean calcAmount) {
            this.calcAmount = calcAmount;
        }

        public List<ProductsBean> getProducts() {
            return products;
        }

        public void setProducts(List<ProductsBean> products) {
            this.products = products;
        }

        public static class CalcAmountBean {
            /**
             * packingFeeAmount : 0
             * totalAmount : 38
             * freightAmount : 0
             * payAmount : 38
             */

            private int packingFeeAmount;
            private int totalAmount;
            private int freightAmount;
            private int payAmount;

            public int getPackingFeeAmount() {
                return packingFeeAmount;
            }

            public void setPackingFeeAmount(int packingFeeAmount) {
                this.packingFeeAmount = packingFeeAmount;
            }

            public int getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(int totalAmount) {
                this.totalAmount = totalAmount;
            }

            public int getFreightAmount() {
                return freightAmount;
            }

            public void setFreightAmount(int freightAmount) {
                this.freightAmount = freightAmount;
            }

            public int getPayAmount() {
                return payAmount;
            }

            public void setPayAmount(int payAmount) {
                this.payAmount = payAmount;
            }
        }

        public static class ProductsBean {
            /**
             * productSkuId : 1135
             * productId : 26
             * productName : 石锅拌饭
             * price : 19
             * quantity : null
             * productPic : http://quxianfeng.oss-cn-shanghai.aliyuncs.com/13d25a3e-499e-445d-ba87-0e14d8ff797b?Expires=33147301674&OSSAccessKeyId=LTAI4G2Km1Dj8K2wke5urowk&Signature=fLzy5PIEEkNPoeJ9k9OUQs1El3A%3D
             * productAttr : null
             */

            private int productSkuId;
            private int productId;
            private String productName;
            private int price;
            private Object quantity;
            private String productPic;
            private Object productAttr;

            public int getProductSkuId() {
                return productSkuId;
            }

            public void setProductSkuId(int productSkuId) {
                this.productSkuId = productSkuId;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public Object getQuantity() {
                return quantity;
            }

            public void setQuantity(Object quantity) {
                this.quantity = quantity;
            }

            public String getProductPic() {
                return productPic;
            }

            public void setProductPic(String productPic) {
                this.productPic = productPic;
            }

            public Object getProductAttr() {
                return productAttr;
            }

            public void setProductAttr(Object productAttr) {
                this.productAttr = productAttr;
            }
        }
    }
}
