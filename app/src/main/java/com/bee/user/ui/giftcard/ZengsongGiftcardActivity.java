package com.bee.user.ui.giftcard;

import android.content.Intent;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.bee.user.R;
import com.bee.user.bean.GiftcardRecordBean;
import com.bee.user.entity.GiftcardRecordEntity;
import com.bee.user.entity.ZengsongGiftcardEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.ToastUtil;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/06  15：19
 * 描述：
 */
public class ZengsongGiftcardActivity extends BaseActivity {
    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;

    @OnClick({R.id.tv_2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_2:
//                List<GiftcardRecordBean> datas = crecyclerview.getBaseAdapter().getData();
//                List<GiftcardRecordBean> data = new ArrayList<>();
//
//                for(GiftcardRecordBean bean : datas){
//
//                    if(bean.isSelected){
//                        data.add(bean);
//                    }
//                }
//                if(0 == data.size()){
//                    ToastUtil.ToastShort(ZengsongGiftcardActivity.this,"请选择礼品卡");
//                    return;
//                }
//
//                ToastUtil.ToastShort(ZengsongGiftcardActivity.this,"已经赠送");
//                startActivity(new Intent(ZengsongGiftcardActivity.this,GetGiftcardActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_zengsong_giftcard;
    }

    @Override
    public void initViews() {
        crecyclerview.setView(ZengsongGiftcardEntity.class).start();


        ArrayList<GiftcardRecordBean> beans = new ArrayList<GiftcardRecordBean>();
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());
        beans.add(new GiftcardRecordBean());


        crecyclerview.getBaseAdapter().setList(beans);
    }
}
