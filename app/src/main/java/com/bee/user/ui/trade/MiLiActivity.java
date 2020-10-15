package com.bee.user.ui.trade;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  17：15
 * 描述：
 */
public class MiLiActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;



    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;

    String[] titles = new String[]{"在线充值", "充值卡/代金券"};


    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mili;
    }

    @Override
    public void initViews() {
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);


        int index = getIntent().getIntExtra("index", 0);



        vp.setAdapter(new FragmentAdapter(this));
//        vp.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.getTabAt(index).select();
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
            if(0 == position){
                return new MiLiChongzhiFragment();
            }else{
                return new MiLiDaijinquanFragment();
            }

        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }


}
