package com.bee.user.ui.xiadan;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.ChooseTimeBean;
import com.bee.user.bean.OrderingBean;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.OrderingEvent;
import com.bee.user.params.OrderPreParams;
import com.bee.user.params.OrderingParams;
import com.bee.user.params.PayParams;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.ChooseTimeAdapter;
import com.bee.user.ui.adapter.OrderingAdapter;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.bee.user.widget.AddRemoveView;
import com.bee.user.widget.RadioGroupPlus;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.Constants.REQUEST_CODE_ORDERING;
import static com.bee.user.Constants.RESULT_CODE_ORDERING;
import static com.bee.user.Constants.TEXT_BEIZHU;
import static com.bee.user.ui.xiadan.ChooseAddressActivity.REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/04  21：13
 * 描述：
 */
public class OrderingActivity extends BaseActivity {

    @BindView(R.id.statusheight)
    View statusheight;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.tv_heji_money)
    TextView tv_heji_money;
    @BindView(R.id.tv_youhui_value)
    TextView tv_youhui_value;

    private int operationType;
    TextView tv_tigongcanju;

    private boolean isSingle = true;

    //headview
    private TextView tv_dizhi;
    private TextView tv_dizhi2;
    private TextView tv_time;
    private TextView tv_time_value;
    private TextView tv_paytype;
    private TextView tv_paytype_keyong;

    ArrayList<Integer> cartIds1 ;

//    public String canju = "";

    int totalMoney;

    private Map<String, ChooseTimeBean> timeBeanHashMaps = new HashMap<>();
    private AddressBean mAddress;

    OrderingAdapter orderingAdapter;
    ArrayList<Integer> storeIds;

    @OnClick({R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if(null == mAddress){
                    showCommonDialog("请添加收货人地址", null, "立即添加", new DialogClickListener() {
                        @Override
                        public void onDialogCancle() {
                        }

                        @Override
                        public void onDialogSure() {
                            toChooseAddressActivity();
                        }
                    });
                    return;
                }
                doSubmit();
                break;
        }
    }

    public static Intent newIntent(Context context, int operationType,ArrayList<Integer> ids,ArrayList<Integer> storeIds) {
        Intent intent = new Intent(context, OrderingActivity.class);
        intent.putExtra("operationType",operationType);
        intent.putIntegerArrayListExtra("ids",ids);
        intent.putIntegerArrayListExtra("storeIds",storeIds);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING && resultCode == 1) {

            mAddress = (AddressBean) data.getSerializableExtra("address");
            tv_dizhi.setText(mAddress.detailAddress+mAddress.houseNumber);
            tv_dizhi2.setVisibility(View.VISIBLE);
            String gender = mAddress.gender==1?"先生":"女士";
            tv_dizhi2.setText(mAddress.name+"("+gender+")"+mAddress.phoneNumber);
        }
        if(requestCode == REQUEST_CODE_ORDERING &&  resultCode == RESULT_CODE_ORDERING){
            String beizhu = data.getStringExtra(TEXT_BEIZHU);
            tv_beizhu.setText(beizhu);
        }
        if(requestCode == 55 && resultCode == 55){
            int index = data.getIntExtra("index",-1);
            selectedCouponBean.selectedCoupon = index;
        }
    }



    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_ordering;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    OrderingConfirmBean.StoreOrderConfirmItemsBean  selectedCouponBean;
    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        toolbar_title.setText("确认订单");
        operationType = getIntent().getIntExtra("operationType",1);
        cartIds1 = getIntent().getIntegerArrayListExtra("ids");
        storeIds   = getIntent().getIntegerArrayListExtra("storeIds");

        isSingle = null == storeIds || storeIds.size() <= 1;



        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        orderingAdapter  = new OrderingAdapter(isSingle);
        orderingAdapter.addChildClickViewIds(R.id.tv_youhuiquan_value);
        orderingAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                if(view.getId() == R.id.tv_youhuiquan_value){
                    selectedCouponBean = (OrderingConfirmBean.StoreOrderConfirmItemsBean) adapter.getData().get(position);
                    if(null != selectedCouponBean.couponList && selectedCouponBean.couponList.size()>0){
                        Intent intent = new Intent(OrderingActivity.this, YouhuiquanActivity.class);
                        intent.putExtra("datas", (Serializable) selectedCouponBean.couponList);
                        startActivityForResult(intent,55);
                    }
                }
            }
        });
        recyclerView.setAdapter(orderingAdapter);

        View head = View.inflate(this, R.layout.head_ordering, null);
        View foot = View.inflate(this, R.layout.foot_ordering, null);
        orderingAdapter.addHeaderView(head);
        orderingAdapter.addFooterView(foot);
        initHeadFootView(head, foot);

        getDatas();
    }


    private void setDatas(OrderingConfirmBean userBean) {
        totalMoney = 0;
        orderingAdapter.setNewInstance(userBean.getStoreOrderConfirmItems());

        for(OrderingConfirmBean.StoreOrderConfirmItemsBean bean : userBean.getStoreOrderConfirmItems()){
            totalMoney += bean.getCalcAmount().getPayAmount();
        }
        tv_heji_money.setText("¥"+totalMoney);
    }

    private void getDatas() {
        String token = SPUtils.geTinstance().getToken();
        Api.getClient(HttpRequest.baseUrl_member).listAddress().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<AddressBean>>() {
                    @Override
                    public void onSuccess(List<AddressBean> addressBean2) {
                        if(null != addressBean2 && addressBean2.size()>0){
                            mAddress = addressBean2.get(0);
                            setAddressView();

                        }
                    }
                });

        doSubmitPre();
        //获取默认收货地址
