package com.huaxiafinance.www.crecyclerview.crecyclerView;


import org.greenrobot.eventbus.EventBus;

/**
 * Created by chenqun on 2016/7/13.
 */
public class EventBusUtils {
    //errorCode 异常code
    public static final String ErrorCode_login_out = "50010";//访问受限
    private static volatile EventBusUtils utils ;

    public static EventBusUtils getInstance(){
        EventBusUtils util = utils;
        if(null == util){
            synchronized (EventBusUtils.class){
                util = utils;
                if(null == util){
                    util = new EventBusUtils();
                    utils = util;
                }
            }
        }
        return util;
    }
    public static  long time = 0;
    private static final long intervalTime = 1000*5;//x秒的周期
    public void kickOff(int a){
        if(System.currentTimeMillis() - time > intervalTime){
            EventBus.getDefault().postSticky(new KICKOUTEvent(a));
            time = System.currentTimeMillis();
        }
    }
}
