package com.bee.user.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bee.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/29  18：51
 * 描述：
 */
public class StoreTimeAdapter extends BaseAdapter {

    private  String[] strs = new String[]{"星期一 07:00-20:00","星期二 07:00-20:00","星期三 07:00-20:00",
    "星期四 07:00-20:00","星期五 07:00-20:00","星期六 07:00-20:00","星期日 07:00-20:00"};

    public StoreTimeAdapter() {
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int position) {
        return strs[position];
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

        viewHolder.text.setText(strs[position]);
        if(1 == position){
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
