package com.bee.user.ui.xiadan;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.YouhuiquanBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.ChooseAddressAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.ui.xiadan.NewAddressActivity.REQUEST_CODE_NEW;
import static com.bee.user.ui.xiadan.NewAddressActivity.REQUEST_CODE_OLD;
import static com.bee.user.ui.xiadan.NewAddressActivity.RESULT_CODE_NEWADDRESS;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/17  17：40
 * 描述：
 */
public class ChooseAddressActivity extends BaseActivity {
    public static final  int REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING = 88;
    public static final  int REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_Secectlocation = 89;

    public static final int RESULT_CODE_CHANGED= 90;
    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;


    ChooseAddressAdapter chooseAddressAdapter;
    private int showOrNot;//是否展示超出配送范围地址 0：不展示  1：展示
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
                Intent intent = new Intent(ChooseAddressActivity.this, NewAddressActivity.class);
                startActivityForResult(intent,REQUEST_CODE_NEW);
                break;

        }
    }

    @Override
    public void initViews() {
        int from = getIntent().getIntExtra("from", 0);
        showOrNot = getIntent().getIntExtra("showOrNot", 1);
        toolbar_title.setText("收货地址");

        tv_right.setText("新增地址");
        tv_right.setVisibility(View.VISIBLE);

        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        chooseAddressAdapter = new ChooseAddressAdapter(from);
        recyclerview1.setAdapter(chooseAddressAdapter);
        chooseAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if(position < chooseAddressAdapter.getData().size()){
                    if(1 == from){
                        Intent intent = new Intent();
                        intent.putExtra("address", chooseAddressAdapter.getData().get(position));
                        setResult(1, intent);
                        finish();
                    }else{
                        Intent intent = new Intent(ChooseAddressActivity.this, NewAddressActivity.class);
                        intent.putExtra("address",chooseAddressAdapter.getData().get(position));
                        intent.putExtra("clickPosition",position);
                        startActivityForResult(intent,REQUEST_CODE_OLD);
                    }
                }
            }
        });
        getAddress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_OLD && resultCode == RESULT_CODE_NEWADDRESS ){
            AddressBean address = (AddressBean)data .getSerializableExtra("address");
            int clickPosition = data.getIntExtra("clickPosition", 0);
            if(null != address){
                chooseAddressAdapter.setData(clickPosition,address);
            }else{
                chooseAddressAdapter.removeAt(clickPosition);
            }
            setResult(RESULT_CODE_CHANGED);
        }else if(requestCode == REQUEST_CODE_NEW && resultCode == RESULT_CODE_NEWADDRESS ){
            getAddress();
            setResult(RESULT_CODE_CHANGED);
        }
    }

    private void getAddress() {
        Api.getClient(HttpRequest.baseUrl_member).listAddress().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<AddressBean>>() {
                    @Override
                    public void onSuccess(List<AddressBean> addressBean2) {
                        if(null != addressBean2 && addressBean2.size()>0){
//                            if(addressBean2.size() > 4){
//                                chooseAddressAdapter.setNewInstance(addressBean2.subList(0,3));
//                            }else{
                                chooseAddressAdapter.setNewInstance(addressBean2);
//                            }

                            if(showOrNot == 1) {
                                List<AddressBean> lists2 = new ArrayList<>();
                                AddressBean Bean = new AddressBean();
                                Bean.viewType = YouhuiquanBean.type2;
                                lists2.add(Bean);

                                lists2.add(new AddressBean(1));
                                lists2.add(new AddressBean(1));
                                chooseAddressAdapter.addData(lists2);
                            }

                        }
                    }
                });
    }
}
