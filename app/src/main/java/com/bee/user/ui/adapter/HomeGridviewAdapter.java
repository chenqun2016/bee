package com.bee.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.HomeCatogoryBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/24  15：27
 * 描述：
 */
public class HomeGridviewAdapter extends BaseAdapter {
    private Context context;

    public List<HomeCatogoryBean> getDatas() {
        return mDatas;
    }

    private List<HomeCatogoryBean> mDatas = new ArrayList<>();

//    public String[] strs = new String[]{"美食广场","营养早餐","品质午餐","品质晚餐","下午茶",
//    "咖啡奶茶","现切水果","面包点心","简餐便当","新人礼包"};

//    public int[] ints = new int[]{R.drawable.icon_meishi,R.drawable.icon_zaocan,R.drawable.icon_wucan,R.drawable.icon_yexiao,R.drawable.icon_xiawucha
//            ,R.drawable.icon_kafei,R.drawable.icon_shuiguo,R.drawable.icon_dianxin,R.drawable.icon_biandang,R.drawable.icon_libao};

    public HomeGridviewAdapter(Context mContext) {
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HomeGridviewHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_gridview, null);
            viewHolder = new HomeGridviewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (HomeGridviewHolder) convertView.getTag();
        }

        HomeCatogoryBean homeCatogoryBean = mDatas.get(position);
        viewHolder.tv_text.setText(homeCatogoryBean.name);
        Picasso.with(context)
                .load(homeCatogoryBean.icon)
                .fit()
                .into(viewHolder.iv_image);

        return convertView;
    }

    public void setNewInstance(List<HomeCatogoryBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    static class HomeGridviewHolder {
        @BindView(R.id.iv_image)
        ImageView iv_image;
        @BindView(R.id.tv_text)
        TextView tv_text;


        public HomeGridviewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
