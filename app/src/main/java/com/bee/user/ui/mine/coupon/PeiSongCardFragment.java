package com.bee.user.ui.mine.coupon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.bean.PeiSongCardBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.utils.CommonUtil;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2021/8/7
 *
 * @Author： 陈陈陈
 * 功能描述：
 */
public class PeiSongCardFragment extends BaseFragment {
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
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }

}
