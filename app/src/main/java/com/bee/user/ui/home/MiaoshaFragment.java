package com.bee.user.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.GoodsBySectionBean;
import com.bee.user.params.GetGoodsBySectionParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.GoodsBySectionAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.nearby.StoreActivity;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;
import org.jetbrains.annotations.NotNull;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  17：06
 * 描述：
 */
public class MiaoshaFragment extends BaseFragment implements OnItemClickListener {
    private Unbinder unbinder;
    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipe_refresh_layout;
    @BindView(R.id.recyclerview)
    public RecyclerView recyclerview;

    private LoadmoreUtils loadmoreUtils;

    private GoodsBySectionAdapter goodsBySectionAdapter;
    private int id;

    private CRecyclerView<FoodBean> view;

    public static MiaoshaFragment newInstance(int id) {
        Bundle arguments = new Bundle();
        arguments.putInt("id", id);
        MiaoshaFragment fragment = new MiaoshaFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_miaosha, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        id = arguments.getInt("id",0);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        goodsBySectionAdapter = new GoodsBySectionAdapter();
        recyclerview.setAdapter(goodsBySectionAdapter);
        goodsBySectionAdapter.addChildClickViewIds(R.id.tv_status);
        goodsBySectionAdapter.setOnItemClickListener(this);
        initLoadMore();

    }

    @Override
    protected void getDatas() {
        loadmoreUtils.refresh(goodsBySectionAdapter);
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadmoreUtils = new LoadmoreUtils() {

            @Override
            protected void getDatas(int page) {
                getGoodsBySection(page);
            }
        };
        loadmoreUtils.initLoadmore(goodsBySectionAdapter,swipe_refresh_layout);
    }

    private void getGoodsBySection(int page) {

        GetGoodsBySectionParams params = new GetGoodsBySectionParams();
        params.pageNum = page;
        params.pageSize = LoadmoreUtils.PAGE_SIZE;
        params.data = new GetGoodsBySectionParams.GetGoodsBySectionData(id);
        Api.getClient(HttpRequest.baseUrl_shop).getGoodsBySection(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<GoodsBySectionBean>() {
                    @Override
                    public void onSuccess(GoodsBySectionBean o) {
                        List<GoodsBySectionBean.RecordBean> records = o.getRecords();
                        loadmoreUtils.onSuccess(goodsBySectionAdapter,records);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(goodsBySectionAdapter,fail);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        List<GoodsBySectionBean.RecordBean> data = goodsBySectionAdapter.getData();
        GoodsBySectionBean.RecordBean recordBean = data.get(position);
        Intent intent = new Intent(getContext(), FoodActivity.class);
        intent.putExtra("shopProductId", recordBean.getShopProductId());
        intent.putExtra("skuId",recordBean.getSkuId());
        startActivity(intent);
    }
}
