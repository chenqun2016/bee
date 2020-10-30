package com.bee.user.ui.mine;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.trade.MiLiActivity;
import com.bee.user.ui.trade.MiLiChongzhiFragment;
import com.bee.user.ui.trade.MiLiDaijinquanFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/27  20：30
 * 描述：
 */
public class CouponActivity extends BaseActivity {



    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;


    String[] titles = new String[]{"优惠券", "失效券"};


    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initViews() {
        vp.setAdapter(new FragmentAdapter(this));

        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();


        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.getTabAt(0).select();
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
                return CouponFragment.newInstance(0);
            }else{
                return CouponFragment.newInstance(1);
            }

        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

}
