package com.bee.user.event;

import com.bee.user.bean.AddChartBean;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/14  21：46
 * 描述：
 */
public class AddChartEvent {
    public  AddChartBean addChartBean;
    //1是加，0是减
    public int type;
    public AddChartEvent(AddChartBean addChartBean,int type) {
        this.addChartBean = addChartBean;
        this.type = type;
    }
}
