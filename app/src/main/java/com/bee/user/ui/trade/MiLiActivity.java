package com.bee.user.ui.trade;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    @BindView(R.id.tv_mili)
    TextView tv_mili;

    String[] titles = new String[]{"在线充值", "充值卡/代金券"};


    @OnClick({R.id.trade_list})
    public void onClick(View view){
        startActivity(new Intent(this,TradeListActivity.class));
    }

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

        getMiLiDatas();
    }
    MyMiLiBean miLiBean;
    private void getMiLiDatas() {
        Api.getClient(HttpRequest.baseUrl_pay).getMemberRice().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MyMiLiBean>() {
                    @Override
                    public void onSuccess(MyMiLiBean s) {
                        miLiBean = s;
                        tv_mili.setText(s.surplusAmount+"");
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
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
