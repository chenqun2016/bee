package com.bee.user.ui.nearby;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.FoodTypeBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.StoreDetailBean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.MainEvent;
import com.bee.user.event.StoreEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.FoodChooseTypeAdapter;
import com.bee.user.ui.adapter.SelectedFoodAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.search.SearchFoodActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.DragDialogLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.squareup.picasso.Picasso;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/27  14：41
 * 描述：
 */
public class StoreActivity extends BaseActivity {
    @BindView(R.id.app_barbar)
    AppBarLayout app_barbar;

    @BindView(R.id.status_bar1)
    View status_bar1;
    @BindView(R.id.background)
    View background;



    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.ll_mark)
    LinearLayout ll_mark;


    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_distance)
    TextView tv_distance;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_sells)
    TextView tv_sells;


    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_search_1)
    TextView tv_search_1;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_shoucang)
    ImageView iv_shoucang;
    @BindView(R.id.iv_more)
    ImageView iv_more;
    @BindView(R.id.tv_dingwei)
    ImageView tv_dingwei;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;

    @BindView(R.id.cl_qujiesuan)
    ConstraintLayout cl_qujiesuan;
    @BindView(R.id.view_selected)
    DragDialogLayout view_selected;
    @BindView(R.id.view_background)
    View view_background;

    private Fragment[] mFragments;
    String[] titles = new String[]{"菜单", "评价", "商家"};

    private int windowHeight;
    private int heightSelected;

    ViewGroup.LayoutParams params;

    StoreDetailBean storeDetailBean;

    @OnClick({R.id.tv_confirm,R.id.cl_qujiesuan, R.id.tv_dingwei,R.id.view_background,
    R.id.tv_search_1,R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tv_confirm:
                startActivity(new Intent(this, OrderingActivity.class));
                break;
            case R.id.cl_qujiesuan:
                showSelectedDialog();
                break;
            case R.id.view_background:

                close();
                break;

            case R.id.tv_dingwei:

                startActivity(new Intent(this, DingWeiActivity.class));
                break;

            case R.id.iv_search:
            case R.id.tv_search_1:
                startActivity(new Intent(this, SearchFoodActivity.class));
                break;
        }

    }


    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        mFragments = new Fragment[titles.length];
        windowHeight = DisplayUtil.getWindowHeight(this);


        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);

        ViewGroup.LayoutParams layoutParams1 = background.getLayoutParams();
        layoutParams1.height = ImmersionBar.getStatusBarHeight(this) + DisplayUtil.dip2px(this, 44);

        vp.setAdapter(new FragmentAdapter(this));
        vp.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int selectedTabPosition = tabLayout.getSelectedTabPosition();
                switch (selectedTabPosition) {
                    case 0:
                        cl_qujiesuan.setVisibility(View.VISIBLE);
                        view_selected.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                    case 2:
                        cl_qujiesuan.setVisibility(View.GONE);
                        view_selected.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tv_dingwei.setVisibility(View.VISIBLE);

        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset==" + verticalOffset + "--scrollRangle==" + scrollRangle);

                if (verticalOffset == 0) {
                    background.setAlpha(0);
                    iv_back.setImageResource(R.drawable.icon_back_bai);
                    tv_search_1.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
                    iv_shoucang.setImageResource(R.drawable.icon_shoucang);
                    iv_more.setImageResource(R.drawable.icon_more);
                } else if (Math.abs(verticalOffset) >= scrollRangle) {
                    background.setAlpha(1);
                    tv_search_1.setVisibility(View.VISIBLE);
                    iv_back.setImageResource(R.drawable.icon_back_anse);
                    iv_search.setVisibility(View.GONE);
                    iv_shoucang.setImageResource(R.drawable.icon_shoucang_1);
                    iv_more.setImageResource(R.drawable.icon_more_shen);
                } else {
                    //保留一位小数
                    float alpha = (Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                }


            }
        });

        initSelectedFoodDialog();
        getDatas();
    }

    private void getDatas() {

        Api.getClient(HttpRequest.baseUrl_shop).shop_getDetail(9+"") .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<StoreDetailBean>() {
                    @Override
                    public void onSuccess(StoreDetailBean storeDetailBean) {
                        StoreActivity.this.storeDetailBean = storeDetailBean;
                        setViews(storeDetailBean);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    private void setViews(StoreDetailBean storeDetailBean) {

        Picasso.with(this)
                .load(storeDetailBean.getLogoUrl())
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,5),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_icon);
        CommonUtil.initTAGViews(ll_mark);

        tv_title.setText(storeDetailBean.getName());
        tv_distance.setText(storeDetailBean.getDistance()+"公里");
        tv_time.setText("大约"+storeDetailBean.getDuration()+"分钟");
        tv_sells.setText("月销"+storeDetailBean.getMonthSalesCount());
    }

    private void initSelectedFoodDialog() {
        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });


        view_selected.setNextPageListener(new DragDialogLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                closeOhter();

                params.height = 0;
                view_selected.setLayoutParams(params);

                isShow = !isShow;
            }
        });

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        List<FoodBean> foodBeans = new ArrayList<>();
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());
        foodBeans.add(new FoodBean());

        SelectedFoodAdapter selectedFoodAdapter = new SelectedFoodAdapter(foodBeans);
        recyclerview.setAdapter(selectedFoodAdapter);
        selectedFoodAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

            }
        });

        view_background.setAlpha(0);
        view_selected.post(new Runnable() {
            @Override
            public void run() {
                heightSelected = view_selected.getMeasuredHeight();

                if (heightSelected > windowHeight / 2f) {
                    heightSelected = windowHeight / 2;
                }
                params = view_selected.getLayoutParams();
                params.height = 0;
                view_selected.setLayoutParams(params);
                view_selected.setVisibility(View.VISIBLE);
            }
        });
    }


    private void showChooseTypeDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_store_bottom_choose);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        FoodChooseTypeAdapter foodChooseTypeAdapter = new FoodChooseTypeAdapter();
        recyclerview.setAdapter(foodChooseTypeAdapter);

        List<String> dataSource = new ArrayList<>();
        dataSource.add("热");
        dataSource.add("温热");
        dataSource.add("多冰");
        dataSource.add("少冰");
        dataSource.add("去冰");
        FoodTypeBean foodTypeBean = new FoodTypeBean();
        foodTypeBean.title = "温度";
        foodTypeBean.lists = dataSource;

        List<FoodTypeBean> datas = new ArrayList<>();
        datas.add(foodTypeBean);
        datas.add(foodTypeBean);

        foodChooseTypeAdapter.setNewInstance(datas);


        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }




    final class FragmentAdapter extends FragmentStateAdapter {


        public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return createFragments(position);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }


    private Fragment createFragments(Integer index) {
        if (mFragments[index] != null) {
            return mFragments[index];
        }
        Fragment fragment = null;
        switch (index) {
            case 0:

                fragment = StoreFragment.newInstance(DisplayUtil.getWindowHeight(this) - app_barbar.getMeasuredHeight());
                break;
            case 1:
                fragment = new CommentFragment();
                break;
            case 2:
                fragment = new StoreDesFragment();
                break;
        }
        mFragments[index] = fragment;
        return mFragments[index];
    }














