package com.bee.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.HomeGridview2Bean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/24  15：53
 * 描述：
 */
public class HomeGridview2Adapter extends BaseAdapter {
    private Context context;
    private List<HomeGridview2Bean> list  = new ArrayList<>();

    public HomeGridview2Adapter(Context mContext,List<HomeGridview2Bean> list) {
        this.context = mContext;
        this.list.clear();
        this.list .addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeGridview2Holder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_gridview2, null);
            viewHolder = new HomeGridview2Holder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HomeGridview2Holder) convertView.getTag();
        }
        HomeGridview2Bean bean = list.get(position);
//        viewHolder.tv_image.setImageResource();
        viewHolder.tv_title.setText(bean.title);
        viewHolder.tv_time.setText(bean.time);
        viewHolder.tv_name.setText(bean.name);
        viewHolder.tv_money.setText(bean.money);
        viewHolder.tv_moneypast.setText(bean.moneypast);


        return convertView;
    }


    static class HomeGridview2Holder {
        @BindView(R.id.tv_image)
        ImageView tv_image;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_money)
        TextView tv_money;
        @BindView(R.id.tv_moneypast)
        TextView tv_moneypast;


        public HomeGridview2Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
