package com.bee.user.bean;

import java.util.Date;
import java.util.List;

/**
 * 创建时间：2021/9/12
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class MyCommentWrapBean {
    public List<CommentItemBean> records;
    public Integer total;
    public Integer size;
    public Integer current;
    public List<?> orders;
    public Boolean hitCount;
    public Boolean searchCount;
    public Integer pages;

    public class CommentItemBean {
        public Integer id;
        public String storeName;
        public String storeLogo;
        public Date createTime;
    }
}