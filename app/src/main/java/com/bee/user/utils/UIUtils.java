package com.bee.user.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddChartBean;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.ElemeGroupedItem;
import com.bee.user.bean.FoodTypeBean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.AddChartEvent;
import com.bee.user.event.ChartFragmentEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.FoodChooseTypeAdapter;
import com.bee.user.ui.nearby.StoreActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2021/12/5
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class UIUtils {
    public static boolean isTagStyle(BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {
        return isTagStyle(item.info.getBean());
    }
    public static boolean isTagStyle(StoreFoodItem2Bean item) {
        return (item.attributeList != null && item.attributeList.size() > 0)
                || (item.skuList != null && item.skuList.size() > 1);
    }
    public static void showChooseTypeDialog(Context activity, AddChartEvent event, StoreActivity.OnAddChartListener listener) {
        AddChartBean bean = event.addChartBean;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(activity);
        bottomSheetDialog.setContentView(R.layout.dialog_store_bottom_choose);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        TextView iv_goods_name = bottomSheetDialog.findViewById(R.id.iv_goods_name);
        iv_goods_name.setText(bean.data2.subTitle);
        TextView iv_goods_detail = bottomSheetDialog.findViewById(R.id.iv_goods_detail);
//        iv_goods_detail.setText(bean.data2.description);

//        TextView iv_goods_comment = bottomSheetDialog.findViewById(R.id.iv_goods_comment);
//        iv_goods_comment.setText("剩余" + bean.data2.stock + "份  月售" + bean.data2.sale);
        TextView iv_goods_price = bottomSheetDialog.findViewById(R.id.iv_goods_price);
        iv_goods_price.setText("¥" + bean.data2.skuList.get(0).price);
        TextView iv_goods_price_past = bottomSheetDialog.findViewById(R.id.iv_goods_price_past);
        if ( bean.data2.skuList.get(0).orginPrice >0) {
            iv_goods_price_past.setVisibility(View.VISIBLE);
        } else {
            iv_goods_price_past.setVisibility(View.GONE);
        }
        iv_goods_price_past.setText("¥" + bean.data2.skuList.get(0).orginPrice);

        ImageView iv_goods_img = bottomSheetDialog.findViewById(R.id.iv_goods_img);
        Picasso.with(iv_goods_img.getContext())
                .load(bean.data2.pic)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 5), 0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_goods_img);
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(activity));
        FoodChooseTypeAdapter foodChooseTypeAdapter = new FoodChooseTypeAdapter();
        recyclerview.setAdapter(foodChooseTypeAdapter);

        List<FoodTypeBean> datas = new ArrayList<>();

        String title1 = "规格";
        //添加规格
        if (bean.data2.skuList.size() > 1) {
            List<String> dataSource = new ArrayList<>();
            for (StoreFoodItem2Bean.SkuListBean name : bean.data2.skuList) {
                dataSource.add(name.skuName);
            }
            FoodTypeBean foodTypeBean = new FoodTypeBean();
            foodTypeBean.title = title1;
            foodTypeBean.lists = dataSource;
            datas.add(foodTypeBean);
        }

        //添加标签
        for (StoreFoodItem2Bean.AttributeListBean bean1 : bean.data2.attributeList) {
            List<String> tags = new ArrayList<>();
            tags.addAll(Arrays.asList(bean1.inputList.split(",")));
            FoodTypeBean tagsBean = new FoodTypeBean();
            tagsBean.title = bean1.name;
            tagsBean.lists = tags;
            datas.add(tagsBean);
        }


        foodChooseTypeAdapter.setNewInstance(datas);
        foodChooseTypeAdapter.setOnItemCheckedListener(new FoodChooseTypeAdapter.OnItemCheckedListener() {
            @Override
            public void onItemChecked(int position ,int index, String content) {
                setSelectedParams(foodChooseTypeAdapter.getData(),iv_goods_detail);
                if(position == 0){
                    iv_goods_price.setText("¥" + bean.data2.skuList.get(index).price);
                    if (bean.data2.skuList.get(index).orginPrice>0) {
                        iv_goods_price_past.setVisibility(View.VISIBLE);
                    } else {
                        iv_goods_price_past.setVisibility(View.GONE);
                    }
                    iv_goods_price_past.setText("¥" + bean.data2.skuList.get(index).orginPrice);
                }
            }
        });
        setSelectedParams(foodChooseTypeAdapter.getData(),iv_goods_detail);

        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
//        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bottomSheetDialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                List<FoodTypeBean> data = foodChooseTypeAdapter.getData();
                //有规格的情况，设置skuid
                if (title1.equals(data.get(0).title)) {
                    event.addChartBean.skuId = event.addChartBean.data2.skuList.get(data.get(0).selected).skuId;

                }
                event.type = 1;
//                onAddChartEvent(event);
                StringBuilder tags = new StringBuilder();
                for (FoodTypeBean bean : data) {
                    tags.append(bean.lists.get(bean.selected));
                    tags.append(",");
                }
                event.addChartBean.tags = tags.toString();
                doAddChartEvent(event, listener);
            }
        });

        bottomSheetDialog.show();
    }

    private static void doAddChartEvent(AddChartEvent event, StoreActivity.OnAddChartListener listener) {
        AddChartBean addChartBean = event.addChartBean;
        Map<String, String> map = new HashMap<>();
        map.put("num", "1");
        map.put("skuId", addChartBean.skuId + "");
        map.put("storeId", addChartBean.storeId + "");
        if (!TextUtils.isEmpty(event.addChartBean.tags)) {
            map.put("attributes", event.addChartBean.tags);
        }
        Api.getClient(HttpRequest.baseUrl_member).addToCart(Api.getRequestBody(map)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ChartBean>() {
                    @Override
                    public void onSuccess(ChartBean userBean) {
                        if (null != listener) {
                            listener.onSuccess();
                        }
                        EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        if (null != listener) {
                            listener.onFail();
                        }
                    }
                });
    }
    private static void setSelectedParams(List<FoodTypeBean> data, TextView iv_goods_detail) {
        StringBuilder builder = new StringBuilder();
        for(FoodTypeBean foodTypeBean :data){
            int indexOf = data.indexOf(foodTypeBean);
            if(0 == indexOf){
                builder.append(foodTypeBean.lists.get(foodTypeBean.selected));
            }else{
                builder.append("/"+foodTypeBean.lists.get(foodTypeBean.selected));
            }
        }
        iv_goods_detail.setText("已选：+"+builder.toString());
    }
}
