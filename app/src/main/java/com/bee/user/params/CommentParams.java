package com.bee.user.params;

import java.util.List;

/**
 * 创建时间：2021/8/14
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class CommentParams {
    public String content;
    public List<DetailsBean> details;
    public Integer id;
    public Integer isAnonymous;
    public Integer orderId;
    public String pics;
    public Integer star;
    public Integer storeId;

    public static class DetailsBean {
        public String commentObj;
        public Integer commentObjId;
        public Integer commentType;
        public Integer createBy;
        public String createName;
        public String createTime;
        public Integer delFlag;
        public Integer give;
        public Integer id;
        public Integer shopOrderCommentId;
        public Integer star;
        public Integer updateBy;
        public String updateName;
        public String updateTime;
    }
}
