package com.bee.user.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.bean.FoodBean;
import com.bee.user.entity.LunchEntity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/26  17：06
 * 描述：
 */
public class MiaoshaFragment extends BaseFragment {
    private CRecyclerView<FoodBean> view;

    public static MiaoshaFragment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type", type);
        MiaoshaFragment fragment = new MiaoshaFragment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = new CRecyclerView<>(getContext());
        view.setView(LunchEntity.class);
        return view;
    }

    @Override
    protected void getDatas() {
        if(null != view){
            view.start();

            List foodBeans = new ArrayList<FoodBean>();
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            foodBeans.add(new FoodBean());
            view.getBaseAdapter().setList(foodBeans);
        }
    }
}
