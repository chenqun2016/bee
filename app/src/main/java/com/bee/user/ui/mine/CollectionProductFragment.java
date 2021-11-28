package com.bee.user.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bee.user.R;
import com.bee.user.bean.CollectionStoreBean;
import com.bee.user.params.AddFavoritesParams;
import com.bee.user.params.CollectionStoreParams;
import com.bee.user.params.UpAndDownFavoritesParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.CollectionProductAdapter;
import com.bee.user.ui.adapter.CollectionStoreAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CollectionProductFragment extends BaseFragment implements OnItemChildClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    LoadmoreUtils loadmoreUtils;
    CollectionProductAdapter  collectionStoreAdapter;
    public static CollectionProductFragment newInstance() {
        CollectionProductFragment fragment = new CollectionProductFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_collection_store, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        collectionStoreAdapter = new CollectionProductAdapter();
        recyclerview.setAdapter(collectionStoreAdapter);
        collectionStoreAdapter.addChildClickViewIds(R.id.tv_delete,R.id.tv_top);
        collectionStoreAdapter.setOnItemChildClickListener(this);
        initLoadMore();
    }

    @Override
    protected void getDatas() {
        loadmoreUtils.refresh(collectionStoreAdapter);
    }

    /**
     * 初始化加载更多
     */
    private void initLoadMore() {
        loadmoreUtils = new LoadmoreUtils() {

            @Override
            protected void getDatas(int page) {
                getCollectionStore(page);
            }
        };
        loadmoreUtils.initLoadmore(collectionStoreAdapter,swiperefresh);
    }

    /**
     * 获取收藏商家
     * @param page
     */
    private void getCollectionStore(int page) {
        CollectionStoreParams params = new CollectionStoreParams();
        params.pageNum = page;
        params.pageSize = LoadmoreUtils.PAGE_SIZE;
        params.favoritesType = "GOOD";
        Api.getClient(HttpRequest.baseUrl_member).getMemberFavorites(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<CollectionStoreBean>() {
                    @Override
                    public void onSuccess(CollectionStoreBean o) {
                        loadmoreUtils.onSuccess(collectionStoreAdapter,o.getRecords());
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(collectionStoreAdapter,fail);
                    }
                });
    }

    @Override
    public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
        List<CollectionStoreBean.RecordBean> data = collectionStoreAdapter.getData();
        CollectionStoreBean.RecordBean recordBean = data.get(position);
        switch (view.getId()) {
            case R.id.tv_delete:
                removeStore(recordBean);
                break;
            case R.id.tv_top:
                setTopOrNo(recordBean,position);
                break;
        }
    }

    /**
     * 取消关注店铺
     * @param recordBean
     */
    private void removeStore(CollectionStoreBean.RecordBean recordBean) {
        AddFavoritesParams params = new AddFavoritesParams();
        params.setBizId(recordBean.getBizId());
        params.favoritesType = "GOOD";
        Api.getClient(HttpRequest.baseUrl_member).cancleFavorites(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        collectionStoreAdapter.remove(recordBean);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);

                    }
                });
    }

    /**
     * 置顶店铺/取消置顶
     * @param recordBean
     * @param position
     */
    private void setTopOrNo(CollectionStoreBean.RecordBean recordBean, int position) {
        UpAndDownFavoritesParams params = new UpAndDownFavoritesParams();
        params.setBizId(recordBean.getBizId());
        params.setTop(recordBean.getTop()==0?1:0);
        params.favoritesType = "SHOP";
        Api.getClient(HttpRequest.baseUrl_member).upAndDownFavorites(Api.getRequestBody(params)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        recordBean.setTop(recordBean.getTop()==0?1:0);
                        if(collectionStoreAdapter.getData().size()>1) {
                            collectionStoreAdapter.remove(recordBean);
                            CollectionStoreBean.RecordBean recordBean1 = collectionStoreAdapter.getData().get(0);
                            recordBean1.setTop(0);
                            collectionStoreAdapter.addData(0,recordBean);
                            collectionStoreAdapter.setData(1,recordBean1);
                            collectionStoreAdapter.notifyItemMoved(position,0);
                            collectionStoreAdapter.notifyItemRangeChanged(Math.min(position, 0), Math.abs(position - 0) + 1);
                        }else {
                            collectionStoreAdapter.setData(0,recordBean);
                        }

                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);

                    }
                });
    }
}