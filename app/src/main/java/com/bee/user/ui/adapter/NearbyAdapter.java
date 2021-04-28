package com.bee.user.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建人：进京赶考
 * 创建时间：2020/10/19  21：16
 * 描述：
 */
public class NearbyAdapter extends BaseQuickAdapter<StoreListBean.RecordsBean , BaseViewHolder> implements LoadMoreModule {
    public NearbyAdapter() {
        super(R.layout.item_nearby);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder helper, StoreListBean.RecordsBean  bean) {
        TextView tv_title = helper.getView(R.id.tv_title);
        tv_title.setText(bean.name);

        TextView tv_point = helper.getView(R.id.tv_point);
        tv_point.setText("");
        TextView tv_distance = helper.getView(R.id.tv_distance);
        tv_distance.setText(bean.distance+"");
        TextView tv_time = helper.getView(R.id.tv_time);
        tv_time.setText(bean.duration);
        TextView tv_sells = helper.getView(R.id.tv_sells);
        tv_sells.setText(bean.monthSalesCount);

        ImageView iv_icon = helper.getView(R.id.iv_icon);

        Context mContext = iv_icon.getContext();

        Picasso.with(mContext)
                .load(R.drawable.food2)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(mContext,5),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_icon);

        RecyclerView recyclerview = helper.getView(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
//        recyclerview.addItemDecoration(dividerItemDecoration);


        NearbyStoreFoodAdapter homeFooterAdapter = new NearbyStoreFoodAdapter(bean.products);
        homeFooterAdapter.setOnItemClickListener(new NearbyStoreFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                recyclerview.getContext().startActivity(new Intent(recyclerview.getContext(), StoreActivity.class));
            }
        });
        recyclerview.setAdapter(homeFooterAdapter);




//        标签
        LinearLayout ll_mark = helper.getView(R.id.ll_mark);
        ll_mark.setVisibility(View.GONE);
        ll_mark.removeAllViews();
        ll_mark.setGravity(Gravity.START);

        try {
//            String strList = bean.getStampContents();
//            if (!TextUtils.isEmpty(strList)) {
//                List<StoreBean.StoreTag> list = new Gson().fromJson(strList, new TypeToken<List<StoreBean.StoreTag>>() {
//                }.getType());

            List<StoreBean.StoreTag> list = new ArrayList<>();
            list.add(new StoreBean.StoreTag("自营品牌",0));
            list.add(new StoreBean.StoreTag("休息中",1));
            list.add(new StoreBean.StoreTag("预定明日",2));

            if ( list.size() > 0) {
                ll_mark.setVisibility(View.VISIBLE);
                TextView textView;
                for (int a = 0; a < (list.size() > 3 ? 3 : list.size()); a++) {
                    StoreBean.StoreTag o = list.get(a);
                    textView = new TextView(mContext);
                    textView.setText(o.tag);
                    textView.setTextSize(10);

                    textView.setBackgroundResource(R.drawable.bg_stroke_tag);
                    GradientDrawable drawable = (GradientDrawable) textView.getBackground();

                    switch (o.type){
                        case 0:
                            textView.setTextColor(mContext.getResources().getColor(R.color.color_FF6200));
                            drawable.setStroke(1,mContext.getResources().getColor(R.color.color_FF6200));
                            break;
                        case 1:
                            textView.setTextColor(mContext.getResources().getColor(R.color.color_ccc));
                            drawable.setStroke(1,mContext.getResources().getColor(R.color.color_ccc));
                            break;
                        case 2:
                            textView.setTextColor(mContext.getResources().getColor(R.color.color_2CD18A));
                            drawable.setStroke(1,mContext.getResources().getColor(R.color.color_2CD18A));
                            break;
                    }

                    textView.setPadding(DisplayUtil.dip2px(mContext, 5), 0, DisplayUtil.dip2px(mContext, 5), 0);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    if (a == 0) {
                        layoutParams.leftMargin = 0;
                    } else {
                        layoutParams.leftMargin = DisplayUtil.dip2px(mContext, 10);
                    }

                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    textView.setLayoutParams(layoutParams);
                    textView.setGravity(Gravity.CENTER);
                    textView.setSingleLine();
                    textView.setEllipsize(TextUtils.TruncateAt.END);
                    ll_mark.addView(textView);

                }
            }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
