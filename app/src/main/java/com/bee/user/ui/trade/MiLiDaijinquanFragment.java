package com.bee.user.ui.trade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.MiLiChongzhiBean;
import com.bee.user.ui.adapter.MiLiChongzhiAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.widget.MyGridView;

import java.util.ArrayList;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  21：55
 * 描述：
 */
public class MiLiDaijinquanFragment extends BaseFragment {
    @Override
    protected void getDatas() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milidaijinquan,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }


}
