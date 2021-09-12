package com.bee.user.bean;

import java.util.List;

/**
 * 创建时间：2021/8/28
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class CommentWrapBean {
    public List<CommentBean> records;
    public Integer total;
    public Integer size;
    public Integer current;
    public List<?> orders;
    public Boolean hitCount;
    public Boolean searchCount;
    public Integer pages;
}
