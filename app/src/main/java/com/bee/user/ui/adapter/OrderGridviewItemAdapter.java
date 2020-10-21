package com.bee.user.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.MiLiChongzhiBean;
import com.bee.user.bean.OrderGridviewItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/21  16：07
 * 描述：
 */
public class OrderGridviewItemAdapter extends BaseAdapter {
    private Context context;

    public List<OrderGridviewItemBean> getList() {
        return list;
    }

    private List<OrderGridviewItemBean> list  = new ArrayList<>();

    public OrderGridviewItemAdapter(Context mContext,List<OrderGridviewItemBean> list) {
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
        ItemHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_gridview, null);
            viewHolder = new ItemHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemHolder) convertView.getTag();
        }
        OrderGridviewItemBean bean = list.get(position);


        Drawable icon = viewHolder.tv_text.getResources().getDrawable(bean.icon);
        int width =  icon.getIntrinsicWidth() ;
        int height =  icon.getIntrinsicHeight() ;
        icon.setBounds(0, 0, width, height);
        viewHolder.tv_text.setCompoundDrawables(null,icon,null,null);

        viewHolder.tv_text.setText(bean.text);
        viewHolder.tv_text.setTextColor(viewHolder.tv_text.getResources().getColor(bean.text_color));

        return convertView;
    }



    static class ItemHolder {

        @BindView(R.id.tv_text)
        TextView tv_text;

        public ItemHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
