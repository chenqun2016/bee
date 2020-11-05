package com.bee.user.ui.giftcard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.GiftcardRecordBean;
import com.bee.user.entity.GiftcardRecordEntity;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/05  18：43
 * 描述：
 */
public class GiftcardRecordFregment extends BaseFragment {


    public CRecyclerView crecyclerview;
    int type;

    public static GiftcardRecordFregment newInstance(int type) {
        Bundle arguments = new Bundle();
        arguments.putInt("type",type);
        GiftcardRecordFregment fragment = new GiftcardRecordFregment();
        fragment.setArguments(arguments);
        return fragment;
    }


    @Override
    protected void getDatas() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.model_crecyclerview,container,false);
        crecyclerview = view.findViewById(R.id.crecyclerview);


        Bundle arguments = getArguments();
        type = arguments.getInt("type",0);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        crecyclerview.setView(GiftcardRecordEntity.class).start();


        ArrayList<GiftcardRecordBean> beans = new ArrayList<GiftcardRecordBean>();
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());



        crecyclerview.getBaseAdapter().setList(beans);
    }

}
