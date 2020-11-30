package com.bee.user.ui.xiadan;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.ui.adapter.ChooseAddressAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  17：40
 * 描述：
 */
public class ChooseAddressActivity extends BaseActivity {
    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    ArrayList<AddressBean> addressBeans;
    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_address;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
    }



    @OnClick({R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this,NewAddressActivity.class));
                break;

        }
    }

    @Override
    public void initViews() {
        toolbar_title.setText("收货地址");

        tv_right.setText("新增地址");
        tv_right.setVisibility(View.VISIBLE);

        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        ChooseAddressAdapter chooseAddressAdapter = new ChooseAddressAdapter();
        recyclerview1.setAdapter(chooseAddressAdapter);
        chooseAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if(position < addressBeans.size()){
                    Intent intent = new Intent();
                    intent.putExtra("address",addressBeans.get(position));
                    setResult(1,intent);
                    finish();
                }
            }
        });


        addressBeans = new ArrayList<>();
        addressBeans.add(new AddressBean(0));
        addressBeans.add(new AddressBean(0));

        chooseAddressAdapter.setNewInstance(addressBeans);

        List<AddressBean> lists2 = new ArrayList<>();
        AddressBean Bean = new AddressBean();
        Bean.viewType = YouhuiquanBean.type2;
        lists2.add(Bean);

        lists2.add(new AddressBean(1));
        lists2.add(new AddressBean(1));
        chooseAddressAdapter.addData(lists2);
    }
}
