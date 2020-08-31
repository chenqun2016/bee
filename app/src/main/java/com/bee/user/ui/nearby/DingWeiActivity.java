package com.bee.user.ui.nearby;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.DingWeiBean;
import com.bee.user.ui.base.activity.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  16：47
 * 描述：
 */
public class DingWeiActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dingwei;
    }

    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DingWeiBean> dingWeiBeans = new ArrayList<>();
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        dingWeiBeans.add(new DingWeiBean());
        DingWeiAdapter dingWeiAdapter = new DingWeiAdapter(dingWeiBeans);
        recyclerview.setAdapter(dingWeiAdapter);
    }
}
