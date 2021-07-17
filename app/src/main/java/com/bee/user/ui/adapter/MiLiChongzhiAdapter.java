package com.bee.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.MiLiChongzhiBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  21：31
 * 描述：
 */
public class MiLiChongzhiAdapter extends BaseAdapter {
    private Context context;
    private List<MiLiChongzhiBean> list  = new ArrayList<>();
    public int mIndex = -1;

    public MiLiChongzhiAdapter(Context mContext,List<MiLiChongzhiBean> data) {
        this.context = mContext;
        this.list  = data;
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
        MiLiHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mili_chongzhi, null);
            viewHolder = new MiLiHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MiLiHolder) convertView.getTag();
        }

        MiLiChongzhiBean bean = list.get(position);
        viewHolder.tv_1.setText(bean.faceValue+"米粒");
        if(bean.freeValue == 0){
            viewHolder.tv_2.setVisibility(View.GONE);
        }else{
            viewHolder.tv_2.setText("送"+bean.freeValue + "米粒");
        }


        if(position == mIndex){
            viewHolder.tv_1.setTextColor(viewHolder.tv_1.getResources().getColor(R.color.color_FF6200));
            viewHolder.tv_2.setTextColor(viewHolder.tv_2.getResources().getColor(R.color.color_FF6200));
            viewHolder.ll_content.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);
        }else{
            viewHolder.tv_1.setTextColor(viewHolder.tv_1.getResources().getColor(R.color.color_282626));
            viewHolder.tv_2.setTextColor(viewHolder.tv_2.getResources().getColor(R.color.color_7C7877));
            viewHolder.ll_content.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);
        }


        return convertView;
    }

    public void setSelected(int i) {
        this.mIndex = i;
    }


    static class MiLiHolder {

        @BindView(R.id.tv_2)
        TextView tv_2;
        @BindView(R.id.tv_1)
        TextView tv_1;
        @BindView(R.id.ll_content)
        LinearLayout ll_content;

        public MiLiHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
