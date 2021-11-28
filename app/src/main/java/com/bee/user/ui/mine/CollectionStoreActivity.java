package com.bee.user.ui.mine;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.mine.coupon.PeiSongCardFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 - @Description: 收藏的店
 - @Author: bxy
 - @Time:  2021/11/28 下午2:18
 */
public class CollectionStoreActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager2 vp;

    //跳转到第几个
    int index = 0;
    String[] titles = new String[]{"商家", "商品"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_collection_store;
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
                return CollectionStoreFragment.newInstance();
            }else{
                return CollectionProductFragment.newInstance();
            }
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

}