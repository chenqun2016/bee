package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;


import butterknife.BindView;

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


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;

    private Fragment[] mFragments;
    String[] titles = new String[]{"菜单","评价","商家"};

        @Override
    protected void initImmersionBar() {
         ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store);

        mFragments = new Fragment[titles.length];
        initViews();

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
                } else if(verticalOffset >= scrollRangle){
                    background.setAlpha(1);

                }else{
                    //保留一位小数
                    float alpha =( Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    background.setAlpha(alpha);
                }

            }
        });
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
                fragment = StoreFragment.newInstance(DisplayUtil.getWindowHeight(this) - app_barbar.getMeasuredHeight());
                break;
            case 2:
                fragment = StoreFragment.newInstance(DisplayUtil.getWindowHeight(this) - app_barbar.getMeasuredHeight());
                break;
        }
        mFragments[index] = fragment;
        return mFragments[index];
    }
}
