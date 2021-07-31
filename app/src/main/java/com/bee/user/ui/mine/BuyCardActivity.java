package com.bee.user.ui.mine;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.bean.UserBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.BuyCardAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.PayUtils;
import com.bee.user.utils.sputils.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/20  19：31
 * 描述：
 */
public class BuyCardActivity extends BaseActivity {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;



    @BindView(R.id.checkbox_zhifubao)
    ImageView checkbox_zhifubao;

    @BindView(R.id.checkbox_weixin)
    ImageView checkbox_weixin;



    @BindView(R.id.tv_sure)
    TextView tv_sure;

    BuyCardAdapter adapter;


    private int payType = -1;
    @OnClick({R.id.ll_zhifubao,R.id.ll_weixin})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_zhifubao:
                payType = 0;
                break;
            case R.id.ll_weixin:
                payType = 1;
                break;
        }

        if(payType == 0){
            checkbox_zhifubao.setImageResource(R.drawable.icon_xuanzhong_gouwuche);
            checkbox_weixin.setImageResource(R.drawable.checkbutton_chart);
        }else if(payType == 1){
            checkbox_zhifubao.setImageResource(R.drawable.checkbutton_chart);
            checkbox_weixin.setImageResource(R.drawable.icon_xuanzhong_gouwuche);
        }


        if(adapter.current != -1){
            setButtonStatus(true);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_card;
    }


    private static final Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {

                    break;
                }

                default:
                    break;
            }
        };
    };

    @Override
    public void initViews() {
        UserBean userInfo = SPUtils.geTinstance().getUserInfo();
        Picasso.with(this)
                .load(userInfo.icon)
                .fit()
                .transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,100),0, PicassoRoundTransform.CornerType.ALL))
                .into(iv_icon);
        tv_title.setText(userInfo.nickname);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> map = new HashMap<>();
                PeiSongCardBean peiSongCardBean = adapter.getData().get(adapter.current);
                if(null != peiSongCardBean){
                    map.put("bizId", peiSongCardBean.id+"");
                }
                map.put("bizType", "2");
                map.put("deviceType", "安卓");

                String payChannel = payType==0?"ALIPAY":"WECHATPAY";
                map.put("payChannel", payChannel);

                PayUtils.toPay(BuyCardActivity.this,map,mHandler);
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        adapter = new BuyCardAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> a, @NonNull View view, int position) {
                if( adapter.current  != position){
                    adapter.current = position;
                    adapter.notifyDataSetChanged();
                }

                if(payType != -1){
                    setButtonStatus(true);
                }
            }
        });
        recyclerview.setAdapter(adapter);

//        ArrayList<PeiSongCardBean> beans = new ArrayList<>();
//        beans.add(new PeiSongCardBean(0,"连续年度卡","89","15.50元/月","年"));
//        beans.add(new PeiSongCardBean(0,"年度卡","99","15.50元/月","年"));
//        beans.add(new PeiSongCardBean(1,"连续季度卡","25.9","15.50元/月","季"));
//        beans.add(new PeiSongCardBean(1,"季度卡","25.9","15.50元/月","季"));
//        beans.add(new PeiSongCardBean(2,"连续月度卡","8.9","15.50元/月","月"));
//        beans.add(new PeiSongCardBean(2,"月度卡","8.9","15.50元/月","月"));
//        adapter.setNewInstance(beans);

        getDatas();
    }

    private void getDatas() {
        Api.getClient(HttpRequest.baseUrl_pay).distributionCardOnSale()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<PeiSongCardBean>>() {
                    @Override
                    public void onSuccess(List<PeiSongCardBean> bean) {
                        adapter.setNewInstance(bean);
                    }

                });
    }


    private void setButtonStatus(Boolean aBoolean) {
        if (aBoolean) {
            tv_sure.setEnabled(true);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
        } else {
            tv_sure.setEnabled(false);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_grey_round);
        }
    }
}
