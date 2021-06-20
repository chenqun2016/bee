package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.HelpTypeItemBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.HelpTypeItemAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import java.util.List;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/28  19：45
 * 描述：
 */
public class HelpCenterItemActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    private HelpTypeItemAdapter mAdapter;
    private int typeId = -1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_helpcenter_item;
    }

    @Override
    public void initViews() {

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        typeId = intent.getIntExtra("typeId",0);
        toolbar_title.setText(title);

        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        mAdapter = new HelpTypeItemAdapter();
        recyclerview.setAdapter(mAdapter);

        getHelpTypeItem();


        mAdapter.setOnItemClickListener(this);
    }

    /**
     * 获取帮助内容列表
     */
    private void getHelpTypeItem() {
        Api.getClient(HttpRequest.baseUrl_sys).helpTypeItem(typeId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<HelpTypeItemBean>>() {
                    @Override
                    public void onSuccess(List<HelpTypeItemBean> helpTypeItemBean) {
                        if(null != helpTypeItemBean && helpTypeItemBean.size()>0){
                            mAdapter.setList(helpTypeItemBean);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        List<HelpTypeItemBean> data = (List<HelpTypeItemBean>) adapter.getData();
        if(data!=null&&data.size()>0) {
            Intent intent = new Intent(HelpCenterItemActivity.this, HelpCenterItemItemActivity.class);
            intent.putExtra("title",data.get(position).getTitle());
            intent.putExtra("content",data.get(position).getContent());
            intent.putExtra("contentId",data.get(position).getId());
            intent.putExtra("id",data.get(position).getTypeId());
            startActivity(intent);
        }
    }
}
