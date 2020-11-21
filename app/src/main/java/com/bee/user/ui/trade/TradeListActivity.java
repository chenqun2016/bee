package com.bee.user.ui.trade;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.TradeRecordBean;
import com.bee.user.entity.TradeListEntity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import com.bee.user.widget.PinnedSectionDecoration;
import com.bee.user.widget.RadioGroupPlus;
import com.huaxiafinance.www.crecyclerview.crecyclerView.CRecyclerView;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/17  21：08
 * 描述：
 */
public class TradeListActivity extends BaseActivity {
    @BindView(R.id.crecyclerview)
    CRecyclerView crecyclerview;


    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.background)
    View background;

    @OnClick(R.id.shaixuan)
    public void onClick(View view){

        showShaixuan();
    }

    private void showShaixuan() {
        View mMenuView = LayoutInflater.from(this).inflate(R.layout.popop_window_trade_list, null);
        PopupWindow  popupWindow = new PopupWindow(mMenuView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        RadioGroupPlus rp_1 = mMenuView.findViewById(R.id.rp_1);
        rp_1.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

            }
        });
        RadioGroupPlus rp_2 = mMenuView.findViewById(R.id.rp_2);
        rp_2.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupPlus group, int checkedId) {

            }
        });
        rp_1.check(R.id.rb_1);
        rp_2.check(R.id.rb_11);

        View btn_1 = mMenuView.findViewById(R.id.btn_1);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rp_1.check(R.id.rb_1);
                rp_2.check(R.id.rb_11);
            }
        });
        View btn_2 = mMenuView.findViewById(R.id.btn_2);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


        popupWindow.setAnimationStyle(R.style.AnimTools);
        //实例化一个ColorDrawable颜色为半透明0xb0000000
        ColorDrawable dw = new ColorDrawable(0x00000000);//
//        //设置SelectPicPopupWindow弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                close();
            }
        });
        popupWindow.showAsDropDown(toolbar_title, 0, 0);
        show();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_trade_list;
    }

    @Override
    public void initViews() {

        View empty = View.inflate(this, R.layout.empty_trade_list, null);
        CommonUtil.initBuyCardView(empty);

        TextView tv_guangguang  = empty.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        crecyclerview.setView(TradeListEntity.class);
        crecyclerview.setEmptyView(empty);
        crecyclerview.setRow(20);
        crecyclerview.setCanLoadMore(true);
//        crecyclerview.setItemDecoration(new PinnedSectionDecoration(this, new PinnedSectionDecoration.DecorationCallback() {
//            @Override
//            public String getGroupText(int position) {
//                if(null == crecyclerview.getBaseAdapter() ||  position < 0 || crecyclerview.getBaseAdapter().getData().size() <= position ){
//                    return "";
//                }
//
//                return "近一年交易记录";
//            }
//        }));
        crecyclerview.setOnRequestListener(new CRecyclerView.OnRequestListener() {
            @Override
            public void onRequestEnd() {

            }

            @Override
            public void onNoData() {
            }

            @Override
            public void onNoMoreDatas() {

            }

            @Override
            public void onRefreshStart() {

            }
        });
        crecyclerview.start();


        ArrayList<TradeRecordBean> beans = new ArrayList<>();
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());
        beans.add(new TradeRecordBean());



        crecyclerview.getBaseAdapter().setList(beans);
    }












//弹窗popwindow 的时候，背景同步变暗。
    private ValueAnimator alphaAnimation1;
    private ValueAnimator alphaAnimation2;
    public void show(){
        if (null != alphaAnimation2 && alphaAnimation2.isRunning()) {
            alphaAnimation2.end();
        }
        background.setVisibility(View.VISIBLE);
        if (null == alphaAnimation1) {
            alphaAnimation1 = ValueAnimator.ofFloat(0, 1);
            alphaAnimation1.setDuration(200);
            alphaAnimation1.setInterpolator(new LinearInterpolator());
            alphaAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation1.start();
    }
    public void close(){
        if (null != alphaAnimation1 && alphaAnimation1.isRunning()) {
            alphaAnimation1.end();
        }

        if (null == alphaAnimation2) {
            alphaAnimation2 = ValueAnimator.ofFloat(1, 0);
            alphaAnimation2.setDuration(200);
            alphaAnimation2.setInterpolator(new LinearInterpolator());
            alphaAnimation2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.GONE);
                    background.setAlpha(0);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    background.setVisibility(View.GONE);
                    background.setAlpha(0);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            alphaAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation2.start();

    }
}
