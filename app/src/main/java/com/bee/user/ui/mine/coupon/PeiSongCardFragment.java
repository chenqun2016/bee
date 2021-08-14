package com.bee.user.ui.mine.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.ui.mine.BuyCardActivity;
import com.bee.user.utils.CommonUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2021/8/7
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class PeiSongCardFragment extends BaseFragment {
    @BindView(R.id.tv_des1)
    TextView tv_des1;
    @BindView(R.id.tv_des2)
    TextView tv_des2;
    @BindView(R.id.tv_des3)
    TextView tv_des3;
    @BindView(R.id.tv_des4)
    TextView tv_des4;


    @Override
    protected void getDatas() {

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCardDatas();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peisong_card, container, false);
        CommonUtil.initBuyCardView(view);
        return view;
    }

    private void getCardDatas() {
        Api.getClient(HttpRequest.baseUrl_pay).distributionCard()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<PeiSongCardBean>() {
                    @Override
                    public void onSuccess(PeiSongCardBean bean) {
                        if(null == bean){
                            tv_des1.setText("配送卡未开通");
                            tv_des2.setText("开通配送卡，省钱以后花");
                            tv_des3.setText("");
                            tv_des3.setVisibility(View.GONE);
                            tv_des4.setText("我要开通");

                        }else if(bean.status == 0){
                            tv_des1.setText(bean.cardName);
                            tv_des2.setText(bean.expireTimeDesc);
                            tv_des3.setText(bean.remainDays);
                            tv_des3.setVisibility(View.VISIBLE);
                            tv_des4.setText("我要续费");

                        }else{
                            //已过期
                            tv_des1.setText(bean.cardName);
                            tv_des2.setText(bean.expireTimeDesc);
                            tv_des3.setText(bean.remainDays);
                            tv_des3.setVisibility(View.VISIBLE);
                            tv_des4.setText("我要开通");
                        }

                        tv_des4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getContext(), BuyCardActivity.class));
                            }
                        });
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

}
