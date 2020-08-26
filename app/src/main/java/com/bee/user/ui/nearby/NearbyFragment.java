package com.bee.user.ui.nearby;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.FoodBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.entity.LunchEntity;
import com.bee.user.entity.StoreEntity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.home.MiaoshaFragment;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  20：27
 * 描述：
 */
public class NearbyFragment extends BaseFragment {

    private CRecyclerView<StoreBean> crecyclerview;

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
        crecyclerview =  view.findViewById(R.id.crecyclerview);
        crecyclerview.setView(StoreEntity.class);
        return view;
    }

    @Override
    protected void getDatas() {
        if(null != crecyclerview) {
            crecyclerview.start();


            ArrayList<StoreBean> StoreBeans = new ArrayList<StoreBean>();
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            StoreBeans.add(new StoreBean());
            crecyclerview.getBaseAdapter().setList(StoreBeans);
        }
    }


}
