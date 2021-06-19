package com.bee.user.ui.nearby;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.bean.StoreListBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
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
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  20：27
 * 描述：
 */
public class NearbyFragment extends BaseFragment {

//    private CRecyclerView<StoreBean> crecyclerview;

    private View status_bar1;

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
         status_bar1 = view.findViewById(R.id.status_bar1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new NearbyAdapter();
        recyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getContext(), StoreActivity.class);
                List<StoreListBean.RecordsBean> data = mAdapter.getData();
                if(null != data && null != data.get(position)){
                    intent.putExtra("id",data.get(position).id+"");
                }

                startActivity(intent);
            }
        });



        AMapLocation location = SPUtils.geTinstance().getLocation();
        Map<String, String> map = new HashMap<>();
        if(null != location){
            map.put("longitude", location.getLongitude()+"");
            map.put("latitude", location.getLatitude()+"");
            map.put("address", location.getAddress());
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


    public static class NearbyAdapter extends BaseQuickAdapter<StoreListBean.RecordsBean, BaseViewHolder> implements LoadMoreModule {

        public NearbyAdapter() {
            super(R.layout.item_nearby);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, StoreListBean.RecordsBean bean) {

            TextView tv_title = helper.getView(R.id.tv_title);
            tv_title.setText(bean.name);

            TextView tv_point = helper.getView(R.id.tv_point);
            tv_point.setText("");
            TextView tv_distance = helper.getView(R.id.tv_distance);
            if(!TextUtils.isEmpty(bean.distance)){
                tv_distance.setText(bean.distance+"");
            }

            TextView tv_time = helper.getView(R.id.tv_time);

            if(!TextUtils.isEmpty(bean.duration)){
                tv_time.setText(bean.duration+"");
            }

            TextView tv_sells = helper.getView(R.id.tv_sells);

            if(!TextUtils.isEmpty(bean.monthSalesCount)){
                tv_sells.setText(bean.monthSalesCount+"");
            }


            ImageView iv_icon = helper.getView(R.id.iv_icon);

            Context mContext = iv_icon.getContext();

            Picasso.with(mContext)
                    .load(bean.logoUrl)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(mContext,5),0, PicassoRoundTransform.CornerType.ALL))
                    .into(iv_icon);

            RecyclerView recyclerview = helper.getView(R.id.recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerview.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
//        recyclerview.addItemDecoration(dividerItemDecoration);


            NearbyStoreFoodAdapter homeFooterAdapter = new NearbyStoreFoodAdapter(bean.products);
            homeFooterAdapter.setOnItemClickListener(new NearbyStoreFoodAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(String id) {
                    Intent intent = new Intent(recyclerview.getContext(), StoreActivity.class);
                    intent.putExtra("id",bean.id+"");
                    recyclerview.getContext().startActivity(intent);
                }
            });
            recyclerview.setAdapter(homeFooterAdapter);




//        标签

            LinearLayout ll_mark = helper.getView(R.id.ll_mark);
            CommonUtil.initTAGViews(ll_mark);


        }


    }

}
