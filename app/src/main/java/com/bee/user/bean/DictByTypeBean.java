package com.bee.user.bean;

/**
 - @Description:
 - @Author:  bixueyun
 - @Time:  2021/6/21 下午5:31
 */
public class DictByTypeBean {
    //意见反馈
    public static final String TYPE_FEEDBACK_TYPE = "feedback_type";
    //订单备注的快捷输入
    public static final String TYPE_ORDER_REMARK = "order_remark";
    //订单评价
    public static final String TYPE_ORDER_APPRAISE = "order_appraise";
    //退货原因
    public static final String TYPE_REFUND_REASON = "refund_reason";

    private String dictKey;

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String dictValue;
    private Integer id;
    private String remark;
    private Long sort;
    private Integer status;
    private String type;
}
