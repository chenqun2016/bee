package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.event.StoreEvent;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;

    private Fragment[] mFragments;
    String[] titles = new String[]{"菜单","评价","商家"};

    @OnClick({R.id.cl_qujiesuan})
    public void onClick(View view){

    }


        @Override
    protected void initImmersionBar() {
         ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        EventBus.getDefault().register(this);
        mFragments = new Fragment[titles.length];
        initViews();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);

        ViewGroup.LayoutParams layoutParams1 = background.getLayoutParams();
        layoutParams1.height = ImmersionBar.getStatusBarHeight(this)+DisplayUtil.dip2px(this,44);

        vp.setAdapter(new FragmentAdapter(this));
        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();




        app_barbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);

                if (verticalOffset == 0) {
                    background.setAlpha(0);
                    iv_back.setImageResource(R.drawable.icon_back_bai);
                    tv_search_1.setVisibility(View.GONE);
                    iv_search.setVisibility(View.VISIBLE);
                    iv_shoucang.setImageResource(R.drawable.icon_shoucang);
                    iv_more.setImageResource(R.drawable.icon_more);
                } else if(Math.abs(verticalOffset) >= scrollRangle){
                    background.setAlpha(1);
                    tv_search_1.setVisibility(View.VISIBLE);
                    iv_back.setImageResource(R.drawable.icon_back_anse);
                    iv_search.setVisibility(View.GONE);
                    iv_shoucang.setImageResource(R.drawable.icon_shoucang_1);
                    iv_more.setImageResource(R.drawable.icon_more_shen);
                }else{
                    //保留一位小数
                    float alpha =( Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                }


            }
        });
    }

    private void showChooseTypeDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_store_bottom);
        bottomSheetDialog.findViewById(R.id.iv_goods_add).setVisibility(View.GONE);
        bottomSheetDialog.findViewById(R.id.iv_goods_comment).setVisibility(View.GONE);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }catch (Exception e){

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
        switch (index){
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
