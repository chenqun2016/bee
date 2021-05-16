package com.bee.user.ui.xiadan;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.bee.user.bean.AddressBean2;
import com.bee.user.bean.ChooseTimeBean;
import com.bee.user.bean.OrderingConfirmBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.event.CloseEvent;
import com.bee.user.event.OrderingEvent;
import com.bee.user.params.OrderPreParams;
import com.bee.user.params.OrderingParams;
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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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


    TextView tv_tigongcanju;

    private boolean isSingle = true;

    //headview
    private TextView tv_dizhi;
    private TextView tv_dizhi2;
    private TextView tv_time;
    private TextView tv_time_value;
    private TextView tv_paytype;
    private TextView tv_paytype_keyong;

    private int[] ids1 ;

    public String canju = "";



    private Map<String, ChooseTimeBean> timeBeanHashMaps = new HashMap<>();
    private AddressBean2 mAddress;

    OrderingAdapter orderingAdapter;
    ArrayList<Integer> storeIds;

    @OnClick({R.id.tv_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(canju)) {
                    showChooseCanjuDialog();
                } else {
                    if(null == mAddress){
                        return;
                    }
                    doSubmit();
                }

                break;

        }
    }

    public static Intent newIntent(Context context, List<StoreBean> datas) {
        Intent intent = new Intent(context, OrderingActivity.class);
        intent.putExtra("data", (Serializable) datas);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {

            AddressBean address = (AddressBean) data.getSerializableExtra("address");
            tv_dizhi.setText("新天地大厦C座1808室");
            tv_dizhi2.setVisibility(View.VISIBLE);
            tv_dizhi2.setText("夏雨天(女士)13708263728");
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

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        toolbar_title.setText("确认订单");
        ids1 = getIntent().getIntArrayExtra("ids");
        storeIds   = getIntent().getIntegerArrayListExtra("storeIds");

        isSingle = null == storeIds || storeIds.size() <= 1;



        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        orderingAdapter  = new OrderingAdapter(isSingle);
        recyclerView.setAdapter(orderingAdapter);


        View head = View.inflate(this, R.layout.head_ordering, null);
        View foot = View.inflate(this, R.layout.foot_ordering, null);
        orderingAdapter.addHeaderView(head);
        orderingAdapter.addFooterView(foot);
        initHeadFootView(head, foot);

        getDatas();
    }


    private void setDatas(OrderingConfirmBean userBean) {
        orderingAdapter.setNewInstance(userBean.getStoreOrderConfirmItems());
        tv_heji_money.setText(userBean.getCalcAmount()+"");
    }

    private void getDatas() {
        String token = SPUtils.geTinstance().getToken();
        Api.getClient(HttpRequest.baseUrl_member).listAddress().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<AddressBean2>>() {
                    @Override
                    public void onSuccess(List<AddressBean2> addressBean2) {
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
        for(Integer storeId : storeIds){
            Api.getClient(HttpRequest.baseUrl_goods).caculateTime(storeId+"").subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseSubscriber<ChooseTimeBean>() {
                        @Override
                        public void onSuccess(ChooseTimeBean bean) {
                            List<ChooseTimeBean.ChooseTimeItemBean> today = bean.getToday();
                            if (today != null && today.size() > 0) {
                                today.get(0).choosed = true;
                                bean.pre = 0;
                            } else {
                                List<ChooseTimeBean.ChooseTimeItemBean> tomorrow = bean.getTomorrow();
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
                startActivityForResult(new Intent(OrderingActivity.this, ChooseAddressActivity.class), 1);
            }
        });


//        ImageView imageview = foot.findViewById(R.id.imageview);
//        Picasso.with(this).
//                load(R.drawable.banner).
//                fit().
//                transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,10),0, PicassoRoundTransform.CornerType.ALL)).
//                into(imageview);

        foot.findViewById(R.id.tv_beizhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OrderingActivity.this, BeizhuActivity.class));
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
            public void onNumChangedListener(int num) {
                if (0 == num) {
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_grey_round);
                    tv_pay.setEnabled(false);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);

                    canju = "";
                    tv_tigongcanju.setText(canju);
                } else {
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                    tv_pay.setEnabled(true);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);
                    tv_buxuyao.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);
                    tv_aoto.setBackgroundResource(R.drawable.btn_stroke5dp_ccc);

                    canju = num + "双";
                    tv_tigongcanju.setText(canju);
                }

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

                canju = "无需餐具";
                tv_tigongcanju.setText(canju);
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

                canju = "依据餐量提供";
                tv_tigongcanju.setText(canju);
            }
        });


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

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
        orderingParams.cartIds = ids1;

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


    private void doSubmit() {
        OrderingParams orderingParams = new OrderingParams();


        Api.getClient(HttpRequest.baseUrl_order).ordering(Api.getRequestBody(orderingParams)).
                subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    public void onSuccess(String userBean) {
                        startActivity(new Intent(OrderingActivity.this, PayActivity.class));
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });

    }



    private  void showChooseTimeDialog(String storeId) {
        try {
            ChooseTimeBean chooseTimeBean = timeBeanHashMaps.get(storeId);
            if (null == chooseTimeBean || (chooseTimeBean.getToday() == null && chooseTimeBean.getTomorrow() == null)) {
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
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.getToday().get(chooseTimeBean.pre);
                                        preItem.choosed = false;
                                        chooseTimeAdapter.notifyItemChanged(chooseTimeBean.pre);
                                    }
                                    if (-1 != chooseTimeBean.pre2) {
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.getTomorrow().get(chooseTimeBean.pre2);
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
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.getTomorrow().get(chooseTimeBean.pre2);
                                        preItem.choosed = false;
                                        chooseTimeAdapter.notifyItemChanged(chooseTimeBean.pre2);
                                    }
                                    if (-1 != chooseTimeBean.pre) {
                                        ChooseTimeBean.ChooseTimeItemBean preItem = chooseTimeBean.getToday().get(chooseTimeBean.pre);
                                        preItem.choosed = false;
                                        chooseTimeBean.pre = -1;
                                    }

                                    chooseTimeAdapter.notifyItemChanged(position);
                                    chooseTimeBean.pre2 = position;
                                    chooseTimeBean.   mCurrentChooseTimeBean = item;
                                }
                            }

                        }
                    });

                    buttons.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

                            switch (checkedId) {
                                case R.id.rb_1:
                                    chooseTimeAdapter.setNewInstance(chooseTimeBean.getToday());
                                    break;
                                case R.id.rb_2:
                                    chooseTimeAdapter.setNewInstance(chooseTimeBean.getTomorrow());
                                    break;
                            }
                        }
                    });

                    RadioButton rb_1 = dialog.findViewById(R.id.rb_1);
                    if (chooseTimeBean.getToday() == null || chooseTimeBean.getToday().size()==0) {
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


    private void setAddressView() {
        tv_dizhi.setText(mAddress.getDetailAddress());
        tv_dizhi2.setVisibility(View.VISIBLE);
        tv_dizhi2.setText(mAddress.getName()+mAddress.getPhoneNumber());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderingEvent(OrderingEvent event) {
        showChooseTimeDialog(event.storeId);
    }
}
