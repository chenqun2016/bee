package com.bee.user.ui.nearby;

import androidx.fragment.app.FragmentManager;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/31  21：33
 * 描述：
 */
public class CommentActivity extends BaseActivity {

//    @BindView(R.id.rgp_tags)
//    RadioGroupPlus rgp_tags;


    int storeId;
    int skuId;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    public void initViews() {
        skuId  = getIntent().getIntExtra("skuId",0);
        storeId = getIntent().getIntExtra("storeId", 0);


        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_content,  new CommentFragment(storeId+"",1))
                .commitAllowingStateLoss();
    }
}
