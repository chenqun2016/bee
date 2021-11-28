package com.bee.user.params;

/**
 - @Description:
 - @Author: bxy
 - @Time:  2021/11/28 下午3:35
 */
public class CollectionStoreParams {
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int pageNum;
    public int pageSize;
    public String getFavoritesType() {
        return favoritesType;
    }

    public void setFavoritesType(String favoritesType) {
        this.favoritesType = favoritesType;
    }


    public String favoritesType;
}
