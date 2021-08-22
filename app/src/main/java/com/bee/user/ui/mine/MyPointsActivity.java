package com.bee.user.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bee.user.R;
import com.bee.user.bean.PointDayBean;
import com.bee.user.bean.RenWuBean;
import com.bee.user.bean.SignInMessageBean;
import com.bee.user.bean.UserPointsBean;
import com.bee.user.bean.UserSigninBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.ToastUtil;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.gyf.immersionbar.ImmersionBar;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/14  15：59
 * 描述：
 */
public class MyPointsActivity extends BaseActivity {
    @BindView(R.id.statusheight)
    View statusheight;

    @BindView(R.id.recyclerview_qiandao)
    RecyclerView recyclerview_qiandao;


    @BindView(R.id.recyclerview_renwu)
    RecyclerView recyclerview_renwu;

    @BindView(R.id.tv_qiandao)
    TextView tv_qiandao;

    @BindView(R.id.tv_mili)
    TextView tv_mili;
    @BindView(R.id.tv_lianxuqiandao)
    TextView tv_lianxuqiandao;

    private PointsAdapter pointsAdapter;

    @OnClick({R.id.tv_qiandao,R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_qiandao:
                toSign();
                break;
            case R.id.tv_right:
                startActivity(new Intent(this,PointDetailListActivity.class));
                break;
        }

    }

