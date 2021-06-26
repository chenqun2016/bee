package com.bee.user.bean;

/**
 - @Description:
 - @Author:  bixueyun
 - @Time:  2021/6/24 下午4:30
 */
public class UploadImageBean {

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private Integer id;
    private String original;
    private String name;
    private String url;
}
