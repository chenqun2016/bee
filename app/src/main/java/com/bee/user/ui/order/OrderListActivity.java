package com.bee.user.ui.order;

import android.content.Context;
import android.content.Intent;

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

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  13：43
 * 描述：
 */
public class OrderListActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;

    String[] titles = new String[]{"全部", "待支付", "已收货", "待评价", "退款"};
    private Fragment[] mFragments;


    public static Intent newInstance(Context context, int index) {
        Intent intent = new Intent(context, OrderListActivity.class);
        intent.putExtra("index",index);
        return intent;
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    public void initViews() {
        int index = getIntent().getIntExtra("index", 0);
        mFragments = new Fragment[titles.length];

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
        Fragment fragment = OrderListFragment.newInstance(index);

        mFragments[index] = fragment;
        return mFragments[index];
    }

}
