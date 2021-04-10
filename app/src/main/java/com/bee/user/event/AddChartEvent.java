package com.bee.user.event;

import com.bee.user.bean.AddChartBean;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/14  21：46
 * 描述：
 */
public class AddChartEvent {
    public  AddChartBean addChartBean;

    public AddChartEvent(AddChartBean addChartBean) {
        this.addChartBean = addChartBean;
    }
}