//        Api.getClient(HttpRequest.baseUrl_member).getDefaultArea().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseSubscriber<AddressBean2>() {
//                    @Override
//                    public void onSuccess(AddressBean2 addressBean2) {
//                        mAddress = addressBean2;
//                        doSubmitPre();
//                    }
//                });
        if(null == storeIds){
            return;
        }
        for(Integer storeId : storeIds){
            Api.getClient(HttpRequest.baseUrl_goods).caculateTime(storeId+"").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ChooseTimeBean>() {
                        @Override
                        public void onSuccess(ChooseTimeBean bean) {
                            List<ChooseTimeBean.ChooseTimeItemBean> today = bean.today;
                            if (today != null && today.size() > 0) {
                                today.get(0).choosed = true;
                                bean.pre = 0;
                            } else {
                                List<ChooseTimeBean.ChooseTimeItemBean> tomorrow = bean.tomorrow;
                                tomorrow.get(0).choosed = true;
                                bean.pre2 = 0;
                            }
                            timeBeanHashMaps.put(storeId+"", bean);
                        }

                        @Override
                        public void onFail(String fail) {
                            super.onFail(fail);
                        }
                    });
        }


    }
    private TextView tv_beizhu;

    private void initHeadFootView(View head, View foot) {
        CommonUtil.initBuyCardView(foot);

        tv_time = head.findViewById(R.id.tv_time);
        tv_time_value = head.findViewById(R.id.tv_time_value);
        tv_paytype = head.findViewById(R.id.tv_paytype);
        tv_paytype_keyong = head.findViewById(R.id.tv_paytype_keyong);
        if (isSingle) {
            tv_time.setVisibility(View.VISIBLE);
            tv_time_value.setVisibility(View.VISIBLE);
        } else {
            tv_time.setVisibility(View.GONE);
            tv_time_value.setVisibility(View.GONE);
        }
        tv_time_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseTimeDialog(storeIds.get(0)+"");
            }
        });


        tv_dizhi = head.findViewById(R.id.tv_dizhi);
        tv_dizhi2 = head.findViewById(R.id.tv_dizhi2);
        tv_dizhi2.setVisibility(View.GONE);
        tv_dizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toChooseAddressActivity();
            }
        });


