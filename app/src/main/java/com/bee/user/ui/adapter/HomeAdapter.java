package com.bee.user.ui.adapter;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddChartBean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.AddChartEvent;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.UIUtils;
import com.bee.user.widget.AddRemoveView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/23  20：48
 * 描述：
 */
public class HomeAdapter extends BaseQuickAdapter<StoreFoodItem2Bean,BaseViewHolder> implements LoadMoreModule {
    public HomeAdapter() {
        super(R.layout.item_home);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, StoreFoodItem2Bean bean) {
        ImageView iv_image = baseViewHolder.findView(R.id.iv_image);
        Picasso.with(iv_image.getContext())
                .load(bean.pic)
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_image.getContext(),10),0, PicassoRoundTransform.CornerType.TOP))
                .into(iv_image);

        TextView tv_title = baseViewHolder.findView(R.id.tv_title);
        tv_title.setText(bean.subTitle);
        TextView tv_content = baseViewHolder.findView(R.id.tv_content);
        tv_content.setText(bean.description);

        TextView tv_money = baseViewHolder.findView(R.id.tv_money);
        tv_money.setText(bean.price+"");

        TextView tv_tag = baseViewHolder.findView(R.id.tv_tag);
        if(!TextUtils.isEmpty(bean.tags)){
            tv_tag.setText(bean.tags);
            tv_tag.setVisibility(View.VISIBLE);
        }else{
            tv_tag.setVisibility(View.GONE);
        }
        TextView tv_chart_num = baseViewHolder.findView(R.id.tv_chart_num);
        if(bean.cartQuantity >0){
            tv_chart_num.setVisibility(View.VISIBLE);
            tv_chart_num.setText("×"+bean.cartQuantity);
        }else{
            tv_chart_num.setVisibility(View.GONE);
        }
        ImageView iv_chart = baseViewHolder.findView(R.id.iv_chart);
        iv_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Map<String, String> map = new HashMap<>();
//                map.put("num", "1");
//                map.put("skuId", bean.skuId + "");
//                map.put("storeId", bean.storeId + "");
//                map.put("attributes", "标签");
//                Api.getClient(HttpRequest.baseUrl_member).addToCart(Api.getRequestBody(map)).
//                        subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new BaseSubscriber<ChartBean>() {
//                            @Override
//                            public void onSuccess(ChartBean userBean) {
//                                EventBus.getDefault().post(new ChartFragmentEvent(ChartFragmentEvent.TYPE_REFLUSH));
//                            }
//
//                            @Override
//                            public void onFail(String fail) {
//                                super.onFail(fail);
//                            }
//                        });

                AddChartBean ChartBean = new AddChartBean(UIUtils.isTagStyle(bean), 1, bean.skuId, bean.storeId, BigDecimal.valueOf(bean.price), bean.cartItemId, null, bean);
                AddChartEvent addChartEvent = new AddChartEvent(ChartBean, 1);
                UIUtils.showChooseTypeDialog(iv_image.getContext(),addChartEvent, new StoreActivity.OnAddChartListener() {
                    @Override
                    public void onSuccess() {
                        if(null != mParent && null != mEnd){
                            AddRemoveView.doChartAnimal(iv_chart.getContext(),iv_chart,mParent,mEnd);
                        }
                        bean.cartQuantity++;
                        tv_chart_num.setText(bean.cartQuantity + "");
                        tv_chart_num.setVisibility(View.VISIBLE);

                        if(null != listener){
                            listener.onAddChart();
                        }
                    }

                    @Override
                    public void onFail() {

                    }
                });

            }
        });

    }

    ViewGroup mParent;
    View mEnd;
    public void setAddChartAnimatorView(ViewGroup parent,View end) {
        mParent = parent;
        mEnd = end;
    }



    private OnAddChartListener listener ;

    public void setListener(OnAddChartListener listener) {
        this.listener = listener;
    }

    public interface OnAddChartListener {
        void onAddChart();
    }
}
