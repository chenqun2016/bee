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

import com.amap.api.location.AMapLocation;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.NearbyStoreFoodAdapter;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseCEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/25  22：46
 * 描述：
 */
public class NearbyEntity extends BaseCEntity<StoreListBean.RecordsBean> {
    @Override
    public Observable getPageAt(int page, int row) {
        AMapLocation location = SPUtils.geTinstance().getLocation();
        Map<String, String> map = new HashMap<>();
        if(null != location){
            map.put("longitude", location.getLongitude()+"");
            map.put("latitude", location.getLatitude()+"");
            map.put("address", location.getAddress());
        }
        map.put("maxDistance", 1000+"");
        map.put("productCategoryId", 1+"");
        map.put("productCategoryName", 1+"");
        return Api.getClient(HttpRequest.baseUrl_shop).shop_nearby(page,row,Api.getRequestBody(map));
    }

    @Override
    public void onClick(Context context, StoreListBean.RecordsBean item,int position) {
        super.onClick(context, item,position);

        context.startActivity(new Intent(context, StoreActivity.class));
    }


    @Override
    public int getItemLayou() {
        return R.layout.item_nearby;
    }

    @Override
    public void convert(BaseQuickAdapter adapter, BaseViewHolder helper, StoreListBean.RecordsBean bean, int position) {
        super.convert(adapter, helper, bean , position);
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
        CommonUtil.initTAGViews(ll_mark);


    }
}
