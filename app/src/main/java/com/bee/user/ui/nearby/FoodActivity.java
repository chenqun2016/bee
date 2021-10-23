package com.bee.user.ui.nearby;

import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.CommentWrapBean;
import com.bee.user.bean.FoodDetailBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.home.BannerImageHolder;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.LogUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.listener.OnPageChangeListener;
import com.google.android.material.tabs.TabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  20：48
 * 描述：
 */
public class FoodActivity extends BaseActivity {
//    @BindView(R.id.app_barbar)
//    AppBarLayout app_barbar;

    //    @BindView(R.id.background)
//    View background;
    @BindView(R.id.ll_toolbar)
    RelativeLayout ll_toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @BindView(R.id.scrollview)
    NestedScrollView scrollview;

    @BindView(R.id.fl_title2)
    FrameLayout fl_title2;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    @BindView(R.id.ll_head2)
    LinearLayout ll_head2;

    @BindView(R.id.tv_food_title)
    TextView tv_food_title;
    @BindView(R.id.tv_selled)
    TextView tv_selled;

    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_money_past)
    TextView tv_money_past;


    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_point)
    TextView tv_point;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_sells)
    TextView tv_sells;
    @BindView(R.id.ll_mark)
    LinearLayout ll_mark;


    @BindView(R.id.tv_food_comment)
    TextView tv_food_comment;
    @BindView(R.id.line_food_comment)
    View line_food_comment;

    @BindView(R.id.banner2)
    ConvenientBanner banner2;
    @BindView(R.id.tv_index)
    TextView tv_index;

    @BindView(R.id.ll_foot)
    LinearLayout ll_foot;


    @BindView(R.id.iv_des)
    WebView iv_des;

    String[] titles = new String[]{"商品", "评价", "详情"};
    int storeId;
    int skuId;

    @OnClick({R.id.view1, R.id.view2, R.id.view3})
    public void onClick(View view) {
        scrollview.stopNestedScroll();

        int ll_toolbarHeight = ll_toolbar.getMeasuredHeight();

        int bannerHeight = banner2.getMeasuredHeight() - ll_toolbarHeight;
        LogUtil.d("bannerHeight" + bannerHeight + ",banner2.getMeasuredHeight()==" + banner2.getMeasuredHeight() + ",ll_toolbar.getMeasuredHeight()==" + ll_toolbar.getMeasuredHeight());
        int commentPoint = recyclerview.getTop() - ll_toolbarHeight - DisplayUtil.dip2px(FoodActivity.this, 50);//商品评价的位置
        int detailPoint = ll_foot.getTop() - ll_toolbarHeight;

        switch (view.getId()) {
            case R.id.view1:
                scrollview.smoothScrollTo(0, bannerHeight);
//                if (tabLayout.getSelectedTabPosition() != 0) {
//                    tabLayout.selectTab(tabLayout.getTabAt(0));
//                }
                break;
            case R.id.view2:
                scrollview.smoothScrollTo(0, commentPoint);
//                if (tabLayout.getSelectedTabPosition() != 1) {
//                    tabLayout.selectTab(tabLayout.getTabAt(1));
//                }
                break;
            case R.id.view3:
                scrollview.smoothScrollTo(0, detailPoint);
//                                scrollview.fullScroll(NestedScrollView.FOCUS_DOWN);
//                if (tabLayout.getSelectedTabPosition() != 2) {
//                    tabLayout.selectTab(tabLayout.getTabAt(2));
//                }
                break;
        }
    }

    CommentAdapter mAdapter;


    //banner 计数
    private int totalPage = 0;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_food2;
    }

    @Override
    public void initViews() {
        skuId = getIntent().getIntExtra("skuId", 0);
        storeId = getIntent().getIntExtra("storeId", 0);

        View iv_back2 = findViewById(R.id.iv_back2);
        if (null != iv_back2) {
            iv_back2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        ViewGroup.LayoutParams layoutParams = ll_toolbar.getLayoutParams();
        layoutParams.height += ImmersionBar.getStatusBarHeight(this);
        ll_toolbar.invalidate();


        fl_title2.setPadding(0, ImmersionBar.getStatusBarHeight(this), 0, 0);

        View view1 = View.inflate(this, R.layout.item_food_tab, null);
        TextView tv_text1 = view1.findViewById(R.id.tv_text);
        tv_text1.setText("商品");
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        view1 = View.inflate(this, R.layout.item_food_tab, null);
        tv_text1 = view1.findViewById(R.id.tv_text);
        tv_text1.setText("评价");
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        view1 = View.inflate(this, R.layout.item_food_tab, null);
        tv_text1 = view1.findViewById(R.id.tv_text);
        tv_text1.setText("详情");
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView tv_text = tab.getCustomView().findViewById(R.id.tv_text);
                tv_text.setTextSize(16);
                tv_text.getPaint().setFakeBoldText(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView tv_text = tab.getCustomView().findViewById(R.id.tv_text);
                tv_text.setTextSize(15);
                tv_text.getPaint().setFakeBoldText(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//
//
//                int scrollRangle = appBarLayout.getTotalScrollRange();
//
//                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);
//
//                if (verticalOffset == 0) {
//                    background.setAlpha(0);
//                } else if(Math.abs(verticalOffset) >= scrollRangle){
//                    background.setAlpha(1);
//                }else{
//                    //保留一位小数
//                    float alpha =( Math.abs(verticalOffset)) * 1.0f / scrollRangle;
//                    background.setAlpha(alpha);
//                }
//
//
//            }
//        });

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new CommentAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.getLoadMoreModule().setEnableLoadMore(false);


//        View head = View.inflate(this, R.layout.head_activity_food_comment, null);
//        View head2 = View.inflate(this, R.layout.head_activity_food_comment2, null);
//        initBanner(head);
//        initBanner2(head2);


//        mAdapter.addHeaderView(head,0);
//        mAdapter.addHeaderView(head2,1);

//        View bottom = View.inflate(this, R.layout.bottom_food_list, null);
//        mAdapter.addFooterView(bottom);


        getComments();
        initScroll();
        getDatas();

    }

    FoodDetailBean mBeans;

    private void getDatas() {
        Api.getClient(HttpRequest.baseUrl_goods).productDetail(skuId)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<FoodDetailBean>() {
                    @Override
                    public void onSuccess(FoodDetailBean beans) {
                        mBeans = beans;
                        initBanner2();
                        initBanner();
                        if(!TextUtils.isEmpty(beans.detailDesc)){
                            ll_foot.setVisibility(View.VISIBLE);
                            setWebView(beans.detailDesc);
                        }else{
                            ll_foot.setVisibility(View.GONE);
                        }
                        getStoreDetail();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    private void setWebView(String url) {
        WebSettings settings = iv_des.getSettings();
        if (getIntent().getBooleanExtra("scale", false)) {
            settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        }
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setLoadWithOverviewMode(true);
        settings.setSavePassword(false);
        settings.setJavaScriptEnabled(true);// 支持js功能
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDomStorageEnabled(true);//H5使用了在浏览器本地存储功能就必须加这句
        settings.setUseWideViewPort(true);
        iv_des.loadDataWithBaseURL(null,url,"text/html","utf-8",null);
    }

    /**
     * 刷新
     */
    private void getComments() {
        Api.getClient(HttpRequest.baseUrl_eva).queryListBySkuId(skuId + "", 1, LoadmoreUtils.PAGE_SIZE)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CommentWrapBean>() {
                    @Override
                    public void onSuccess(CommentWrapBean beans) {
                        mAdapter.setNewInstance(beans.records.size()>2?beans.records.subList(0,1):beans.records);

                        tv_food_comment.setText("商品评价("+beans.records.size()+")");
                        tv_food_comment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(FoodActivity.this, CommentActivity.class);
                                intent.putExtra("storeId", storeId);
                                intent.putExtra("skuId", skuId);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    private void initScroll() {

        recyclerview.post(new Runnable() {
            @Override
            public void run() {
                int ll_toolbarHeight = ll_toolbar.getMeasuredHeight();

                int bannerHeight = banner2.getMeasuredHeight() - ll_toolbarHeight;
                LogUtil.d("bannerHeight" + bannerHeight + ",banner2.getMeasuredHeight()==" + banner2.getMeasuredHeight() + ",ll_toolbar.getMeasuredHeight()==" + ll_toolbar.getMeasuredHeight());
                int commentPoint = recyclerview.getTop() - ll_toolbarHeight - DisplayUtil.dip2px(FoodActivity.this, 50);//商品评价的位置
                int detailPoint = ll_foot.getTop() - ll_toolbarHeight;

                fl_title2.setVisibility(View.GONE);
                ll_toolbar.setVisibility(View.VISIBLE);


                scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

                    @Override
                    public void onScrollChange(NestedScrollView v, int scrollX, int totalScrollY, int oldScrollX, int oldScrollY) {

                        if (totalScrollY < commentPoint) {
                            if (tabLayout.getSelectedTabPosition() != 0) {
                                tabLayout.selectTab(tabLayout.getTabAt(0));
                            }

                        } else if (totalScrollY >= detailPoint) {
                            if (tabLayout.getSelectedTabPosition() != 2) {
                                tabLayout.selectTab(tabLayout.getTabAt(2));
                            }
                        } else if (totalScrollY < detailPoint) {
                            if (tabLayout.getSelectedTabPosition() != 1) {
                                tabLayout.selectTab(tabLayout.getTabAt(1));
                            }
                        }


                        LogUtil.d(",scrollY==" + totalScrollY + ",oldScrollY==" + oldScrollY);

                        if (totalScrollY > oldScrollY) {
                            LogUtil.d("=====", "下滑");
                        }
                        if (totalScrollY < oldScrollY) {
                            LogUtil.d("=====", "上滑");
                        }

                        if (totalScrollY == 0) {
                            LogUtil.d("=====", "滑倒顶部");
                        }

                        if (totalScrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                            LogUtil.d("=====", "滑倒底部");
                        }

                        float lineValue = bannerHeight * 0.7f;

                        if (totalScrollY <= lineValue) {
                            ll_toolbar.setAlpha(1);
                            fl_title2.setAlpha(0);

                            fl_title2.setVisibility(View.GONE);
                            ll_toolbar.setVisibility(View.VISIBLE);
                        } else if (totalScrollY <= bannerHeight) {
                            ll_toolbar.setAlpha(1 - (float) (totalScrollY - lineValue) / (bannerHeight - lineValue));
                            fl_title2.setAlpha((float) (totalScrollY - lineValue) / (bannerHeight - lineValue));

                            fl_title2.setVisibility(View.VISIBLE);
                            ll_toolbar.setVisibility(View.VISIBLE);
                        } else {
                            ll_toolbar.setAlpha(0);
                            fl_title2.setAlpha(1);

                            fl_title2.setVisibility(View.VISIBLE);
                            ll_toolbar.setVisibility(View.GONE);
                        }


                    }
                });


            }
        });

    }


    private void initBanner2() {
        tv_money_past.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        tv_money_past.setText("¥"+mBeans.orginPrice+"");
        tv_money.setText(mBeans.price+"");
        tv_food_title.setText(mBeans.skuName);
        tv_selled.setText("已售" + mBeans.sale);



    }

    private void getStoreDetail() {
        Api.getClient(HttpRequest.baseUrl_shop).shop_getDetail(storeId + "").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StoreDetailBean>() {
                    @Override
                    public void onSuccess(StoreDetailBean data) {

                        if (null != data) {
                            tv_title.setText(data.getName() + "");
                            if (!TextUtils.isEmpty(data.getDistance())) {
                                tv_distance.setText(data.getDistance() + "公里");
                            }
                            if (!TextUtils.isEmpty(data.getDuration())) {
                                tv_time.setText("大约" + data.getDuration() + "分钟");
                            }
                            if (!TextUtils.isEmpty(data.getMonthSalesCount())) {
                                tv_sells.setText("月销" + data.getMonthSalesCount());
                            }

                            Picasso.with(FoodActivity.this)
                                    .load(data.getLogoUrl())
                                    .fit()
                                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(FoodActivity.this, 5), 0, PicassoRoundTransform.CornerType.ALL))
                                    .into(iv_icon);
                        }

//                        List<StoreBean.StoreTag> list = new ArrayList<>();
//                        list.add(new StoreBean.StoreTag(mBeans.sp1, 0));
//                        list.add(new StoreBean.StoreTag(mBeans.sp2, 0));
//                        list.add(new StoreBean.StoreTag(mBeans.sp3, 2));
//                        list.add(new StoreBean.StoreTag(mBeans.sp4, 2));
//
//                        CommonUtil.initTAGViews(ll_mark, list);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }


    private void initBanner() {
//        ConvenientBanner banner2 = head.findViewById(R.id.banner2);
//        TextView tv_index = head.findViewById(R.id.tv_index);

//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
//        params2.width = DisplayUtil.getWindowWidth(getActivity());
//        params2.height = (int) ((params2.width - DisplayUtil.dip2px(getContext(), 30)) * Constants.RATE_HOME) + DisplayUtil.dip2px(getContext(), 35);
//        mBanner.setLayoutParams(params2);
        BannerBean bannerBean = new BannerBean();
        bannerBean.imageUrl = mBeans.pic;

        List<BannerBean> adsList = new ArrayList<>();//banner数据
        adsList.add(bannerBean);
        adsList.add(bannerBean);
        adsList.add(bannerBean);

        totalPage = adsList.size();
        tv_index.setText(1 + "/" + totalPage);
        banner2.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerImageHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_food_image;
            }


        }, adsList);
        banner2.setPointViewVisible(false);
        banner2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        banner2.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }

            @Override
            public void onPageSelected(int index) {
                tv_index.setText((index + 1) + "/" + totalPage);
            }
        });

    }

//    private void showChooseTypeDialog(){
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        bottomSheetDialog.setContentView(R.layout.dialog_store_bottom_choose);
//        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
//        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
//        try {
//            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
//                    .setBackgroundResource(android.R.color.transparent);
//        }catch (Exception e){
//
//        }
//        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (bottomSheetDialog.isShowing()) {
//                    bottomSheetDialog.dismiss();
//                }
//            }
//        });
//        bottomSheetDialog.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (bottomSheetDialog.isShowing()) {
//                    bottomSheetDialog.dismiss();
//                }
//            }
//        });
//
//        bottomSheetDialog.show();
//    }

}
