package com.bee.user.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;

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

    public String[] strs = new String[]{"美食广场","营养早餐","品质午餐","品质晚餐","下午茶",
    "咖啡奶茶","现切水果","面包点心","简餐便当","新人礼包"};

    public int[] ints = new int[]{R.drawable.icon_meishi,R.drawable.icon_zaocan,R.drawable.icon_wucan,R.drawable.icon_yexiao,R.drawable.icon_xiawucha
            ,R.drawable.icon_kafei,R.drawable.icon_shuiguo,R.drawable.icon_dianxin,R.drawable.icon_biandang,R.drawable.icon_libao};

    public HomeGridviewAdapter(Context mContext) {
        this.context = mContext;
    }

    @Override
    public int getCount() {
        return strs.length;
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


        int i = ints[position];
        String s = strs[position];

        viewHolder.iv_image.setImageResource(i);
        viewHolder.tv_text.setText(s);



        return convertView;
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
