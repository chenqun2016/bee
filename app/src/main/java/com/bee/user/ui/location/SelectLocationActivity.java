package com.bee.user.ui.location;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.ui.adapter.ChooseAddressAdapter;
import com.bee.user.ui.adapter.DingWeiAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/12/07  20：19
 * 描述：
 */
public class SelectLocationActivity extends BaseActivity {
    @BindView(R.id.recyclerview1)
    RecyclerView recyclerview1;

    @BindView(R.id.recyclerview2)
    RecyclerView recyclerview2;

    @OnClick({R.id.tv_select,R.id.tv_location})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_select:
                startActivity(new Intent(this, SelectLocationActivity.class));
                break;

            case R.id.tv_location:
                break;
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.activity_select_location;
    }

    @Override
    public void initViews() {
        recyclerview1.setLayoutManager(new LinearLayoutManager(this));

        ChooseAddressAdapter2 chooseAddressAdapter = new ChooseAddressAdapter2();
        recyclerview1.setAdapter(chooseAddressAdapter);


        ArrayList addressBeans = new ArrayList<>();
        addressBeans.add(new AddressBean(0));
        addressBeans.add(new AddressBean(0));

        chooseAddressAdapter.setNewInstance(addressBeans);



        recyclerview2.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DingWeiBean> dingWeiBeans = new ArrayList<>();
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        DingWeiAdapter2 dingWeiAdapter = new DingWeiAdapter2(dingWeiBeans);
        recyclerview2.setAdapter(dingWeiAdapter);

    }


    public static class ChooseAddressAdapter2 extends BaseQuickAdapter<AddressBean, BaseViewHolder> {
        public ChooseAddressAdapter2() {
            super(R.layout.item_chooose_address);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, AddressBean addressBean) {
            TextView tv_address = baseViewHolder.findView(R.id.tv_address);
            TextView tv_tag = baseViewHolder.findView(R.id.tv_tag);
            TextView tv_address2 = baseViewHolder.findView(R.id.tv_address2);
            TextView tv_name = baseViewHolder.findView(R.id.tv_name);


            baseViewHolder.findView(R.id.imageview).setVisibility(View.INVISIBLE);
        }
    }

    public static class DingWeiAdapter2 extends BaseQuickAdapter<DingWeiBean, BaseViewHolder> {
        public DingWeiAdapter2( @Nullable List<DingWeiBean> data) {
            super(R.layout.item_dingwei2, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, DingWeiBean dingWeiBean) {

        }
    }
}
