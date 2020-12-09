package com.huaxiafinance.www.crecyclerview.cindicatorview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by chenqun on 2017/2/21.
 */

public abstract class LazyFragment extends Fragment {
    private boolean isFirstLoad = true; // 是否第一次加载

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isFirstLoad = true;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (isFirstLoad) {
            // 将数据加载逻辑放到onResume()方法中
            getDatas();
            isFirstLoad = false;
        }
    }

    protected abstract void getDatas();

}
