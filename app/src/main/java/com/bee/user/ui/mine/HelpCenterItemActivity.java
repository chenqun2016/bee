package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/28  19：45
 * 描述：
 */
public class HelpCenterItemActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    private HelpCenterActivity.HelpCenterAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_helpcenter_item;
    }

    @Override
    public void initViews() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        toolbar_title.setText(title);

        List<String> strings = new ArrayList<>();
        strings.add("1、目前APP有那些支付方式？");
        strings.add("2、目前APP有那些支付方式？");
        strings.add("3、目前APP有那些支付方式？");
        strings.add("4、目前APP有那些支付方式？");
        strings.add("5、目前APP有那些支付方式？");
        strings.add("6、目前APP有那些支付方式？");
        strings.add("7、目前APP有那些支付方式？");

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new HelpCenterActivity.HelpCenterAdapter();
        recyclerview.setAdapter(mAdapter);

        mAdapter.setList(strings);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(HelpCenterItemActivity.this, HelpCenterItemItemActivity.class);
                intent.putExtra("title",strings.get(position));

                startActivity(intent);
            }
        });
    }
}
