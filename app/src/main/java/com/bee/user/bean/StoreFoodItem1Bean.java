package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2021/05/08  15：16
 * 描述：
 */
public class StoreFoodItem1Bean {


    private int id;
    private int storeId;
    private int parentId;
    private String name;
    private int navStatus;
    private int showStatus;
    private int indexStatus;
    private int sort;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNavStatus() {
        return navStatus;
    }

    public void setNavStatus(int navStatus) {
        this.navStatus = navStatus;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public int getIndexStatus() {
        return indexStatus;
    }

    public void setIndexStatus(int indexStatus) {
        this.indexStatus = indexStatus;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
