package com.bee.user.ui.order;

import android.app.Dialog;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.StoreBean;
import com.bee.user.bean.TraceBean;
import com.bee.user.ui.adapter.OrderCancelAdapter;
import com.bee.user.ui.adapter.OrderDetailAdapter;
import com.bee.user.ui.adapter.OrderTraceAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/23  17：08
 * 描述：
 */
public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    View line4;
    TextView tv_people;
    TextView tv_people_des;

    LinearLayout ll_bottom;

    private long time = 10;
    private Disposable subscription;
    private int type;


    @OnClick({R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title:
                switch (type) {
                    case 0://等待支付
                        break;
                    case 1://商家正在备货
                        break;
                    case 2://商品配送中
                        break;
                    case 3://订单已送达
                        break;
                    case 4://订单已取消
                        break;
                    case 5://退款中
                        break;
                    default:
                        showTraceDialog();
                        break;
                }

                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != subscription && !subscription.isDisposed()) {
            subscription.dispose();
            subscription = null;
        }
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 0);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        OrderDetailAdapter orderingAdapter = new OrderDetailAdapter();
        recyclerView.setAdapter(orderingAdapter);


        View foot = View.inflate(this, R.layout.foot_orderdetail, null);
        initFootView(foot);

        View head;
        switch (type) {

            case 0:
                tv_title.setText("等待支付，剩余10");
                head = View.inflate(this, R.layout.head_orderdetail_waite, null);
                initHeadViewWaite(head);

                countDown();
                break;
            case 1:
                tv_title.setText("商家正在备货");
                head = View.inflate(this, R.layout.head_orderdetail_beihuo, null);
                initHeadViewbeihuo(head);

                break;
            case 2:
                tv_title.setText("商品配送中");
                head = View.inflate(this, R.layout.head_orderdetail_beihuo, null);
                initHeadViewbeihuo(head);

                break;
            case 3:
                tv_title.setText("订单已送达");
                head = View.inflate(this, R.layout.head_orderdetail_complete, null);
                initHeadView(head);
                break;
            case 4:
                tv_title.setText("订单已取消");
                head = View.inflate(this, R.layout.head_orderdetail_quxiao, null);
                initHeadViewQuxiao(head);

                line4.setVisibility(View.GONE);
                tv_people.setVisibility(View.GONE);
                tv_people_des.setVisibility(View.GONE);
                ll_bottom.setVisibility(View.VISIBLE);
                break;
            case 5://退款中
                head = View.inflate(this, R.layout.head_orderdetail_tuikuan, null);
                initHeadViewtuikuan(head);
                tv_title.setText("订单已送达");
                break;
            default:
                head = View.inflate(this, R.layout.head_orderdetail_complete, null);
                initHeadView(head);
                break;
        }


        ArrayList<StoreBean> beans = new ArrayList<>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());

        orderingAdapter.addHeaderView(head);
        orderingAdapter.addFooterView(foot);
        orderingAdapter.setNewInstance(beans);
    }

    //退款中
    private void initHeadViewtuikuan(View head) {

    }

    //备货
    private void initHeadViewbeihuo(View head) {
        TextView tv_cancer_order = head.findViewById(R.id.tv_cancer_order);
        tv_cancer_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelConfirmDialog();
            }
        });

        TextView tv_jingkuai = head.findViewById(R.id.tv_jingkuai);

        String str1 = time + "";
        SpannableString msp = new SpannableString(str1 + "送达");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_jingkuai.setText(msp);
    }



    //等待支付
    private void initHeadViewWaite(View head) {
        TextView tv_cancel_waite = head.findViewById(R.id.tv_cancel_waite);
        tv_cancel_waite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelDialog();
            }
        });

        TextView tv_des = head.findViewById(R.id.tv_des);

        String str1 = time + "分钟";
        SpannableString msp = new SpannableString(str1 + "内未支付，订单将自动取消");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        tv_des.setText(msp);
    }

    //订单取消
    private void initHeadViewQuxiao(View head) {

    }


    //    默认
    private void initHeadView(View head) {

    }

    private void initFootView(View foot) {
        line4 = foot.findViewById(R.id.line4);
        tv_people = foot.findViewById(R.id.tv_people);
        tv_people_des = foot.findViewById(R.id.tv_people_des);
        ll_bottom = foot.findViewById(R.id.ll_bottom);
    }


    String str = "等待支付，剩余";

    private void countDown() {
        Disposable subscription = Observable.interval(0, 1, TimeUnit.SECONDS).
                take(time + 1).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {

                SpannableString msp = new SpannableString(str + (time - aLong));
                msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), str.length(), msp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

                tv_title.setText(msp);
            }

        });
    }

    //订单追踪弹窗
    private void showTraceDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_order_detail_trace);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        OrderTraceAdapter orderTraceAdapter = new OrderTraceAdapter();
        recyclerview.setAdapter(orderTraceAdapter);

        ArrayList<TraceBean> traceBeans = new ArrayList<>();
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(0));
        traceBeans.add(new TraceBean(1));
        orderTraceAdapter.setNewInstance(traceBeans);


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }


    private void showCancelDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_order_detail_cancel);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != bottomSheetDialog && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        });
        RecyclerView recyclerview = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        OrderCancelAdapter orderTraceAdapter = new OrderCancelAdapter();
        recyclerview.setAdapter(orderTraceAdapter);
        orderTraceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                orderTraceAdapter.selected = position;
                orderTraceAdapter.notifyDataSetChanged();

            }
        });

        ArrayList<String> beans = new ArrayList<>();
        beans.add("忘记使用红包");
        beans.add("信息填写错误");
        beans.add("送达时间选错了");
        beans.add("买错了/买少了");
        beans.add("我不想要了");
        orderTraceAdapter.setNewInstance(beans);


        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {

        }

        bottomSheetDialog.show();
    }

    private void showCancelConfirmDialog() {
        Dialog dialog = new Dialog(this, R.style.loadingDialogTheme);
        View inflate = View.inflate(this, R.layout.dialog_order_detail_cancel_confirm, null);
        inflate.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != dialog && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });


        dialog.setContentView(inflate);
        dialog.show();
    }

}
