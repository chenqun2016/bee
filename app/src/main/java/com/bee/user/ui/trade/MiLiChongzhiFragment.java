package com.bee.user.ui.trade;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.bee.user.R;
import com.bee.user.bean.MiLiChongzhiBean;
import com.bee.user.bean.OrderInfo;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.MiLiChongzhiAdapter;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.fragment.BaseFragment;
import com.bee.user.widget.MyGridView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/15  21：04
 * 描述：
 */
public class MiLiChongzhiFragment extends BaseFragment implements View.OnClickListener {

    MiLiChongzhiAdapter miLiChongzhiAdapter;
    MyGridView gridview;

    TextView tv_sure;
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
    protected void getDatas() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_milichongzhi,container,false);
        gridview  = view.findViewById(R.id.gridview);
        tv_sure  = view.findViewById(R.id.tv_sure);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPayDialog();
//                startActivity(new Intent(getContext(),TradeStatusActivity.class));
            }
        });
        setButtonStatus(false,tv_sure);
        ArrayList<MiLiChongzhiBean> miLiChongzhiBeans = new ArrayList<>();
        miLiChongzhiBeans.add(new MiLiChongzhiBean(50,"50米粒","送10米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(100,"100米粒","送20米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(200,"200米粒","送40米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(300,"300米粒","送50米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(500,"500米粒","送100米粒"));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(1000,"1000米粒","送300米粒"));
        miLiChongzhiAdapter   = new MiLiChongzhiAdapter(getContext(), miLiChongzhiBeans);
        gridview.setAdapter(miLiChongzhiAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setButtonStatus(true,tv_sure);
                miLiChongzhiAdapter.setSelected(i);
                miLiChongzhiAdapter.notifyDataSetChanged();
            }
        });

    }

    ImageView checkbox_zhifubao;
    ImageView checkbox_weixin;
    TextView tv_dialog_sure;
    BaseDialog bottomSheetDialog;
    private void showPayDialog() {
        if(null == bottomSheetDialog){
            bottomSheetDialog  = new BaseDialog(getActivity()) {
                @Override
                protected int provideContentViewId() {
                    return R.layout.dialog_pay_type;
                }

                @Override
                protected void initViews(BaseDialog dialog) {
                    dialog.findViewById(R.id.tv_close).setOnClickListener(MiLiChongzhiFragment.this::onClick);
                    dialog.findViewById(R.id.ll_zhifubao).setOnClickListener(MiLiChongzhiFragment.this::onClick);
                    dialog.findViewById(R.id.ll_weixin).setOnClickListener(MiLiChongzhiFragment.this::onClick);
                    checkbox_zhifubao = dialog.findViewById(R.id.checkbox_zhifubao);
                    checkbox_weixin = dialog.findViewById(R.id.checkbox_weixin);
                    tv_dialog_sure = dialog.findViewById(R.id.tv_dialog_sure);
                    tv_dialog_sure.setOnClickListener(MiLiChongzhiFragment.this::onClick);
                }
            };
        }

        bottomSheetDialog.show();
    }
    private int payType = -1;
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_zhifubao:
                payType = 0;
                break;
            case R.id.ll_weixin:
                payType = 1;
                break;
            case R.id.tv_close:
               if(null != bottomSheetDialog && bottomSheetDialog.isShowing()){
                   bottomSheetDialog.dismiss();
               }
                break;
            case R.id.tv_dialog_sure:
                toPay();
                break;
        }

        if(payType == 0){
            checkbox_zhifubao.setImageResource(R.drawable.icon_xuanzhong_gouwuche);
            checkbox_weixin.setImageResource(R.drawable.checkbutton_chart);
        }else if(payType == 1){
            checkbox_zhifubao.setImageResource(R.drawable.checkbutton_chart);
            checkbox_weixin.setImageResource(R.drawable.icon_xuanzhong_gouwuche);
        }

        setButtonStatus(true,tv_dialog_sure);
    }

    private void toPay() {
        Map<String, String> map = new HashMap<>();
        map.put("bizId", "1");
        map.put("bizType", "1");
        map.put("cardType", "a");
        map.put("deviceType", "安卓");

        String payChannel = payType==0?"ALIPAY":"WECHATPAY";
        map.put("payChannel", payChannel);

        Api.getClient(HttpRequest.baseUrl_pay).zhifubao_pay(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String r) {
                        String orderInfo = new Gson().toJson( new OrderInfo());   // 订单信息

                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(getActivity());
                                Map<String,String> result = alipay.payV2(orderInfo,true);

                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });


    }


    private void setButtonStatus(Boolean aBoolean,TextView textView) {
        if (aBoolean) {
            textView.setEnabled(true);
            textView.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
        } else {
            textView.setEnabled(false);
            textView.setBackgroundResource(R.drawable.btn_gradient_grey_round);
        }
    }
}
