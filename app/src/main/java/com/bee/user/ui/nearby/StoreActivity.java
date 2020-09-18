package com.bee.user.ui.nearby;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.FoodTypeBean;
import com.bee.user.event.StoreEvent;
import com.bee.user.ui.adapter.FoodChooseTypeAdapter;
import com.bee.user.ui.adapter.SelectedFoodAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.DragDialogLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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


    private Fragment[] mFragments;
    String[] titles = new String[]{"菜单", "评价", "商家"};
    private PopupWindow popupWindow;
    private ObjectAnimator objectAnimatorY;

    @OnClick({R.id.cl_qujiesuan, R.id.tv_dingwei})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cl_qujiesuan:
                showSelectedDialog();
                break;
            case R.id.tv_dingwei:

                startActivity(new Intent(this, DingWeiActivity.class));
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


        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != popupWindow && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }

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


    private void showSelectedDialog() {
        showPopWindow();

//        BottomSheetDialog showSelectedDialog = new BottomSheetDialog(this);
//        showSelectedDialog.setContentView(R.layout.dialog_store_bottom_selected);
//        showSelectedDialog.findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if( showSelectedDialog.isShowing()){
//                    showSelectedDialog.dismiss();
//                }
//
//            }
//        });
//
//
//        RecyclerView recyclerview = showSelectedDialog.findViewById(R.id.recyclerview);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//        List<FoodBean> foodBeans = new ArrayList<>();
//        foodBeans.add(new FoodBean());
//        foodBeans.add(new FoodBean());
//        foodBeans.add(new FoodBean());
//        foodBeans.add(new FoodBean());
//
//        SelectedFoodAdapter selectedFoodAdapter = new SelectedFoodAdapter(foodBeans);
//        recyclerview.setAdapter(selectedFoodAdapter);
//        selectedFoodAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//
//            }
//        });
//
//        try {
//            showSelectedDialog.getWindow().findViewById(R.id.design_bottom_sheet)
//                    .setBackgroundResource(android.R.color.transparent);
//        }catch (Exception e){
//
//        }
//        showSelectedDialog.show();
    }

    //    int height;
    private void showPopWindow() {
        float windowHeight = DisplayUtil.getWindowHeight(this);
        float[] y = {0, -1400f};
        LogUtil.d("windowHeight", "windowHeight==" + windowHeight);
        objectAnimatorY = ObjectAnimator.ofFloat(view_selected, "translationY", y);
        objectAnimatorY.setDuration(2000);
        objectAnimatorY.start();


//        if (null == popupWindow) {
//            View view  = View.inflate(this, R.layout.dialog_store_bottom_selected, null);
//            height = view.getHeight();
//
//            view.findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(null != popupWindow && popupWindow.isShowing()){
//                        popupWindow.dismiss();
//                    }
//
//                }
//            });
//
//
//            RecyclerView recyclerview = view.findViewById(R.id.recyclerview);
//            recyclerview.setLayoutManager(new LinearLayoutManager(this));
//
//            List<FoodBean> foodBeans = new ArrayList<>();
//            foodBeans.add(new FoodBean());
//            foodBeans.add(new FoodBean());
//            foodBeans.add(new FoodBean());
//            foodBeans.add(new FoodBean());
//
//            SelectedFoodAdapter selectedFoodAdapter = new SelectedFoodAdapter(foodBeans);
//            recyclerview.setAdapter(selectedFoodAdapter);
//            selectedFoodAdapter.setOnItemClickListener(new OnItemClickListener() {
//                @Override
//                public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
//
//                }
//            });
//
//
//            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//
//
//            popupWindow.setAnimationStyle(R.style.AnimTools);
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            popupWindow.setFocusable(true);
//            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    setWindowBackground(false);
//                }
//            });
//            popupWindow.update();
//            popupWindow.setOutsideTouchable(true);
//        }
//        setWindowBackground(true);
//        int measuredHeight = cl_qujiesuan.getMeasuredHeight();
//        popupWindow.showAsDropDown(cl_qujiesuan, 0, -(measuredHeight+height+1500));
    }

    public void setWindowBackground(boolean b) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (b) {
            lp.alpha = 0.5f;
        } else {
            lp.alpha = 1f;
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(StoreEvent event) {

        showChooseTypeDialog();
    }
}
