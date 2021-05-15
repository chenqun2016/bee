package com.bee.user.bean;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/15  20：25
 * 描述：
 */
public class ChooseTimeBean {

    //今天被选中的位置
    public int pre = -1;
    //明天被选中的位置
    public int pre2 = -1;

    public  ChooseTimeBean.ChooseTimeItemBean mCurrentChooseTimeBean;

    private List<ChooseTimeItemBean> today;
    private List<ChooseTimeItemBean> tomorrow;

    public List<ChooseTimeItemBean> getToday() {
        return today;
    }

    public void setToday(List<ChooseTimeItemBean> today) {
        this.today = today;
    }

    public List<ChooseTimeItemBean> getTomorrow() {
        return tomorrow;
    }

    public void setTomorrow(List<ChooseTimeItemBean> tomorrow) {
        this.tomorrow = tomorrow;
    }

    public static class ChooseTimeItemBean {
        public boolean choosed = false;

        /**
         * arriveTime : 15:08
         * deliverFee : 5.00
         */

        private String arriveTime;
        private String deliverFee;

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getDeliverFee() {
            return deliverFee;
        }

        public void setDeliverFee(String deliverFee) {
            this.deliverFee = deliverFee;
        }
    }
}
