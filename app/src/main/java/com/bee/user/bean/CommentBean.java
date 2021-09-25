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
        public int id;
        public int orderId;
        public int storeId;
        public int memberId;
        public String username;
        public int star;
        public String memberIp;
        public String content;
        public int isAnonymous;
        public String pics;
        public int delFlag;
        public int createBy;
        public String createName;
        public Date createTime;
        public Object updateBy;
        public Object updateName;
        public Object updateTime;
        public String icon;
    }

    public static class Detail {
        public int id;
        public int shopOrderCommentId;
        public Object star;
        public int commentObjId;
        public String commentObj;
        public int delFlag;
        public int commentType;
        public int give;
        public int createBy;
        public String createName;
        public String createTime;
        public Object updateBy;
        public Object updateName;
        public Object updateTime;
    }
}
