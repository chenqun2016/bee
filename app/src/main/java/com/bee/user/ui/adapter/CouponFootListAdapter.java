package com.bee.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.CardBean;

import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/02  15：57
 * 描述：
 */
public class CouponFootListAdapter extends BaseAdapter {
    private List<CardBean> mList;
    private Context context;

    public CouponFootListAdapter(Context context, List<CardBean> products) {
        this.mList = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShouyiPlanViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_coupon_foot, null);
            viewHolder = new ShouyiPlanViewHolder();
            viewHolder.textView1 = convertView.findViewById(R.id.tv_1);
            viewHolder.textView2 = convertView.findViewById(R.id.tv_2);
            viewHolder.textView3 = convertView.findViewById(R.id.tv_3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ShouyiPlanViewHolder) convertView.getTag();
        }
        CardBean cardBean = mList.get(position);
        switch (cardBean.type){
            case 0:
                viewHolder.textView3.setText("月");
                viewHolder.textView3.setTextColor(convertView.getResources().getColor(R.color.color_1C95D7));
                break;
            case 1:
                viewHolder.textView3.setText("季");
                viewHolder.textView3.setTextColor(convertView.getResources().getColor(R.color.color_694FF0));
                break;
            case 2:
                viewHolder.textView3.setText("年");
                viewHolder.textView3.setTextColor(convertView.getResources().getColor(R.color.color_F26F30));
                break;
        }
        viewHolder.textView1.setText(cardBean.str1);
        viewHolder.textView2.setText(cardBean.str2);
        return convertView;
    }

    static class ShouyiPlanViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
    }

}
