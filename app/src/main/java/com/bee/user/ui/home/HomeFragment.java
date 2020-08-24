package com.bee.user.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.Constants;
import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/23  13：42
 * 描述：
 */
public class HomeFragment extends BaseFragment {
    private Unbinder unbinder;

    @BindView(R.id.appbar)
    public AppBarLayout appbar;

    @BindView(R.id.recyclerview)
    public RecyclerView recyclerview;

    private List<BannerBean> adsList = new ArrayList<>();//banner数据


    public static HomeFragment newInstance(int arg) {
        Bundle arguments = new Bundle();
        arguments.putInt("arg", arg);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.activity_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int scrollRangle = appBarLayout.getTotalScrollRange();

                LogUtil.d("verticalOffset=="+verticalOffset+"--scrollRangle=="+scrollRangle);

                if (verticalOffset == 0) {
                    appbar.setAlpha(1);
                } else if(verticalOffset >= scrollRangle){
                    appbar.setAlpha(0);

                }else{
                    //保留一位小数
                    float alpha =(scrollRangle - Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    appbar.setAlpha(alpha);
                }

            }
        });


        View  headerViewBanner = View.inflate(getContext(), R.layout.item_home_banner, null);

        ConvenientBanner mBanner = (ConvenientBanner) headerViewBanner.findViewById(R.id.banner2);
//        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mBanner.getLayoutParams();
//        params2.width = DisplayUtil.getWindowWidth(getActivity());
//        params2.height = (int) ((params2.width - DisplayUtil.dip2px(getContext(), 30)) * Constants.RATE_HOME) + DisplayUtil.dip2px(getContext(), 35);
//        mBanner.setLayoutParams(params2);
        BannerBean bannerBean = new BannerBean();
        bannerBean.url = R.drawable.banner;
        adsList.add(bannerBean);
        adsList.add(bannerBean);
        adsList.add(bannerBean);
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerImageHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_home_banner_image;
            }



        }, adsList);
        mBanner.setPageIndicator(new int[]{R.drawable.point_banner_grey, R.drawable.point_banner_yellow});
        mBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);


        HomeAdapter homeAdapter = new HomeAdapter();
        homeAdapter.addHeaderView(headerViewBanner);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });

        ArrayList<HomeBean> homeBeans = new ArrayList<>();
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeAdapter.setNewInstance(homeBeans);
    }


}
