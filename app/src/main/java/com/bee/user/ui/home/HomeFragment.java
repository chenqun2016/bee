package com.bee.user.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.BannerBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.HomeGridview2Bean;
import com.bee.user.entity.LunchEntity;
import com.bee.user.entity.NearbyEntity;
import com.bee.user.ui.CRecyclerViewActivity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.utils.LogUtil;
import com.bee.user.widget.MyGridView;
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
import butterknife.OnClick;
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

    @BindView(R.id.collapsing)
    public CollapsingToolbarLayout collapsing;
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerview;

    private List<BannerBean> adsList = new ArrayList<>();//banner数据
    private Dialog dingweiDialog;

    public static HomeFragment newInstance(int arg) {
        Bundle arguments = new Bundle();
        arguments.putInt("arg", arg);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    protected void getDatas() {

    }

    @OnClick({R.id.ll_tongzhi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tongzhi:

                if (null == dingweiDialog) {
                    dingweiDialog = new Dialog(getActivity(), R.style.loadingDialogTheme);
                    View inflate = View.inflate(getActivity(), R.layout.dialog_home_ditu, null);
                    inflate.findViewById(R.id.tv_guangguang).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (null != dingweiDialog) {
                                dingweiDialog.dismiss();
                            }

                        }
                    });
                    dingweiDialog.setContentView(inflate);
                }

                dingweiDialog.show();

                break;
        }
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        if (null != dingweiDialog && dingweiDialog.isShowing()) {
            dingweiDialog.dismiss();
        }
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
                    collapsing.setAlpha(1);
                } else if(verticalOffset >= scrollRangle){
                    collapsing.setAlpha(0);

                }else{
                    //保留一位小数
                    float alpha =(scrollRangle - Math.abs(verticalOffset)) * 1.0f / scrollRangle;
                    collapsing.setAlpha(alpha);
                }

            }
        });


        View headerViewBanner = View.inflate(getContext(), R.layout.item_home_banner, null);
        View headerView2 = View.inflate(getContext(), R.layout.item_home_2, null);
        View headerView3 = View.inflate(getContext(), R.layout.item_home_3, null);
        initHeaderViewBanner(headerViewBanner);
        initHeaderView2(headerView2);
        initHeaderView3(headerView3);


        HomeAdapter homeAdapter = new HomeAdapter();
        homeAdapter.addHeaderView(headerViewBanner);
        homeAdapter.addHeaderView(headerView2);
        homeAdapter.addHeaderView(headerView3);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview.setAdapter(homeAdapter);
        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                startActivity(new Intent(getContext(), FoodActivity.class));
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


    private void initHeaderView3(View headerView3) {
        MyGridView gridview = headerView3.findViewById(R.id.gridview);

        List<HomeGridview2Bean> homeGridview2Beans = new ArrayList<>();
        HomeGridview2Bean bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "00:00:00";
        bean.money = "10.0";
        bean.title = "限时秒杀";

        homeGridview2Beans.add(bean);
         bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "";
        bean.money = "10.0";
        bean.title = "精选午餐";
        homeGridview2Beans.add(bean);
         bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "";
        bean.money = "10.0";
        bean.title = "销量排行榜";
        homeGridview2Beans.add(bean);
        bean = new HomeGridview2Bean();
        bean.name = "鸡胸肉沙拉";
        bean.time = "";
        bean.money = "10.0";
        bean.title = "附近好店";
        homeGridview2Beans.add(bean);

        gridview.setAdapter(new HomeGridview2Adapter(getActivity(), homeGridview2Beans));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent   intent = new Intent(getContext(), MiaoshaActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent   intent1 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent1.putExtra("title", "精选午餐");
                        intent1.putExtra("entity", LunchEntity.class.getName());
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent   intent2 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent2.putExtra("title", "销量排行榜");
                        intent2.putExtra("entity", LunchEntity.class.getName());
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent  intent3 = new Intent(getContext(), CRecyclerViewActivity.class);
                        intent3.putExtra("title", "附近好店");
                        intent3.putExtra("entity", NearbyEntity.class.getName());
                        startActivity(intent3);
                        break;
                }
            }
        });
    }

    private void initHeaderView2(View headerView2) {
        MyGridView gridview = headerView2.findViewById(R.id.gridview);

        gridview.setAdapter(new HomeGridviewAdapter(getActivity()));
    }

    private void initHeaderViewBanner(View headerViewBanner) {
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

    }


}
