package com.bee.user.params;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/28 下午3:46
 */
public class AddFavoritesParams {
    public String getFavoritesType() {
        return favoritesType;
    }

    public void setFavoritesType(String favoritesType) {
        this.favoritesType = favoritesType;
    }

    public String favoritesType;//类型  SHOP店铺/GOOD商品

    public int getBizId() {
        return bizId;
    }

    public void setBizId(int bizId) {
        this.bizId = bizId;
    }

    private int bizId;//店铺id
}
