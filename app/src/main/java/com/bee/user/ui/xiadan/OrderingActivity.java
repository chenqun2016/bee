package com.bee.user.ui.xiadan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.PicassoRoundTransform;
import com.bee.user.R;
import com.bee.user.bean.AddressBean;
import com.bee.user.bean.ChooseTimeBean;
import com.bee.user.bean.StoreBean;
import com.bee.user.event.CloseEvent;
import com.bee.user.ui.adapter.ChooseTimeAdapter;
import com.bee.user.ui.adapter.OrderingAdapter;
import com.bee.user.ui.base.BaseDialog;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.widget.AddRemoveView;
import com.bee.user.widget.RadioGroupPlus;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gyf.immersionbar.ImmersionBar;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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

    private TextView tv_dizhi;
    private TextView tv_dizhi2;

    @OnClick({R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_confirm:
                showChooseCanjuDialog();
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
        if(requestCode == 1 && resultCode == 1){

            AddressBean address = (AddressBean) data.getSerializableExtra("address");
            tv_dizhi.setText("1");
            tv_dizhi2.setVisibility(View.VISIBLE);
            tv_dizhi2.setText("2");
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

        ViewGroup.LayoutParams layoutParams = statusheight.getLayoutParams();
        layoutParams.height = ImmersionBar.getStatusBarHeight(this);
        statusheight.setLayoutParams(layoutParams);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        OrderingAdapter orderingAdapter = new OrderingAdapter();
        recyclerView.setAdapter(orderingAdapter);


        View head = View.inflate(this, R.layout.head_ordering, null);
        View foot = View.inflate(this, R.layout.foot_ordering, null);
        orderingAdapter.addHeaderView(head);
        orderingAdapter.addFooterView(foot);
        initHeadFootView(head,foot);

        ArrayList<StoreBean> beans = new ArrayList<>();
        beans.add(new StoreBean());
        beans.add(new StoreBean());
        orderingAdapter.setNewInstance(beans);

    }

    private void initHeadFootView(View head, View foot) {
        tv_dizhi    = head.findViewById(R.id.tv_dizhi);
        tv_dizhi2   = head.findViewById(R.id.tv_dizhi2);
        tv_dizhi2.setVisibility(View.GONE);
        tv_dizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(OrderingActivity.this,ChooseAddressActivity.class),1);
            }
        });

        TextView tv_time_value = head.findViewById(R.id.tv_time_value);
        tv_time_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseTimeDialog();
            }
        });

        ImageView imageview = foot.findViewById(R.id.imageview);
        Picasso.with(this).
                load(R.drawable.banner).
                fit().
                transform(new PicassoRoundTransform(DisplayUtil.dip2px(this,10),0, PicassoRoundTransform.CornerType.ALL)).
                into(imageview);

        foot.findViewById(R.id.ll_beizhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OrderingActivity.this,BeizhuActivity.class));
            }
        });

    }

