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

import com.bee.user.R;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.adapter.MineGridviewAdapter;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.giftcard.GiftcardActivity;
import com.bee.user.ui.home.NewsActivity;
import com.bee.user.ui.mine.coupon.CouponActivity;
import com.bee.user.ui.mine.membercenter.MemberCenterActivity;
import com.bee.user.ui.order.OrderActivity;
import com.bee.user.ui.trade.MiLiActivity;
import com.bee.user.ui.xiadan.ChooseAddressActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.MyGridView;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
                    startActivity(new Intent(getContext(),UserInfoActivity.class));
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
                getContext().startActivity(OrderActivity.newInstance(getContext(),0));
                break;
            case R.id.tv_daizhifu:
                getContext().startActivity(OrderActivity.newInstance(getContext(),1));
                break;

            case R.id.tv_daishouhuo:
                getContext().startActivity(OrderActivity.newInstance(getContext(),2));
                break;

            case R.id.tv_daipingjia:
                getContext().startActivity(OrderActivity.newInstance(getContext(),3));
                break;

            case R.id.tv_shouhou:
                getContext().startActivity(OrderActivity.newInstance(getContext(),4));
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
        }
    }


    @Override
    protected void getDatas() {

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

        if(SPUtils.geTinstance().isLogin()){
            tv_name.setText("1111");
            tv_des.setText("胡蜂会员");
        }else{
            tv_name.setText("立即登陆");
            tv_des.setText("省多少你说了算");
        }

        String str = ""+ CommonUtil.moneyType(10.00d);
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
                }
            }
        });
    }

    public void onLogin() {
        if(SPUtils.geTinstance().isLogin()){
            tv_name.setText("1111");
            tv_des.setText("胡蜂会员");
        }else{
            tv_name.setText("立即登陆");
            tv_des.setText("省多少你说了算");
        }

    }
}
