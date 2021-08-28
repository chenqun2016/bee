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
    public List<?> details;

    public static class EvaBean {
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
    }
}
