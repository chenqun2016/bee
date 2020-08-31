package com.bee.user.ui.base.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BasePreActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/20  21：31
 * 描述：基类
 */
public abstract class BaseActivity extends BasePreActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        initViewsPre();
        initViews();
    }

    private void initViewsPre() {
        View iv_back = findViewById(R.id.iv_back);
        if(null != iv_back){
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public abstract int getLayoutId() ;
    public abstract void initViews();
}
