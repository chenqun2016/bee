package com.bee.user.bean;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bee.user.ui.CommonWebActivity;

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
    public String updateTime;

    public void onClick(Context context){
        if(!TextUtils.isEmpty(target)){
            Intent intent = new Intent(context, CommonWebActivity.class);
            intent.putExtra("url", target);
            context.startActivity(intent);
        }
    }
}
