package com.bee.user.bean;

import java.util.Date;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/28  20：58
 * 描述：
 */
public class CommentBean {
    public EvaBean eva;
    public List<Detail> details;

    public static class EvaBean {
        public String replyContent;
        public String storeName;
        public Integer id;
        public Integer orderId;
        public Integer storeId;
        public Integer memberId;
        public String username;
        public Integer star;
        public String memberIp;
        public String content;
        public Integer isAnonymous;
        public String pics;
        public Integer delFlag;
        public Integer createBy;
        public String createName;
        public Date createTime;
        public Object updateBy;
        public Object updateName;
        public Object updateTime;
        public String icon;
    }

    public static class Detail {
        public Integer id;
        public Integer shopOrderCommentId;
        public Object star;
        public Integer commentObjId;
        public String commentObj;
        public Integer delFlag;
        public Integer commentType;
        public Integer give;
        public Integer createBy;
        public String createName;
        public String createTime;
        public Object updateBy;
        public Object updateName;
        public Object updateTime;
    }
}
