package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.CommentBean;
import com.bee.user.ui.adapter.CommentAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/28  19：15
 * 描述：
 */
public class HelpCenterActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;


    private  HelpCenterAdapter  mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_helpcenter;
    }

    @OnClick(R.id.tv_phone)
    public void onClick(View view){
        CommonUtil.callPhone(this,"10010");
    }

    @Override
    public void initViews() {
        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new HelpCenterAdapter();
        recyclerview.setAdapter(mAdapter);

        List<String> strings = new ArrayList<>();
        strings.add("支付问题");
        strings.add("商家问题");
        strings.add("订单问题");
        strings.add("优惠问题");
        strings.add("配送问题");
        strings.add("商品问题");
        strings.add("其他");

        mAdapter.setList(strings);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(HelpCenterActivity.this, HelpCenterItemActivity.class);
                intent.putExtra("title",strings.get(position));

                startActivity(intent);
            }
        });
    }




    public static  class HelpCenterAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

        public HelpCenterAdapter() {
            super(R.layout.item_helpcentr);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, String bean) {

            View line = helper.findView(R.id.line);

            TextView tv_text = helper.findView(R.id.tv_text);
            tv_text.setText(bean);

            if(0 == helper.getLayoutPosition()){
                line.setVisibility(View.GONE);
            }else{
                line.setVisibility(View.VISIBLE);
            }
        }


    }
}
