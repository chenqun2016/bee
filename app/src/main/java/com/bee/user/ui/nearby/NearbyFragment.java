package com.bee.user.ui.nearby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.entity.NearbyEntity;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.adapter.NearbyStoreFoodAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.huaxiafinance.www.crecyclerview.crecyclerView.BaseResult;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  20：27
 * 描述：
 */
public class NearbyFragment extends BaseFragment {

//    private CRecyclerView<StoreBean> crecyclerview;

    private RecyclerView recyclerview;
    NearbyAdapter   mAdapter;
    LoadmoreUtils loadmoreUtils;


    public static NearbyFragment newInstance() {
        Bundle arguments = new Bundle();
        NearbyFragment fragment = new NearbyFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nearby_list,container,false);
//        crecyclerview =  view.findViewById(R.id.crecyclerview);
//        crecyclerview.setView(NearbyEntity.class);

        recyclerview =  view.findViewById(R.id.recyclerview);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new NearbyAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getContext(), StoreActivity.class));
            }
        });



        AMapLocation location = SPUtils.geTinstance().getLocation();
        Map<String, String> map = new HashMap<>();
        if(null != location){
            map.put("longitude", location.getLongitude()+"");
            map.put("latitude", location.getLatitude()+"");
            map.put("address", location.getAddress());
            map.put("address", "上海浦东川杨新苑二期一幢二号");
        }
        map.put("maxDistance", 1000+"");
        map.put("productCategoryId", 1+"");
        map.put("productCategoryName", 1+"");
        loadmoreUtils = new LoadmoreUtils(CommentBean.class){
            @Override
            protected boolean getDatas(int page) {

                Api.getClient(HttpRequest.baseUrl_shop).shop_nearby(page,LoadmoreUtils.PAGE_SIZE,Api.getRequestBody(map)) .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<StoreListBean>() {
                            @Override
                            public void onSuccess(StoreListBean storeListBean) {
                                List data = storeListBean.getRecords();
                                data.add(new StoreBean());
                                loadmoreUtils.onSuccess(mAdapter,data);
                            }

                            @Override
                            public void onFail(String fail) {
                                super.onFail(fail);
                                loadmoreUtils.onFail(mAdapter,fail);
                            }
                        });
                return true;
            }
        };
        loadmoreUtils.initLoadmore(mAdapter);
    }

    @Override
    protected void getDatas() {
//        if(null != crecyclerview) {
//            crecyclerview.start();
//
//        }



        loadmoreUtils.refresh(mAdapter);
    }


    public static class NearbyAdapter extends BaseQuickAdapter<StoreBean, BaseViewHolder> implements LoadMoreModule {

        public NearbyAdapter() {
            super(R.layout.item_nearby);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, StoreBean bean) {


            ImageView iv_icon = helper.getView(R.id.iv_icon);

            Context mContext = iv_icon.getContext();

            Picasso.with(mContext)
                    .load(R.drawable.food2)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(mContext,5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(iv_icon);

            RecyclerView recyclerview = helper.getView(R.id.recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerview.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
//        recyclerview.addItemDecoration(dividerItemDecoration);
            List<StoreBean.StoreFood> storeFoods = new ArrayList<>();
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","10"));
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","11"));
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","12"));
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","13"));
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","14"));
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","15"));
            storeFoods.add(new StoreBean.StoreFood("切角榴莲蛋糕","16"));

            NearbyStoreFoodAdapter homeFooterAdapter = new NearbyStoreFoodAdapter(storeFoods);
            homeFooterAdapter.setOnItemClickListener(new NearbyStoreFoodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id) {
                    recyclerview.getContext().startActivity(new Intent(recyclerview.getContext(), StoreActivity.class));
                }
            });
            recyclerview.setAdapter(homeFooterAdapter);




//        标签

            LinearLayout ll_mark = helper.getView(R.id.ll_mark);
            CommonUtil.initTAGViews(ll_mark);


        }


    }

}
