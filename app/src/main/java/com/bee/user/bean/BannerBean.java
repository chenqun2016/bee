package com.bee.user.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bee.user.ui.CommonWebActivity;
import com.bee.user.utils.CommonUtil;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/23  20：35
 * 描述：
 */
public class BannerBean {
    public String createTime;
    public Integer delFlag;
    public String endTime;
    public Integer id;
    public String imageUrl;
    public String memo;
    public String name;
    public String place;
    public String platform;
    public String startTime;
    public Integer status;
    public String target;
    //"落地页类型：0-无落地页；1-应用内跳转；2-外部链接"
    public int targetType;
    public String updateTime;

    public void onClick(Context context){
        if(!TextUtils.isEmpty(target)){
            if(0 == targetType){

            }
            if(1 == targetType){

            }
            if(2 == targetType){
                Intent intent = new Intent(context, CommonWebActivity.class);
                intent.putExtra("url", target);
                context.startActivity(intent);
            }
            if(3 == targetType){
                if(!CommonUtil.jumpToApp(context,target)){
                    Intent intent = new Intent(context, CommonWebActivity.class);
                    intent.putExtra("url", target);
                    context.startActivity(intent);
                }
            }

        }
    }
}
