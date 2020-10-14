package com.bee.user.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  15：37
 * 描述：
 */
public class MineGridviewAdapter extends BaseAdapter {
    private Context context;

    public String[] strs = new String[]{"我的设置","我的地址","发票助手","我的客服",
            "浏览记录", "企业订餐","商务合作","我的评价",
            "收藏的店","帮助中心","推荐有奖","意见反馈"};

    public int[] ints = new int[]{R.drawable.icon_wodeshezhi,R.drawable.icon_wodedizhi,R.drawable.icon_fapiaozhushou,R.drawable.icon_wodedizhi,
            R.drawable.icon_liulanjilu,R.drawable.icon_qiyedingcan,R.drawable.icon_shangwuhezuo,R.drawable.icon_wodepingjia,
            R.drawable.icon_shoucangdedian,R.drawable.icon_wodedizhi,R.drawable.icon_tuijianyoujiang,R.drawable.icon_yijianfankui};

    public MineGridviewAdapter(Context mContext) {
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

        MineGridviewAdapter.HomeGridviewHolder viewHolder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_mine_gridview, null);
            viewHolder = new MineGridviewAdapter.HomeGridviewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MineGridviewAdapter.HomeGridviewHolder) convertView.getTag();
        }


        int i = ints[position];
        String s = strs[position];

        viewHolder.tv_content.setText(s);

        Drawable icon = viewHolder.tv_content.getResources().getDrawable(i);
        int width =  icon.getIntrinsicWidth() ;
        int height =  icon.getIntrinsicHeight() ;
        icon.setBounds(0, 0, width, height);
        viewHolder.tv_content.setCompoundDrawables(null,icon,null,null);

        return convertView;
    }

    static class HomeGridviewHolder {
        @BindView(R.id.tv_content)
        TextView tv_content;


        public HomeGridviewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
