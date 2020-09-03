package com.bee.user.entity;

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
import com.bee.user.ui.home.NearbyStoreFoodAdapter;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/25  22：46
 * 描述：
 */
public class NearbyEntity extends BaseCEntity<StoreBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        return null;
    }

    @Override
    public void onClick(Context context, StoreBean item) {
        super.onClick(context, item);

        context.startActivity(new Intent(context, StoreActivity.class));
    }


    @Override
    public int getItemLayou() {
        return R.layout.item_nearby;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, StoreBean item, int position) {
        super.convert(adapter, helper, item, position);

        ImageView iv_icon = helper.getView(R.id.iv_icon);

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
        List<StoreBean.StoreFood> storeFoods = new ArrayList<>();
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","10"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","11"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","12"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","13"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","14"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","15"));
        storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","16"));

        NearbyStoreFoodAdapter homeFooterAdapter = new NearbyStoreFoodAdapter(storeFoods);
        homeFooterAdapter.setOnItemClickListener(new NearbyStoreFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String id) {
                ToastUtil.ToastShort(mContext,"haha"+id);
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