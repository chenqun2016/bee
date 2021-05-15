package com.bee.user.ui.nearby;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bee.user.BeeApplication;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddChartBean;
import com.bee.user.bean.ElemeGroupedItem;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.AddChartEvent;
import com.bee.user.event.StoreEvent;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.AddRemoveView;
import com.kunminx.linkage.LinkageRecyclerView;
import com.kunminx.linkage.adapter.viewholder.LinkagePrimaryViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryFooterViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder;
import com.kunminx.linkage.adapter.viewholder.LinkageSecondaryViewHolder;
import com.kunminx.linkage.bean.BaseGroupedItem;
import com.kunminx.linkage.contract.ILinkagePrimaryAdapterConfig;
import com.kunminx.linkage.contract.ILinkageSecondaryAdapterConfig;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/27  20：35
 * 描述：
 */
public class StoreFragment extends BaseFragment {
    private Unbinder unbinder;


    private static final int SPAN_COUNT_FOR_GRID_MODE = 2;
    private static final int MARQUEE_REPEAT_LOOP_MODE = -1;
    private static final int MARQUEE_REPEAT_NONE_MODE = 0;

    @BindView(R.id.linkage)
    LinkageRecyclerView linkage;

    public static StoreFragment newInstance(int height, String id) {
        Bundle arguments = new Bundle();
        arguments.putInt("height", height);
        arguments.putString("id", id);
        StoreFragment fragment = new StoreFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private  List<StoreFoodItem2Bean> mDatas = new ArrayList();

    static String storeId = "";

    @Override
    protected void getDatas() {
        storeId = getArguments().getString("id");

    }

    private void setViews() {
        if (null == mDatas || mDatas.size()==0) {
            return;
        }
        List<ElemeGroupedItem> mEDatas = new ArrayList();
        for (int i = 0; i < mDatas.size(); i++) {
            StoreFoodItem2Bean bean = mDatas.get(i);


            if (i == 0 || !mDatas.get(i - 1).shopProductCategoryName.equals(bean.shopProductCategoryName)) {
                ElemeGroupedItem elemeGroupedItem = new ElemeGroupedItem(true, bean.shopProductCategoryName);
                mEDatas.add(elemeGroupedItem);
            }

            ElemeGroupedItem.ItemInfo itemInfo = new ElemeGroupedItem.ItemInfo(bean.skuName, bean.shopProductCategoryName, bean);
            if(!TextUtils.isEmpty(bean.cartQuantity)){
                itemInfo.num = Integer.parseInt(bean.cartQuantity);
            }


            mEDatas.add(new ElemeGroupedItem(itemInfo));
        }

        ElemeSecondaryAdapterConfig elemeSecondaryAdapterConfig = new ElemeSecondaryAdapterConfig();
        elemeSecondaryAdapterConfig.setContext(BeeApplication.getInstance());
        linkage.init(mEDatas, new StoreFragment.ElemePrimaryAdapterConfig(), elemeSecondaryAdapterConfig);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();

        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_store_1, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linkage.setPrimaryWidget(80);

////        List<ElemeGroupedItem> items = new ArrayList<>();
//        Gson gson = new Gson();
//        List<ElemeGroupedItem> items = gson.fromJson(getString(R.string.eleme_json),
//                new TypeToken<List<ElemeGroupedItem>>() {
//                }.getType());
//
//
//        int anInt = getArguments().getInt("height");
////        linkage.setLayoutHeight(DisplayUtil.px2dip(linkage.getContext(),anInt));
//        ElemeSecondaryAdapterConfig elemeSecondaryAdapterConfig = new ElemeSecondaryAdapterConfig();
//        elemeSecondaryAdapterConfig.setContext(BeeApplication.getInstance());
//        linkage.init(items,new StoreFragment.ElemePrimaryAdapterConfig(),elemeSecondaryAdapterConfig );
    }




    public void setFoodDatas(List<StoreFoodItem2Bean> datas) {
        mDatas = datas;
        setViews();
    }


    private static class ElemePrimaryAdapterConfig implements ILinkagePrimaryAdapterConfig {

        private Context mContext;

        public void setContext(Context context) {
            mContext = context;
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_store_list_first;
        }

        @Override
        public int getGroupTitleViewId() {
            return R.id.tv_group;
        }

        @Override
        public int getRootViewId() {
            return R.id.layout_group;
        }
//
//        @Override
//        public int getLayoutId() {
//            return com.kunminx.linkage.R.layout.default_adapter_linkage_primary;
//        }
//
//        @Override
//        public int getGroupTitleViewId() {
//            return com.kunminx.linkage.R.id.tv_group;
//        }
//
//        @Override
//        public int getRootViewId() {
//            return com.kunminx.linkage.R.id.layout_group;
//        }

        @Override
        public void onBindViewHolder(LinkagePrimaryViewHolder holder, boolean selected, String title) {
            TextView tvTitle = ((TextView) holder.mGroupTitle);
            tvTitle.setText(title + "");

            tvTitle.setBackgroundColor(mContext.getResources().getColor(
                    selected ? R.color.white : R.color.color_F5F5F5));
            tvTitle.setTextColor(ContextCompat.getColor(mContext,
                    selected ? R.color.color_282525 : R.color.color_7C7877));
            tvTitle.setEllipsize(selected ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
            tvTitle.setFocusable(selected);
            tvTitle.setTypeface(selected ? Typeface.DEFAULT_BOLD : Typeface.DEFAULT);
            tvTitle.setFocusableInTouchMode(selected);
            tvTitle.setMarqueeRepeatLimit(selected ? MARQUEE_REPEAT_LOOP_MODE : MARQUEE_REPEAT_NONE_MODE);
        }

        @Override
        public void onItemClick(LinkagePrimaryViewHolder holder, View view, String title) {
            //TODO
        }
    }

    private static class ElemeSecondaryAdapterConfig implements ILinkageSecondaryAdapterConfig<ElemeGroupedItem.ItemInfo> {

        private Context mContext;

        public void setContext(Context context) {
            mContext = context;
        }

        @Override
        public int getGridLayoutId() {
            return 0;
        }

        @Override
        public int getLinearLayoutId() {
            return R.layout.item_store_food;
        }

        @Override
        public int getHeaderLayoutId() {
            return R.layout.item_store_list_secondary_header;
        }

        @Override
        public int getFooterLayoutId() {
            return R.layout.item_store_list_secondary_foot;
        }

        @Override
        public int getHeaderTextViewId() {
            return R.id.secondary_header;
        }

        @Override
        public int getSpanCountOfGridMode() {
            return SPAN_COUNT_FOR_GRID_MODE;
        }

        @Override
        public void onBindViewHolder(LinkageSecondaryViewHolder holder,
                                     BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {

            try {
                StoreFoodItem2Bean bean = item.info.getBean();

                ((TextView) holder.getView(R.id.iv_goods_name)).setText(item.info.getTitle() + "");
//            Picasso.with(mContext).load(item.info.getImgUrl()).into((ImageView) holder.getView(R.id.iv_goods_img));
                View view = holder.getView(R.id.iv_goods_item);
                view.setOnClickListener(v -> {

                    Intent intent = new Intent(mContext, FoodActivity.class);
                    intent.putExtra("skuId",bean.skuId);

                    StoreEvent storeEvent = new StoreEvent();
                    storeEvent.type = StoreEvent.TYPE_START_ACTIVITY;
                    storeEvent.intent = intent;
                    EventBus.getDefault().post(storeEvent);

                });

                AddRemoveView iv_goods_add = holder.getView(R.id.iv_goods_add);
                iv_goods_add.setNum(item.info.num);

                iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
                    @Override
                    public void onNumChangedListener(int num) {
                        StoreFoodItem2Bean bean = item.info.getBean();
                        int id = 0;
                        if (null != SPUtils.geTinstance().getUserInfo()) {
                            id = SPUtils.geTinstance().getUserInfo().getId();
                        }

                        AddChartBean addChartBean = new AddChartBean(num, bean.skuId, Integer.parseInt(storeId), BigDecimal.valueOf(bean.price));
                        AddChartEvent addChartEvent = new AddChartEvent(addChartBean);
                        EventBus.getDefault().post(addChartEvent);
                    }
                });
                TextView tv_choosetype = holder.getView(R.id.tv_choosetype);
                tv_choosetype.setOnClickListener(v -> {

                    EventBus.getDefault().post(new StoreEvent());
                });

//                if ((holder.getLayoutPosition() % 2) == 0) {
//                    iv_goods_add.setVisibility(View.GONE);
//                    tv_choosetype.setVisibility(View.VISIBLE);
//                } else {
                    iv_goods_add.setVisibility(View.VISIBLE);
                    tv_choosetype.setVisibility(View.GONE);
//                }



                TextView iv_goods_detail = holder.getView(R.id.iv_goods_detail);
                iv_goods_detail.setText("");
                TextView iv_goods_comment = holder.getView(R.id.iv_goods_comment);
                iv_goods_comment.setText("剩余100份  月售" + bean.sale);

                TextView iv_goods_price = holder.getView(R.id.iv_goods_price);
                iv_goods_price.setText("¥" + bean.price);
                TextView iv_goods_price_past = holder.getView(R.id.iv_goods_price_past);
                iv_goods_price_past.setText("¥" + bean.price);


                ImageView iv_goods_img = holder.getView(R.id.iv_goods_img);

                Picasso.with(iv_goods_img.getContext())
                        .load(bean.pic)
                        .fit()
                        .transform(new PicassoRoundTransform(DisplayUtil.dip2px(iv_goods_img.getContext(), 5), 0, PicassoRoundTransform.CornerType.ALL))
                        .into(iv_goods_img);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBindHeaderViewHolder(LinkageSecondaryHeaderViewHolder holder,
                                           BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {

            ((TextView) holder.getView(R.id.secondary_header)).setText(item.header + "");
        }

        @Override
        public void onBindFooterViewHolder(LinkageSecondaryFooterViewHolder holder,
                                           BaseGroupedItem<ElemeGroupedItem.ItemInfo> item) {

        }
    }
}
