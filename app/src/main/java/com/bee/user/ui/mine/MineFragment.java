package com.bee.user.ui.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.MyMiLiBean;
import com.bee.user.bean.UserBean;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.MineGridviewAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.giftcard.GiftcardActivity;
import com.bee.user.ui.home.NewsActivity;
import com.bee.user.ui.mine.coupon.CouponActivity;
import com.bee.user.ui.mine.membercenter.MemberCenterActivity;
import com.bee.user.ui.order.OrderListActivity;
import com.bee.user.ui.trade.MiLiActivity;
import com.bee.user.ui.xiadan.ChooseAddressActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.MyGridView;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.Constants.REQUEST_CODE_USERINFO;
import static com.bee.user.Constants.RESULT_CODE_USERINFO;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/12  20：43
 * 描述：
 */
public class MineFragment extends BaseFragment {

    Unbinder bind;

    @BindView(R.id.status_bar1)
    View status_bar1;

    @BindView(R.id.tv_icon)
    ImageView tv_icon;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_des)
    TextView tv_des;


    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv_4)
    TextView tv_4;


    @BindView(R.id.gridview)
    MyGridView gridview;

    @OnClick({R.id.tv_icon,R.id.tv_name,R.id.tv_des,R.id.iv_msg,
            R.id.tv_order_list, R.id.tv_daizhifu, R.id.tv_daishouhuo, R.id.tv_daipingjia, R.id.tv_shouhou,
    R.id.tv_1,R.id.tv_2,R.id.tv_3,R.id.tv_4
            })
    public void onClick(View view){

        switch (view.getId()){
            case R.id.iv_msg:
                startActivity(new Intent(getContext(), NewsActivity.class));
                break;
            case R.id.tv_icon:
            case R.id.tv_name:
                if(SPUtils.geTinstance().isLogin()){
                    startActivityForResult(new Intent(getContext(),UserInfoActivity.class), REQUEST_CODE_USERINFO);
                }else{
                    EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_login));
                }
                break;
            case R.id.tv_des:
                if(SPUtils.geTinstance().isLogin()){
                   startActivity(new Intent(getContext(),MemberCenterActivity.class));
                }else{
                    EventBus.getDefault().post(new MainEvent(MainEvent.TYPE_login));
                }
                break;

            case R.id.tv_order_list:
                getContext().startActivity(OrderListActivity.newInstance(getContext(),0));
                break;
            case R.id.tv_daizhifu:
                getContext().startActivity(OrderListActivity.newInstance(getContext(),1));
                break;

            case R.id.tv_daishouhuo:
                getContext().startActivity(OrderListActivity.newInstance(getContext(),2));
                break;

            case R.id.tv_daipingjia:
                getContext().startActivity(OrderListActivity.newInstance(getContext(),4));
                break;

            case R.id.tv_shouhou:
                getContext().startActivity(OrderListActivity.newInstance(getContext(),5));
                break;

            case R.id.tv_1://米粒/充值
                getContext().startActivity(new Intent(getContext(), MiLiActivity.class));
                break;
            case R.id.tv_2://卡券包
                getContext().startActivity(new Intent(getContext(), CouponActivity.class));
                break;
            case R.id.tv_3://礼品券
                getContext().startActivity(new Intent(getContext(), GiftcardActivity.class));
                break;
            case R.id.tv_4://我的积分
                getContext().startActivity(new Intent(getContext(), MyPointsActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_USERINFO && resultCode == RESULT_CODE_USERINFO){
            Api.getClient(HttpRequest.baseUrl_member).getUserInfo()
                    .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<UserBean>() {
                        @Override
                        public void onSuccess(UserBean str) {
                            SPUtils.geTinstance().setLoginCache(str);
                            setUserDatas();
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                        }
                    });
        }
    }

    @Override
    protected void getDatas() {
        if(SPUtils.geTinstance().isLogin()){
            getMiLiDatas();
        }
    }



    @Override public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this,view);


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CommonUtil.initBuyCardView(view);
        initViews();
    }

    private void initViews() {

        ViewGroup.LayoutParams layoutParams = status_bar1.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);

        String str = ""+ CommonUtil.moneyType(0d);
        SpannableString msp = new SpannableString(str + "\n米粒/充值");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_1.setText(msp);

        str = "0";
        msp = new SpannableString(str + "\n卡包券");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_2.setText(msp);

        str = "0";
        msp = new SpannableString(str + "\n礼品卡");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_3.setText(msp);

        str = "0";
        msp = new SpannableString(str + "\n我的积分");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_4.setText(msp);




        gridview.setAdapter(new MineGridviewAdapter(getContext()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent ;
                switch (i){
                    case 0:
                        intent = new Intent(getContext(),SettingActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getContext(), ChooseAddressActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getContext(),BillActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        intent = new Intent(getContext(),CompanyOrderingActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(getContext(),CompanyCooperateActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(getContext(),MyCommentActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        break;
                    case 9:
                        intent = new Intent(getContext(),HelpCenterActivity.class);
                        startActivity(intent);
                        break;
                    case 10:


                        break;
                    case 11:
                        intent = new Intent(getContext(),FeedbackActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
        setUserDatas();
    }

    public void setUserDatas() {
        if(SPUtils.geTinstance().isLogin() && null != SPUtils.geTinstance().getUserInfo()){
            UserBean userInfo = SPUtils.geTinstance().getUserInfo();
            tv_name.setText(userInfo.nickname);
            tv_des.setText(userInfo.memberLevelName+"");
            Picasso.with(getContext())
                    .load(userInfo.icon)
                    .fit()
                    .transform(new PicassoRoundTransform(DisplayUtil.dip2px(getContext(),100),0, PicassoRoundTransform.CornerType.ALL))
                    .into(tv_icon);
        }else{
            tv_name.setText("立即登陆");
            tv_des.setText("省多少你说了算");
            Picasso.with(getContext())
                    .load(R.drawable.icon_touxiang)
                    .fit()
                    .into(tv_icon);
        }

    }

    MyMiLiBean miLiBean;
    public void getMiLiDatas() {
        Api.getClient(HttpRequest.baseUrl_pay).getMemberRice().
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<MyMiLiBean>() {
                    @Override
                    public void onSuccess(MyMiLiBean s) {
                        miLiBean = s;
                        String str = ""+ s.surplusAmount;
                        SpannableString msp = new SpannableString(str + "\n米粒/充值");
                        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        tv_1.setText(msp);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }
}
