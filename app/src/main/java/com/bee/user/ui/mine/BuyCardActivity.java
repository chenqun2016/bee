package com.bee.user.ui.mine;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.bean.UserBean;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.ReflushEvent;
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
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.utils.PayUtils.PAY_TYPE_PEISONG_CARD;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/20  19：31
 * 描述：
 */
public class BuyCardActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;

    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_mili_keyong)
    TextView tv_mili_keyong;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;



    @BindView(R.id.tv_sure)
    TextView tv_sure;

    BuyCardAdapter adapter;
    private MyMiLiBean mMiliBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_card;
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

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
                    map.put("cardId", peiSongCardBean.id+"");
                }
                //卡类型：LGST-配送卡；GC-礼品卡
                map.put("cardType", "LGST");
                List<PeiSongCardBean> data = adapter.getData();
                PayUtils.pay(PAY_TYPE_PEISONG_CARD,mMiliBean,data.get(adapter.current).faceValue,map,BuyCardActivity.this);
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
                    List<PeiSongCardBean> data = adapter.getData();
                    setButtonStatus(true,data.get(position).faceValue+"");
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
        getMiLiDatas();
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

    public void getMiLiDatas() {
        Api.getClient(HttpRequest.baseUrl_pay).getMemberRice().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MyMiLiBean>() {
                    @Override
                    public void onSuccess(MyMiLiBean s) {
                        mMiliBean = s;
                        tv_mili_keyong.setText("可用米粒 "+s.surplusAmount);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }



    private void setButtonStatus(Boolean aBoolean,String money) {
        if (aBoolean) {
            tv_sure.setEnabled(true);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
            tv_sure.setText("确认支付¥"+money);
        } else {
            tv_sure.setEnabled(false);
            tv_sure.setBackgroundResource(R.drawable.btn_gradient_grey_round);
            tv_sure.setText("确认支付");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if (event.type == CloseEvent.TYPE_PAY) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflushEvent(ReflushEvent event) {
        if (ReflushEvent.TYPE_REFLUSH_MILI == event.type) {
            getMiLiDatas();
        }
    }
}
