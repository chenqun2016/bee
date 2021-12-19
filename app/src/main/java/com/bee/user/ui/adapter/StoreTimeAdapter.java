package com.bee.user.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.StoreDetailFullBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/29  18：51
 * 描述：
 */
public class StoreTimeAdapter extends BaseAdapter {

//    private  String[] strs = new String[]{"星期一 07:00-20:00","星期二 07:00-20:00","星期三 07:00-20:00",
//    "星期四 07:00-20:00","星期五 07:00-20:00","星期六 07:00-20:00","星期日 07:00-20:00"};

    List<StoreDetailFullBean.BusinessTimesBean> mDatas ;
    public StoreTimeAdapter(List<StoreDetailFullBean.BusinessTimesBean> businessTimes) {
        mDatas = businessTimes;
        if(null == mDatas){
            mDatas = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StoreTimeAdapter.StoreTimeHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store_time, null);
            viewHolder = new StoreTimeAdapter.StoreTimeHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StoreTimeAdapter.StoreTimeHolder) convertView.getTag();
        }
        StoreDetailFullBean.BusinessTimesBean businessTimesBean = mDatas.get(position);
        viewHolder.text.setText(businessTimesBean.weekdayName+" " + businessTimesBean.startTime + "-" +businessTimesBean.endTime);
        int i = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if(businessTimesBean.weekday == i){
            viewHolder.text.setBackgroundResource(R.drawable.bg_yellow_round_8);
            viewHolder.text.setTextColor(viewHolder.text.getContext().getResources().getColor(R.color.color_FF6600));
        }else{
            viewHolder.text.setBackgroundResource(R.drawable.bg_round5dp_grey);
            viewHolder.text.setTextColor(viewHolder.text.getContext().getResources().getColor(R.color.color_7C7877));
        }


        return convertView;
    }


    static class StoreTimeHolder {

        @BindView(R.id.text)
        TextView text;


        public StoreTimeHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