//已选商品相关代码

    boolean isShow = false;

    private void showSelectedDialog() {
        if (!isShow) {
            show();

        } else {
            close();
        }


    }

    private ValueAnimator showAnimation;
    private ValueAnimator closeAnimation;
    private ValueAnimator alphaAnimation1;
    private ValueAnimator alphaAnimation2;


    public void show() {
        if (null != alphaAnimation2 && alphaAnimation2.isRunning()) {
            alphaAnimation2.end();
        }

        view_background.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view_background.setLayoutParams(layoutParams);

        if (null == alphaAnimation1) {
            alphaAnimation1 = ValueAnimator.ofFloat(0, 1);
            alphaAnimation1.setDuration(300);
            alphaAnimation1.setInterpolator(new LinearInterpolator());
            alphaAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view_background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation1.start();




        if (null != closeAnimation && closeAnimation.isRunning()) {
            closeAnimation.end();
        }

        if (null == showAnimation) {
            showAnimation = ValueAnimator.ofInt(0, heightSelected);
            showAnimation.setDuration(300);
            showAnimation.setInterpolator(new LinearInterpolator());
            showAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    view_selected.setLayoutParams(params);
                }
            });
        }
        showAnimation.start();

        isShow = !isShow;
    }

    public void close() {
        closeOhter();


        if (null == closeAnimation) {
            closeAnimation = ValueAnimator.ofInt(heightSelected, 0);
            closeAnimation.setDuration(300);
            closeAnimation.setInterpolator(new LinearInterpolator());
            closeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    view_selected.setLayoutParams(params);
                }
            });
        }

        closeAnimation.start();

        isShow = !isShow;
    }

    private void closeOhter() {
        if (null != alphaAnimation1 && alphaAnimation1.isRunning()) {
            alphaAnimation1.end();
        }

        if (null == alphaAnimation2) {
            alphaAnimation2 = ValueAnimator.ofFloat(1, 0);
            alphaAnimation2.setDuration(300);
            alphaAnimation2.setInterpolator(new LinearInterpolator());
            alphaAnimation2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    view_background.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
                    layoutParams.height = 1;
                    layoutParams.width = 1;
                    view_background.setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    view_background.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
                    layoutParams.height = 1;
                    layoutParams.width = 1;
                    view_background.setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            alphaAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view_background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation2.start();



        if (null != showAnimation && showAnimation.isRunning()) {
            showAnimation.end();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStoreEvent(StoreEvent event) {
        showChooseTypeDialog();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        finish();
    }
}
