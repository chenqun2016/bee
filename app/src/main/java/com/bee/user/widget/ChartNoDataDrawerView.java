package com.bee.user.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.ui.adapter.ChartUnavalabeRecyclerviewAdapter;
import com.bee.user.utils.DisplayUtil;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 创建时间：2021/5/30
 * 编写人： 陈陈陈
 * 功能描述：
 */
public  class ChartNoDataDrawerView extends FrameLayout implements View.OnClickListener {
    private TextView tv_xia;
    private RecyclerView recyclerView_unavalable_data;
    private TextView tv_shang;
    ViewGroup.LayoutParams params;
    ChartUnavalabeRecyclerviewAdapter chartUnavalabeRecyclerviewAdapter;
    LinearLayout ll_drawer;
    private int heightSelected;

    private int commonHeight ;
    boolean isShow = false;

    public ChartNoDataDrawerView(Context context) {
        this(context, null);
    }


    public ChartNoDataDrawerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartNoDataDrawerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = View.inflate(context, R.layout.item_chart_nodata_drawer, this);
        ll_drawer = view.findViewById(R.id.ll_drawer);
        tv_xia = view.findViewById(R.id.tv_xia);
        recyclerView_unavalable_data  = view.findViewById(R.id.recyclerView_unavalable_data);
        tv_shang  = view.findViewById(R.id.tv_shang);
        tv_xia.setOnClickListener(this);
        tv_shang.setOnClickListener(this);
        recyclerView_unavalable_data.setLayoutManager(new LinearLayoutManager(getContext()));
        chartUnavalabeRecyclerviewAdapter = new ChartUnavalabeRecyclerviewAdapter();
        recyclerView_unavalable_data.setAdapter(chartUnavalabeRecyclerviewAdapter);

        params = ll_drawer.getLayoutParams();
        commonHeight = DisplayUtil.dip2px(getContext(),50);
    }

    public void setDatas(List<ChartBean> beans){
        chartUnavalabeRecyclerviewAdapter.setNewInstance(beans);
        isShow = false;
        ll_drawer.post(new Runnable() {
            @Override
            public void run() {
                params.height = commonHeight;
                ll_drawer.setLayoutParams(params);
                ll_drawer.setVisibility(View.VISIBLE);
            }
        });
    }


    private ValueAnimator showAnimation;
    private ValueAnimator closeAnimation;


    public void show() {
        if(isShow){
            return;
        }
        if (null != closeAnimation && closeAnimation.isRunning()) {
            closeAnimation.end();
        }

        if (null == showAnimation) {
            showAnimation = ValueAnimator.ofInt(commonHeight, heightSelected);
            showAnimation.setDuration(800);
            showAnimation.setInterpolator(new LinearInterpolator());
            showAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    ll_drawer.setLayoutParams(params);
                }
            });
        }
        showAnimation.start();

        isShow = !isShow;
    }

    public void close() {
        if(!isShow){
            return;
        }
        if (null == closeAnimation) {
            closeAnimation = ValueAnimator.ofInt(heightSelected, commonHeight);
            closeAnimation.setDuration(800);
            closeAnimation.setInterpolator(new LinearInterpolator());
            closeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    ll_drawer.setLayoutParams(params);
                }
            });
        }

        closeAnimation.start();

        isShow = !isShow;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_xia:
                show();
                break;
            case R.id.tv_shang:
                close();
                break;
        }
    }

    public void resetSize() {

        params.height = WRAP_CONTENT;
        ll_drawer.setLayoutParams(params);
        ll_drawer.post(new Runnable() {
            @Override
            public void run() {
                heightSelected = ll_drawer.getMeasuredHeight();
            }
        });

    }
}
