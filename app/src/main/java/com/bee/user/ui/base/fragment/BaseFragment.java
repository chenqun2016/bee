package com.bee.user.ui.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.R;
import com.huaxiafinance.www.crecyclerview.cindicatorview.LazyFragment;

import org.jetbrains.annotations.NotNull;

import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/22  21：14
 * 描述：
 */
abstract  public class BaseFragment extends LazyFragment {

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        super.onViewCreated(view, savedInstanceState);
    }

    SwipeRefreshLayout swipeRefreshLayout;
    protected void initSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout){
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDatas();
            }
        });
    }
    protected void endSwipeRefreshLayout(){
        if(null != swipeRefreshLayout && swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
