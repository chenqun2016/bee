package com.bee.user.ui.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
}
