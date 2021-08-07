package com.bee.user.ui.mine.coupon;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import butterknife.OnClick;

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

    //跳转到第几个
    int index;
    String[] titles = new String[]{"优惠券", "配送卡"};

    public static Intent newIntent(Context context,int index) {
        Intent intent = new Intent(context, CouponActivity.class);
        intent.putExtra("index", index);
        return intent;
    }


    @OnClick({R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this, UnuseCouponActivity.class));
                break;

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }


    @Override
    public void initViews() {
        Intent intent = getIntent();
        index = intent.getIntExtra("index", 0);
        vp.setAdapter(new FragmentAdapter(this));

        new TabLayoutMediator(tabLayout, vp, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                if(index < titles.length){
                    tabLayout.getTabAt(index).select();
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
            if(0 == position){
                return CouponFragment.newInstance(0);
            }else{
                return new PeiSongCardFragment();
            }
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

}
