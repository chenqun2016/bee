package com.bee.user.ui.chart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.HomeBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.entity.NearbyEntity;
import com.bee.user.ui.adapter.ChartAdapter;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.order.OrderActivity;
import com.bee.user.utils.DisplayUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/03  21：42
 * 描述：
 */
public class ChartFragment extends BaseFragment {
    Unbinder bind;
    @BindView(R.id.ll_nonet)
    LinearLayout ll_nonet;
    @BindView(R.id.ll_nodata)
    LinearLayout ll_nodata;
    @BindView(R.id.ll_havedata)
    RelativeLayout ll_havedata;


    @BindView(R.id.recyclerview_tuijian)
    RecyclerView recyclerview_tuijian;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @OnClick({R.id.tv_confirm})
    public void onClick(View view){
        Intent intent = new Intent(getContext(), OrderActivity.class);
        startActivity(intent);
    }

    @Override
    protected void getDatas() {

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chart_nodata, container, false);
        bind = ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();

    }

    private void initViews() {


        ll_nonet.setVisibility(View.GONE);
        ll_nodata.setVisibility(View.GONE);
        ll_havedata.setVisibility(View.VISIBLE);

        initNoNet();
        initNoDatas();
        initDatas();
    }

    private void initDatas() {
        ChartAdapter adapter = new ChartAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(adapter);

        View foot = View.inflate(getContext(), R.layout.foot_chart, null);
        ImageView imageview = foot.findViewById(R.id.imageview);
        Picasso.with(getContext()).
                load(R.drawable.banner).
                fit().
                transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),10),0, PicassoRoundTransform.CornerType.ALL)).
                into(imageview);
        adapter.addFooterView(foot);

        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setItemViewCacheSize(200);
        RecyclerView.RecycledViewPool recycledViewPool = new
                RecyclerView.RecycledViewPool();
        recyclerview.setRecycledViewPool(recycledViewPool);

        ArrayList<StoreBean> beans = new ArrayList<>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        adapter.setNewInstance(beans);
    }

    private void initNoNet() {

    }

    private void initNoDatas() {
        HomeAdapter homeAdapter = new HomeAdapter();
        recyclerview_tuijian.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerview_tuijian.setAdapter(homeAdapter);

        View head = View.inflate(getContext(), R.layout.head_fragment_chart_nodata, null);
        homeAdapter.addHeaderView(head);

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
}
