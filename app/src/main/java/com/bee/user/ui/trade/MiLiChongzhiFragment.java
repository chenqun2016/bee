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
import com.bee.user.bean.OrderBean;
import com.bee.user.entity.OrderEntity;
import com.bee.user.ui.adapter.MiLiChongzhiAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.widget.MyGridView;

import java.util.ArrayList;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  21：04
 * 描述：
 */
public class MiLiChongzhiFragment extends BaseFragment {
    @Override
    protected void getDatas() {

    }
    MiLiChongzhiAdapter miLiChongzhiAdapter;
    MyGridView gridview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milichongzhi,container,false);
        gridview  = view.findViewById(R.id.gridview);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<MiLiChongzhiBean> miLiChongzhiBeans = new ArrayList<>();
        miLiChongzhiBeans.add(new MiLiChongzhiBean("50米粒","送10米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean("100米粒","送20米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean("200米粒","送40米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean("300米粒","送50米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean("500米粒","送100米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean("1000米粒","送300米粒"));
        miLiChongzhiAdapter   = new MiLiChongzhiAdapter(getContext(), miLiChongzhiBeans);
        gridview.setAdapter(miLiChongzhiAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                miLiChongzhiAdapter.setSelected(i);
                miLiChongzhiAdapter.notifyDataSetChanged();
            }
        });

    }


}
