package com.bee.user.ui.mine;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.HelpTypeBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/28  19：15
 * 描述：
 */
public class HelpCenterActivity extends BaseActivity implements OnItemClickListener {

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
        getHelpType();

        mAdapter.setOnItemClickListener(this);
    }

    /**
     * 获取帮助类型
     */
    private void getHelpType() {
        Api.getClient(HttpRequest.baseUrl_sys).helpType().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<HelpTypeBean>>() {
                    @Override
                    public void onSuccess(List<HelpTypeBean> helpTypeBean) {
                        if(null != helpTypeBean && helpTypeBean.size()>0){
                            mAdapter.setList(helpTypeBean);
                        }
                    }
                });
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        List<HelpTypeBean> data = (List<HelpTypeBean>) adapter.getData();
        if(data!=null&&data.size()>0) {
            Intent intent = new Intent(HelpCenterActivity.this, HelpCenterItemActivity.class);
            intent.putExtra("typeId",data.get(position).getId());
            intent.putExtra("title",data.get(position).getTypeName());
            startActivity(intent);
        }

    }


    public static  class HelpCenterAdapter extends BaseQuickAdapter<HelpTypeBean, BaseViewHolder> implements LoadMoreModule {

        public HelpCenterAdapter() {
            super(R.layout.item_helpcentr);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, HelpTypeBean bean) {
            View line = helper.findView(R.id.line);

            TextView tv_text = helper.findView(R.id.tv_text);
            tv_text.setText(bean.getTypeName());

            if(0 == helper.getLayoutPosition()){
                line.setVisibility(View.GONE);
            }else{
                line.setVisibility(View.VISIBLE);
            }
        }


    }
}
