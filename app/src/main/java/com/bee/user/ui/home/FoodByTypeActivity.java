package com.bee.user.ui.home;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bee.user.R;
import com.bee.user.bean.CatogoryBean;
import com.bee.user.bean.HomeCatogoryBean;
import com.bee.user.bean.StoreFoodItem2Bean;
import com.bee.user.event.MainEvent;
import com.bee.user.params.HomeCatogoryParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.search.SearchActivity;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.RadioGroupPlus;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/01  21：52
 * 描述：
 */
public class FoodByTypeActivity extends BaseActivity {
    @BindView(R.id.cl_content)
    CoordinatorLayout cl_content;

    @BindView(R.id.iv_chart)
    ImageButton iv_chart;
    @BindView(R.id.iv_back)
    ImageButton iv_back;

    @BindView(R.id.ll_toolbar)
    Toolbar ll_toolbar;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.iv_red)
    ImageView iv_red;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.iv_search)
    ImageButton iv_search;

    @BindView(R.id.app_barbar)
    AppBarLayout app_barbar;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.fl_top_tabs)
    FrameLayout fl_top_tabs;
    @BindView(R.id.rv_top_tabs)
    RecyclerView rv_top_tabs;
    @BindView(R.id.rgp_tags)
    RadioGroupPlus rgp_tags;
    @BindView(R.id.rb_4)
    RadioButton rb_4;

    HomeAdapter homeAdapter;
    int index;
    private int type = 1;
    LoadmoreUtils loadmoreUtils;
    @Override
    public int getLayoutId() {
        return R.layout.activity_food_by_type;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    private void initTopTabs() {
        rv_top_tabs.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        TopTabsAdapter topTabsAdapter = new TopTabsAdapter();
        topTabsAdapter.currentPosition = index;
        rv_top_tabs.setAdapter(topTabsAdapter);
        topTabsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                index = position;
                int prePosition = topTabsAdapter.currentPosition;
                topTabsAdapter.currentPosition = position;
                topTabsAdapter.notifyItemChanged(prePosition);
                topTabsAdapter.notifyItemChanged(position);
                getFoodDatas(1);
            }
        });
        topTabsAdapter.setNewInstance(mTopTabs);
        if(index >=2){
            rv_top_tabs.scrollToPosition(index-1);
        }

    }
    List<HomeCatogoryBean> mTopTabs;
    @Override
    public void initViews() {
        Intent intent = getIntent();
        mTopTabs = (List<HomeCatogoryBean>) intent.getSerializableExtra("data");
        String title = intent.getStringExtra("title");
        index = intent.getIntExtra("index", 0);
        toolbar_title.setText(title + "");
        tv_search.setText(title + "");

        homeAdapter = new HomeAdapter();
        homeAdapter.setListener(new HomeAdapter.OnAddChartListener() {
            @Override
            public void onAddChart() {
                iv_red.setVisibility(View.VISIBLE);
            }
        });
        homeAdapter.setAddChartAnimatorView(cl_content,iv_chart);
//        recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager( 2, StaggeredGridLayoutManager.VERTICAL));
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                StoreFoodItem2Bean bean = homeAdapter.getData().get(position);
                FoodActivity.newInstance(FoodByTypeActivity.this, bean.shopProductId, bean.storeId, bean.skuId);
            }
        });

        rgp_tags.check(R.id.rb_1);
        rb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("onClick==" + v.getId() + "");
                if (type == 5) {
                    Drawable icon = getResources().getDrawable(R.drawable.icon_jiantou_xia);
                    int width = icon.getIntrinsicWidth();
                    int height = icon.getIntrinsicHeight();
                    icon.setBounds(0, 0, width, height);
                    rb_4.setCompoundDrawables(null, null, icon, null);
                    type = 4;
                }
                if (type == 4) {
                    Drawable icon = getResources().getDrawable(R.drawable.icon_jiantou_xia);
                    int width = icon.getIntrinsicWidth();
                    int height = icon.getIntrinsicHeight();
                    icon.setBounds(0, 0, width, height);
                    rb_4.setCompoundDrawables(null, null, icon, null);
                    type = 5;
                }
                getFoodDatas(1);
            }
        });
        rgp_tags.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_1:
                        if (type != 1) {
                            type = 1;
                            getFoodDatas(1);
                        }
                        break;
                    case R.id.rb_2:
                        if (type != 2) {
                            type = 2;
                            getFoodDatas(1);
                        }
                        break;
                    case R.id.rb_3:
                        if (type != 3) {
                            type = 3;
                            getFoodDatas(1);
                        }
                        break;
                    case R.id.rb_4:

                        break;

                }
            }
        });

        loadmoreUtils = new LoadmoreUtils() {
            @Override
            protected void getDatas(int page) {
                getFoodDatas(page);
            }
        };
        loadmoreUtils.initLoadmore(homeAdapter);
        loadmoreUtils.refresh(homeAdapter);

        initToolbar();
        initTopTabs();
    }

    private void initToolbar() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fl_top_tabs.getLayoutParams();
        layoutParams.topMargin = layoutParams.topMargin + ImmersionBar.getStatusBarHeight(this);
        ll_toolbar.setPadding(0,ImmersionBar.getStatusBarHeight(this),0,0);
        Drawable background = ll_toolbar.getBackground();
        background .setAlpha(0);

        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRangle = appBarLayout.getTotalScrollRange();

                int aVerticalOffset = Math.abs(verticalOffset);
                Log.d(TAG, "onOffsetChanged: "+scrollRangle+"/verticalOffset=="+aVerticalOffset);
                if (aVerticalOffset < scrollRangle/2) {
                    background.setAlpha(0);
                    iv_back.setImageResource(R.drawable.icon_back_bai);
                    iv_chart.setImageResource(R.drawable.icon_toubu_gouwuche_bai);

                    ll_search.setVisibility(View.VISIBLE);
                    iv_search.setVisibility(View.GONE);
                } else if (aVerticalOffset >= scrollRangle/2) {
                    background.setAlpha(255);
                    iv_back.setImageResource(R.drawable.icon_back_anse);
                    iv_chart.setImageResource(R.drawable.icon_toubu_gouwuche_hei);

                    ll_search.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
                }
                //保留一位小数
                float alpha = Math.abs(aVerticalOffset) * 1.0f / scrollRangle;
                background.setAlpha((int)(alpha*255));
            }
        });
    }


    private void getFoodDatas(int page) {
        HomeCatogoryParams homeCatogoryParams = new HomeCatogoryParams();
        homeCatogoryParams.data = new HomeCatogoryParams.DataBean(mTopTabs.get(index).categoryId, type);
        homeCatogoryParams.pageNum = page;
        homeCatogoryParams.pageSize = 10;
        Api.getClient(HttpRequest.baseUrl_goods).getGoodsByCatogory(Api.getRequestBody(homeCatogoryParams)).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CatogoryBean>() {
                    @Override
                    public void onSuccess(CatogoryBean data) {
                        loadmoreUtils.onSuccess(homeAdapter,data.data);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(homeAdapter, fail);
                    }
                });
    }

    public static class TopTabsAdapter extends BaseQuickAdapter<HomeCatogoryBean, BaseViewHolder>{

        public int currentPosition;

        public TopTabsAdapter() {
            super(R.layout.item_catogory_top_tab);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeCatogoryBean homeCatogoryBean) {
            ImageView iv_image = baseViewHolder.findView(R.id.iv_image);
            TextView tv_text = baseViewHolder.findView(R.id.tv_text);

            Picasso.with(iv_image.getContext())
                    .load(homeCatogoryBean.icon)
                    .fit()
                    .into(iv_image);
            tv_text.setText(homeCatogoryBean.name);

            if(currentPosition == baseViewHolder.getLayoutPosition()){
                tv_text.setTextColor(ContextCompat.getColor(tv_text.getContext(),R.color.color_282525));
                iv_image.setBackgroundResource(R.drawable.icon_zhezhao);
                tv_text.setTypeface(Typeface.DEFAULT_BOLD);
            }else{
                tv_text.setTextColor(ContextCompat.getColor(tv_text.getContext(),R.color.color_7B7777));
                iv_image.setBackgroundResource(0);
                tv_text.setTypeface(Typeface.DEFAULT);
            }
        }
    }

    @OnClick({ R.id.ll_search, R.id.iv_search,R.id.iv_chart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
            case R.id.ll_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.iv_chart:
                MainEvent mainEvent = new MainEvent(MainEvent.TYPE_set_index);
                mainEvent.index = 2;
                EventBus.getDefault().post(mainEvent);
                finish();
                break;
        }
    }
}