//    选择餐具
    private void showChooseCanjuDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_choose_canju);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != bottomSheetDialog && bottomSheetDialog.isShowing()){
                    bottomSheetDialog.dismiss();
                }
            }
        });
        TextView tv_pay = bottomSheetDialog.findViewById(R.id.tv_pay);
        tv_pay.setEnabled(false);
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(OrderingActivity.this,PayActivity.class));
            }
        });

        LinearLayout ll_xuyao = bottomSheetDialog.findViewById(R.id.ll_xuyao);
        AddRemoveView iv_goods_add = bottomSheetDialog.findViewById(R.id.iv_goods_add);
        TextView tv_buxuyao = bottomSheetDialog.findViewById(R.id.tv_buxuyao);

        iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
            @Override
            public void onNumChangedListener(int num) {
                if(0 == num){
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_grey_round);
                    tv_pay.setEnabled(false);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_grey);
                }else{
                    tv_pay.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                    tv_pay.setEnabled(true);
                    ll_xuyao.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);
                    tv_buxuyao.setBackgroundResource(R.drawable.btn_stroke5dp_grey);
                }

            }
        });
        tv_buxuyao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_pay.setBackgroundResource(R.drawable.btn_gradient_yellow_round);
                tv_pay.setEnabled(true);
                iv_goods_add.setNum(0);

                ll_xuyao.setBackgroundResource(R.drawable.btn_stroke5dp_grey);
                tv_buxuyao.setBackgroundResource(R.drawable.btn_stroke_bg_yellow);
            }
        });




        bottomSheetDialog.setCanceledOnTouchOutside(false);
        try {
            bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }catch (Exception e){

        }

        bottomSheetDialog.show();
    }


    private int pre = 0;
    private int pre2 = 0;

    private ChooseTimeBean mCurrentChooseTimeBean = null;
    private void showChooseTimeDialog() {
        try {

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
                        if(null != dialog && dialog.isShowing()){
                            dialog.dismiss();
                        }
                    }
                });
                RadioGroupPlus buttons = dialog.findViewById(R.id.buttons);
                RecyclerView choosetimeRec = dialog.findViewById(R.id.recyclerview);
                choosetimeRec.setLayoutManager(new LinearLayoutManager(OrderingActivity.this));
                ChooseTimeAdapter chooseTimeAdapter = new ChooseTimeAdapter();
                choosetimeRec.setAdapter(chooseTimeAdapter);
                chooseTimeAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                        if(buttons.getCheckedRadioButtonId() == R.id.rb_1){
                            if(pre != position){
                                ChooseTimeBean item = (ChooseTimeBean) adapter.getItem(position);
                                item.choosed = true;

                                if(-1 != pre){
                                    ChooseTimeBean preItem = (ChooseTimeBean) adapter.getItem(pre);
                                    preItem.choosed = false;
                                    chooseTimeAdapter.notifyItemChanged(pre);
                                }

                                chooseTimeAdapter.notifyItemChanged(position);
                                pre = position;
                                mCurrentChooseTimeBean = item;
                            }
                        }else{
                            if(pre2 != position){
                                ChooseTimeBean item = (ChooseTimeBean) adapter.getItem(position);
                                item.choosed = true;

                                if(-1 != pre2){
                                    ChooseTimeBean preItem = (ChooseTimeBean) adapter.getItem(pre2);
                                    preItem.choosed = false;
                                    chooseTimeAdapter.notifyItemChanged(pre2);
                                }

                                chooseTimeAdapter.notifyItemChanged(position);
                                pre2 = position;
                                mCurrentChooseTimeBean = item;
                            }
                        }

                    }
                });


                ArrayList<ChooseTimeBean> beans = new ArrayList<>();
                beans.add(new ChooseTimeBean("10:00-10:30(5元配送费)"));
                beans.add(new ChooseTimeBean("10:30-11:30(5元配送费)"));
                beans.add(new ChooseTimeBean("11:00-11:30(5元配送费)"));
                beans.add(new ChooseTimeBean("11:30-12:00(5元配送费)"));
                beans.add(new ChooseTimeBean("12:00-12:30(5元配送费)"));
                beans.add(new ChooseTimeBean("12:30-13:00(5元配送费)"));
                beans.add(new ChooseTimeBean("10:00-10:30(5元配送费)"));
                beans.add(new ChooseTimeBean("10:30-11:30(5元配送费)"));
                beans.add(new ChooseTimeBean("11:00-11:30(5元配送费)"));
                beans.add(new ChooseTimeBean("11:30-12:00(5元配送费)"));
                beans.add(new ChooseTimeBean("12:00-12:30(5元配送费)"));
                beans.add(new ChooseTimeBean("12:30-13:00(5元配送费)"));
                ChooseTimeBean chooseTimeBean = beans.get(0);
                chooseTimeBean.choosed = true;


                ArrayList<ChooseTimeBean> beans2 = new ArrayList<>();
                beans2.add(new ChooseTimeBean("10:00-10:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("10:30-11:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("11:00-11:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("11:30-12:00(5元配送费)"));
                beans2.add(new ChooseTimeBean("12:00-12:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("12:30-13:00(5元配送费)"));
                beans2.add(new ChooseTimeBean("10:00-10:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("10:30-11:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("11:00-11:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("11:30-12:00(5元配送费)"));
                beans2.add(new ChooseTimeBean("12:00-12:30(5元配送费)"));
                beans2.add(new ChooseTimeBean("12:30-13:00(5元配送费)"));

                ChooseTimeBean chooseTimeBean2 = beans2.get(0);
                chooseTimeBean2.choosed = true;


                buttons.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

                        switch (checkedId){
                            case R.id.rb_1:
                                chooseTimeAdapter.setNewInstance(beans);
                                break;
                            case R.id.rb_2:
                                chooseTimeAdapter.setNewInstance(beans2);
                                break;
                        }
                    }
                });
                buttons.check(R.id.rb_1);




            }
        };
            baseDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseEvent(CloseEvent event) {
        finish();
    }
}