//        ImageView imageview = foot.findViewById(R.id.imageview);
//        Picasso.with(this).
//                load(R.drawable.banner).
//                fit().
//                transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,10),0, PicassoRoundTransform.CornerType.ALL)).
//                into(imageview);

        tv_beizhu = foot.findViewById(R.id.tv_beizhu);
        tv_beizhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderingActivity.this, BeizhuActivity.class);
                intent.putExtra("text",tv_beizhu.getText().toString());
                startActivityForResult(intent, REQUEST_CODE_ORDERING);
            }
        });
        tv_tigongcanju = foot.findViewById(R.id.tv_tigongcanju);
        tv_tigongcanju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showChooseCanjuDialog();
            }
        });
    }



    //    选择餐具
    private void showChooseCanjuDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_choose_canju);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        TextView tv_pay = bottomSheetDialog.findViewById(R.id.tv_pay);
        tv_pay.setEnabled(false);
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                doSubmit();



            }
        });

        LinearLayout ll_xuyao = bottomSheetDialog.findViewById(R.id.ll_xuyao);
        AddRemoveView iv_goods_add = bottomSheetDialog.findViewById(R.id.iv_goods_add);
        TextView tv_buxuyao = bottomSheetDialog.findViewById(R.id.tv_buxuyao);

        TextView tv_aoto = bottomSheetDialog.findViewById(R.id.tv_aoto);

        iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
            @Override
            public boolean onAddListener(int num) {
                if (0 == num) {
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_grey_round);
                    tv_pay.setEnabled(false);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);

                    tv_tigongcanju.setText("");
                } else {
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                    tv_pay.setEnabled(true);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);
                    tv_buxuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);
                    tv_aoto.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);

                    tv_tigongcanju.setText(num + "双");
                }
                return  true;
            }

            @Override
            public boolean onRemoveListener(int num) {
                if (0 == num) {
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_grey_round);
                    tv_pay.setEnabled(false);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);

                    tv_tigongcanju.setText("");
                }
                return true;
            }

        });
        tv_buxuyao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_pay.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                tv_pay.setEnabled(true);
                iv_goods_add.setNum(0);

                ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);
                tv_buxuyao.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);
                tv_aoto.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);

                tv_tigongcanju.setText("无需餐具");
            }
        });

        tv_aoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_pay.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                tv_pay.setEnabled(true);
                iv_goods_add.setNum(0);

                ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);
                tv_buxuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);
                tv_aoto.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);

                tv_tigongcanju.setText("依据餐量提供");
            }
        });


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bottomSheetDialog.show();
    }

    private void doSubmitPre() {

//        if(null == mAddress){
//            return;
//        }

        OrderPreParams orderingParams = new OrderPreParams();
//        orderingParams.memberId = mAddress.getMemberId();
        orderingParams.offline = 1;
        orderingParams.orderType = 1;
        orderingParams.source = 5;

//        orderingParams.addressId = mAddress.getId();
        orderingParams.cartIds = cartIds1.toArray(new Integer[]{});

        Api.getClient(HttpRequest.baseUrl_order).submitPreview(Api.getRequestBody(orderingParams)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderingConfirmBean>() {
                    @Override
                    public void onSuccess(OrderingConfirmBean userBean) {
                        setDatas(userBean);
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });

    }


    //TODO 下单
    private void doSubmit() {
        OrderingParams orderingParams = new OrderingParams();
        orderingParams.addressId = mAddress.id;

        //设置优惠券
        List<OrderingParams.CouponInfo> shopCoupons = new ArrayList();
        List<OrderingConfirmBean.StoreOrderConfirmItemsBean> data1 = orderingAdapter.getData();
        for(int i=0;i<data1.size();i++){
            OrderingConfirmBean.StoreOrderConfirmItemsBean bean = data1.get(i);
            if(bean.selectedCoupon != -1){
                shopCoupons.add(new OrderingParams.CouponInfo(bean.selectedCoupon,bean.getStoreId()));
            }
        }

        int feightTemplateDetailId =0 ;
        List<OrderingParams.FeightTemplateModel> feightTemplateModels = new ArrayList<>();
        for(int i=0;i<storeIds.size();i++){
            List<OrderingConfirmBean.StoreOrderConfirmItemsBean> data = orderingAdapter.getData();
            OrderingConfirmBean.StoreOrderConfirmItemsBean bean = data.get(i);
            if(null != bean){
                feightTemplateDetailId = bean.feightTemplateDetailId;
            }
            feightTemplateModels.add(new OrderingParams.FeightTemplateModel(feightTemplateDetailId,storeIds.get(i)));
        }
        orderingParams.feightTemplateModels = feightTemplateModels;
        orderingParams.note = tv_beizhu.getText().toString();
        orderingParams.operationType = operationType+"";
        orderingParams.cartItemIds = cartIds1.toArray(new Integer[]{});
        orderingParams.orderType = 1;
        orderingParams.payType = 1;
        orderingParams.pickupWay = 1;
        orderingParams.sourceType = 5;
        orderingParams.shopCoupons = shopCoupons;

        showLoadingDialog();
        Api.getClient(HttpRequest.baseUrl_order).ordering(Api.getRequestBody(orderingParams)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<OrderingBean>() {
                    @Override
                    public void onSuccess(OrderingBean userBean) {
                        PayParams payParams = new PayParams();
                        List<Integer> ins = new ArrayList<>();
                        if(null != userBean && null != userBean.orderList){
                            for(OrderingBean.OrderListBean bean : userBean.orderList){
                                ins.add(bean.id);
                            }
                        }

                        payParams.orderIds = ins;
                        startActivity( PayActivity.newIntent(OrderingActivity.this,payParams,totalMoney));
                        closeLoadingDialog();
                        setResult(RESULT_CODE_ORDERING);
                        finish();
                    }

                    @Override
                    protected void onFail(String errorMsg, int errorCode) {
                        closeLoadingDialog();
                    }
                });
    }


    private  void showChooseTimeDialog(String storeId) {
        try {
            ChooseTimeBean chooseTimeBean = timeBeanHashMaps.get(storeId);
            if (null == chooseTimeBean || (chooseTimeBean.today == null && chooseTimeBean.tomorrow == null)) {
                return;
            }
            BaseDialog baseDialog = new BaseDialog(this) {
                @Override
                protected int provideContentViewId() {
                    return R.layout.dialog_ordering_choose_time;
                }

                @Override
                protected void initViews(BaseDialog dialog) {

                    View close = dialog.findViewById(R.id.close);
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (null != dialog && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    });
                    RadioButton rb_1 = dialog.findViewById(R.id.rb_1);
                    rb_1.setText("今天("+CommonUtil.getWeek(0)+")");
                    RadioButton rb_2 = dialog.findViewById(R.id.rb_2);
                    rb_2.setText("明天("+CommonUtil.getWeek(1)+")");
                    RadioGroupPlus buttons = dialog.findViewById(R.id.buttons);
                    RecyclerView choosetimeRec = dialog.findViewById(R.id.recyclerview);
                    choosetimeRec.setLayoutManager(new LinearLayoutManager(choosetimeRec.getContext()));
                    ChooseTimeAdapter chooseTimeAdapter = new ChooseTimeAdapter();
                    choosetimeRec.setAdapter(chooseTimeAdapter);
                    chooseTimeAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                            if (buttons.getCheckedRadioButtonId() == R.id.rb_1) {
                                if (chooseTimeBean.pre != position) {
                                    ChooseTimeBean.ChooseTimeItemBean item = (ChooseTimeBean.ChooseTimeItemBean) adapter.getItem(position);
                                    item.choosed = true;

                                    if (-1 != chooseTimeBean.pre) {
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.today.get(chooseTimeBean.pre);
                                        preItem.choosed = false;
                                        chooseTimeAdapter.notifyItemChanged(chooseTimeBean.pre);
                                    }
                                    if (-1 != chooseTimeBean.pre2) {
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.tomorrow.get(chooseTimeBean.pre2);
                                        preItem.choosed = false;
                                        chooseTimeBean.pre2 = -1;
                                    }

                                    chooseTimeAdapter.notifyItemChanged(position);
                                    chooseTimeBean.pre = position;
                                    chooseTimeBean.  mCurrentChooseTimeBean = item;
                                }
                            } else {
                                if (chooseTimeBean.pre2 != position) {
                                    ChooseTimeBean.ChooseTimeItemBean item = (ChooseTimeBean.ChooseTimeItemBean) adapter.getItem(position);
                                    item.choosed = true;

                                    if (-1 != chooseTimeBean.pre2) {
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.tomorrow.get(chooseTimeBean.pre2);
                                        preItem.choosed = false;
                                        chooseTimeAdapter.notifyItemChanged(chooseTimeBean.pre2);
                                    }
                                    if (-1 != chooseTimeBean.pre) {
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.today.get(chooseTimeBean.pre);
                                        preItem.choosed = false;
                                        chooseTimeBean.pre = -1;
                                    }

                                    chooseTimeAdapter.notifyItemChanged(position);
                                    chooseTimeBean.pre2 = position;
                                    chooseTimeBean.   mCurrentChooseTimeBean = item;
                                }
                            }
                            setTimeView(storeId,chooseTimeBean.mCurrentChooseTimeBean,chooseTimeBean.pre >= 0?0:1);
                        }
                    });

                    buttons.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

                            switch (checkedId) {
                                case R.id.rb_1:
                                    chooseTimeAdapter.setNewInstance(chooseTimeBean.today);
                                    break;
                                case R.id.rb_2:
                                    chooseTimeAdapter.setNewInstance(chooseTimeBean.tomorrow);
                                    break;
                            }
                        }
                    });

                    if (chooseTimeBean.today == null || chooseTimeBean.today.size()==0) {
                        rb_1.setVisibility(View.GONE);
                        buttons.check(R.id.rb_2);
                    } else {
                        buttons.check(R.id.rb_1);
                    }

                }
            };
            baseDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimeView(String storeId, ChooseTimeBean.ChooseTimeItemBean mCurrentChooseTimeBean,int day) {
        if(isSingle){
            tv_time_value.setText("大约"+(day ==0?"今天":"明天")+mCurrentChooseTimeBean.arriveTime+"送到");
        }else{
            List<OrderingConfirmBean.StoreOrderConfirmItemsBean> data = orderingAdapter.getData();
            for(int i=0;i<data.size();i++){
                if(storeId.equals(data.get(i).getStoreId()+"")){
                    data.get(i).feightTemplateDetail = mCurrentChooseTimeBean.arriveTime;
                    data.get(i).feightTemplateDetailId = mCurrentChooseTimeBean.feightTemplateDetailId;
                    data.get(i).currentDay = day;
                    orderingAdapter.notifyItemChanged(i+orderingAdapter.getHeaderLayoutCount());
                    return;
                }
            }
        }
    }

    private void setAddressView() {
        tv_dizhi.setText(mAddress.detailAddress);
        tv_dizhi2.setVisibility(View.VISIBLE);
        tv_dizhi2.setText(mAddress.name+mAddress.phoneNumber);
    }

    private void toChooseAddressActivity() {
        Intent intent = new Intent(OrderingActivity.this, ChooseAddressActivity.class);
        intent.putExtra("from",1);
        startActivityForResult(intent, REQUEST_CODE_CHOOSEADDRESS_ACTIVITY_ORDERING);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        if(event.type == CloseEvent.TYPE_PAY){
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderingEvent(OrderingEvent event) {
        showChooseTimeDialog(event.storeId);
    }
}