    /**
     * 去签到
     */
    private void toSign() {
        Api.getClient(HttpRequest.baseUrl_activity).userSignIn().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserSigninBean>() {
                    @Override
                    public void onSuccess(UserSigninBean userSigninBean) {
                        showQiandaoDialog(userSigninBean);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        ToastUtil.showSafeToast(MyPointsActivity.this,fail);
                    }
                });
    }

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_mypoints;
    }

    @Override
    public void initViews() {
        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        recyclerview_qiandao.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        pointsAdapter = new PointsAdapter();
        recyclerview_qiandao.setAdapter(pointsAdapter);


        recyclerview_renwu.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        RenWuAdapter renWuAdapter = new RenWuAdapter();
        recyclerview_renwu.setAdapter(renWuAdapter);

        ArrayList<RenWuBean> renwuBeans = new ArrayList<>();
        renwuBeans.add(new RenWuBean(R.drawable.icon_qiandao_jifen,"累计签到3天","连续签到3天额外获得10积分"));
        renwuBeans.add(new RenWuBean(R.drawable.icon_qiandao_jifen,"累计签到7天","连续签到7天额外获得30积分"));
        renwuBeans.add(new RenWuBean(R.drawable.icon_wanchengdingdan,"完成2单外卖订单","在线点单外卖配送2单"));
        renWuAdapter.setNewInstance(renwuBeans);
        toUserPoints();
        toSignInMessage();
    }

    /**
     * 获取活动信息
     */
    private void toSignInMessage() {
        Api.getClient(HttpRequest.baseUrl_activity).getSignInMessage().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<SignInMessageBean>() {
                    @Override
                    public void onSuccess(SignInMessageBean signInMessageBean) {
                        int isNowSignIn = signInMessageBean.getIsNowSignIn();//今天是否签到 0：否 1：是
                        int moreSignIns = signInMessageBean.getMoreSignIns();//连续签到多少天
                        List<PointDayBean> signs = signInMessageBean.getSigns();
                        if(!ObjectUtils.isEmpty(signs)) {
                            pointsAdapter.setNewInstance(signs);
                        }
                        tv_lianxuqiandao.setText(String.format(getString(R.string.tv_lianxuqiandao),moreSignIns+""));
                        setButtonStatus(isNowSignIn);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

    /**
     *获取会员积分
     *
     */
    private void toUserPoints() {
        Api.getClient(HttpRequest.baseUrl_activity).getUserPoints().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserPointsBean>() {
                    @Override
                    public void onSuccess(UserPointsBean userPointsBean) {
                        tv_mili.setText(userPointsBean.getNoUsePoints()+"");
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                        tv_mili.setText("0");
                    }
                });
    }


    public static class PointsAdapter extends BaseQuickAdapter<PointDayBean, BaseViewHolder> {

        public PointsAdapter() {
            super(R.layout.item_point_day);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, PointDayBean bean) {
            ImageView tv_image = baseViewHolder.getView(R.id.tv_image);

            TextView tv_day = baseViewHolder.getView(R.id.tv_day);
            TextView tv_point = baseViewHolder.getView(R.id.tv_point);
            tv_day.setText(bean.dayNum+ "天");
            tv_point.setText(bean.points+"分");

            View content = baseViewHolder.findView(R.id.content);
            if(bean.isSignIn == 1){
                tv_image.setImageResource(R.drawable.icon_wutouying);
                tv_day.setTextColor(tv_day.getResources().getColor(R.color.white));
                tv_point.setTextColor(tv_point.getResources().getColor(R.color.white));
                content.setBackgroundResource(R.drawable.btn_gradient_yellow_round5dp);
            }else{
                tv_image.setImageResource(R.drawable.icon_youtouying);

                tv_day.setTextColor(tv_day.getResources().getColor(R.color.color_7C7877));
                tv_point.setTextColor(tv_point.getResources().getColor(R.color.color_7C7877));
                content.setBackgroundResource(R.drawable.bg_round10dp_grey);

            }
        }
    }


    public static class RenWuAdapter extends BaseQuickAdapter<RenWuBean, BaseViewHolder> {

        public RenWuAdapter() {
            super(R.layout.item_renwu);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, RenWuBean bean) {
            ImageView icon_type = baseViewHolder.getView(R.id.icon_type);
            TextView tv_integral_title = baseViewHolder.getView(R.id.tv_integral_title);
            TextView tv_integral_date = baseViewHolder.getView(R.id.tv_integral_date);
            TextView tv_integral_count = baseViewHolder.getView(R.id.tv_integral_count);

            icon_type.setImageResource(bean.resID);
            tv_integral_title.setText(bean.title);
            tv_integral_date.setText(bean.des);




            View view_line = baseViewHolder.getView(R.id.view_line);

            if(baseViewHolder.getLayoutPosition() == 0){
                view_line.setVisibility(View.INVISIBLE);
            }else{
                view_line.setVisibility(View.VISIBLE);
            }
        }
    }


    //    取消订单dialog
    private  void showQiandaoDialog(UserSigninBean userSigninBean) {
        Dialog dialog = new Dialog(this, R.style.loadingDialogTheme);

        View inflate = View.inflate(this, R.layout.dialog_qiandao, null);
        inflate.findViewById(R.id.btn_get_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != dialog && dialog.isShowing()) {
                    toUserPoints();
                    toSignInMessage();
                    dialog.dismiss();
                }
            }
        });
        TextView tv_status = inflate.findViewById(R.id.tv_status);
        TextView tv_get_integer = inflate.findViewById(R.id.tv_get_integer);
        String str = String.format(getString(R.string.tv_sign_inform),userSigninBean.getRunningDays()+"");
        String str2 = userSigninBean.getPoints()+"";
        String str3 = "积分";
        SpannableString msp = new SpannableString(str + str2 + str3);
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), str.length(), str.length()+str2.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_get_integer.setText(msp);

        dialog.setContentView(inflate);
        dialog.show();
    }

    private void setButtonStatus(int isNowSignIn) {
        if (isNowSignIn == 0) {
            tv_qiandao.setEnabled(true);
            tv_qiandao.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
            tv_qiandao.setText("立即签到");
        } else {
            tv_qiandao.setEnabled(false);
            tv_qiandao.setBackgroundResource(R.drawable.btn_gradient_grey_round);
            tv_qiandao.setText("已签到");
        }
    }
}
