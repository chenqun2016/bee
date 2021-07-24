package com.bee.user.ui.giftcard;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bee.user.R;
import com.bee.user.bean.GiftcardBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.LoadmoreUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
 * 创建时间：2020/11/04  20：03
 * 描述：
 */
public class GiftcardActivity extends BaseActivity {
    @BindView(R.id.reflushlayout)
    SwipeRefreshLayout reflushlayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    MyAdapter myAdapter;
    LoadmoreUtils loadmoreUtils;


    @OnClick({R.id.tv_right,R.id.tv_1,R.id.tv_2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this, GiftcardRecordActivity.class));
                break;
            case R.id.tv_1:
                startActivity(new Intent(this, BuyGiftcardActivity.class));
                break;
            case R.id.tv_2:
                startActivity(new Intent(this, ZengsongGiftcardActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_giftcard;
    }

    @Override
    public void initViews() {
        View empty = View.inflate(this, R.layout.empty_giftcard_list, null);

        TextView tv_guangguang  = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GiftcardActivity.this, MainActivity.class));
            }
        });

        myAdapter = new MyAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(myAdapter);
        loadmoreUtils = new LoadmoreUtils(){
            @Override
            protected void getDatas(int page) {
                super.getDatas(page);
                getNetDatas(page);
            }
        };
        loadmoreUtils.initLoadmore(myAdapter,reflushlayout);
        loadmoreUtils.setEmptyView(empty);

        loadmoreUtils.refresh(myAdapter);
    }

    private void getNetDatas(int page) {
        Api.getClient(HttpRequest.baseUrl_pay).giftCard().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<GiftcardBean>>() {
                    @Override
                    public void onSuccess(List<GiftcardBean> beans) {
                        loadmoreUtils.onSuccess(myAdapter, beans);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        loadmoreUtils.onFail(myAdapter, fail);
                    }
                });
    }

    public class MyAdapter extends BaseQuickAdapter<GiftcardBean, BaseViewHolder>  implements LoadMoreModule {

        public MyAdapter() {
            super(R.layout.item_giftcard);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder helper, GiftcardBean giftcardBean) {

            int position = helper.getAbsoluteAdapterPosition();
            View rl_content = helper.getView(R.id.rl_content);
            if(0 == position){
                rl_content.setBackgroundResource(R.drawable.quan_100);
            }else if(1 == position){
                rl_content.setBackgroundResource(R.drawable.quan_300);
            }
            else if(2 == position){
                rl_content.setBackgroundResource(R.drawable.quan_500);
            }
            else if(3 == position){
                rl_content.setBackgroundResource(R.drawable.quan_1000);
            }else{
                rl_content.setBackgroundResource(R.drawable.quan_100);
            }
        }
    }
}
