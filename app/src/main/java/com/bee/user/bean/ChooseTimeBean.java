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

    public List<ChooseTimeItemBean> today;
    public List<ChooseTimeItemBean> tomorrow;

    public int getCurrent(){
        if(pre != -1){
            return today.get(pre).feightTemplateDetailId;
        }else if(pre2 != -1){
            return today.get(pre2).feightTemplateDetailId;
        }
        return -1;
    }

    public static class ChooseTimeItemBean {
        public boolean choosed = false;

        /**
         * arriveTime : 15:08
         * deliverFee : 5.00
         */

        public String arriveTime;
        public String deliverFee;
        public int feightTemplateDetailId;

    }
}
