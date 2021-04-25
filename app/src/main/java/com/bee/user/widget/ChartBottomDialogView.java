package com.bee.user.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.ui.adapter.SelectedFoodAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：进京赶考
 * 创建时间：2021/03/14  20：34
 * 描述：
 */
public class ChartBottomDialogView extends FrameLayout {
    View view_background;
    DragDialogLayout view_selected;

    private int heightSelected;

    ViewGroup.LayoutParams params;



    public ChartBottomDialogView(@NonNull Context context) {
        this(context, null);
    }

    public ChartBottomDialogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartBottomDialogView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = View.inflate(context, R.layout.view_chart_bottom_dialogview, this);

        view_background = view.findViewById(R.id.view_background);
        view_selected = view.findViewById(R.id.view_selected);

        initSelectedFoodDialog();
    }

    SelectedFoodAdapter selectedFoodAdapter;
    private void initSelectedFoodDialog() {
        view_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });


        view_selected.setNextPageListener(new DragDialogLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                closeOhter();

                params.height = 0;
                view_selected.setLayoutParams(params);

                isShow = !isShow;
            }
        });

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        List<ChartBean> foodBeans = new ArrayList<>();
        foodBeans.add(new ChartBean());
        foodBeans.add(new ChartBean());

        selectedFoodAdapter  = new SelectedFoodAdapter(foodBeans);
        recyclerview.setAdapter(selectedFoodAdapter);
        selectedFoodAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

            }
        });

        view_background.setAlpha(0);

    }

    public void reflushAdapter(List<ChartBean> beans){
        selectedFoodAdapter.setNewInstance(beans);
    }

    //初始化view的高度
    public void initDatas(int windowHeight) {
        view_selected.post(new Runnable() {
            @Override
            public void run() {
                heightSelected = view_selected.getMeasuredHeight();

                if (heightSelected > windowHeight / 2f) {
                    heightSelected = windowHeight / 2;
                }
                params = view_selected.getLayoutParams();
                params.height = 0;
                view_selected.setLayoutParams(params);
                view_selected.setVisibility(View.VISIBLE);
            }
        });
    }


//已选商品相关代码

    boolean isShow = false;

    public void showSelectedDialog() {
        if (!isShow) {
            show();

        } else {
            close();
        }


    }

    private ValueAnimator showAnimation;
    private ValueAnimator closeAnimation;
    private ValueAnimator alphaAnimation1;
    private ValueAnimator alphaAnimation2;


    public void show() {
        if (null != alphaAnimation2 && alphaAnimation2.isRunning()) {
            alphaAnimation2.end();
        }

        view_background.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view_background.setLayoutParams(layoutParams);

        if (null == alphaAnimation1) {
            alphaAnimation1 = ValueAnimator.ofFloat(0, 1);
            alphaAnimation1.setDuration(300);
            alphaAnimation1.setInterpolator(new LinearInterpolator());
            alphaAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view_background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation1.start();




        if (null != closeAnimation && closeAnimation.isRunning()) {
            closeAnimation.end();
        }

        if (null == showAnimation) {
            showAnimation = ValueAnimator.ofInt(0, heightSelected);
            showAnimation.setDuration(300);
            showAnimation.setInterpolator(new LinearInterpolator());
            showAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    view_selected.setLayoutParams(params);
                }
            });
        }
        showAnimation.start();

        isShow = !isShow;
    }

    public void close() {
        closeOhter();


        if (null == closeAnimation) {
            closeAnimation = ValueAnimator.ofInt(heightSelected, 0);
            closeAnimation.setDuration(300);
            closeAnimation.setInterpolator(new LinearInterpolator());
            closeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    view_selected.setLayoutParams(params);
                }
            });
        }

        closeAnimation.start();

        isShow = !isShow;
    }

    private void closeOhter() {
        if (null != alphaAnimation1 && alphaAnimation1.isRunning()) {
            alphaAnimation1.end();
        }

        if (null == alphaAnimation2) {
            alphaAnimation2 = ValueAnimator.ofFloat(1, 0);
            alphaAnimation2.setDuration(300);
            alphaAnimation2.setInterpolator(new LinearInterpolator());
            alphaAnimation2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    view_background.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
                    layoutParams.height = 1;
                    layoutParams.width = 1;
                    view_background.setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    view_background.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
                    layoutParams.height = 1;
                    layoutParams.width = 1;
                    view_background.setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            alphaAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view_background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation2.start();



        if (null != showAnimation && showAnimation.isRunning()) {
            showAnimation.end();
        }
    }

    public void showSelectedView(int visible) {
        view_selected.setVisibility(visible);
    }

}
