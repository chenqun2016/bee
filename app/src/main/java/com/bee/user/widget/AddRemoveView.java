package com.bee.user.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.utils.DisplayUtil;
import com.github.florent37.viewanimator.ViewAnimator;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/30  16：09
 * 描述：
 */
public class AddRemoveView extends FrameLayout implements View.OnClickListener {

    private ImageView iv_remove;
    private TextView tv_num;
    private ImageView iv_add;

    private int num = 0;//选中数量

    //动画执行时间
    public static final long animalTime = 300;

    private ViewGroup parent;
    private View end;

    public void setMin(int min) {
        this.min = min;
    }

    private int min = 0;
    private int max = 100;
    private OnNumChangedListener mOnNumChangedListener;

    public AddRemoveView(Context context) {
        this(context, null);
    }

    public AddRemoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AddRemoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View view = View.inflate(context, R.layout.item_add_remove, this);
        iv_remove = view.findViewById(R.id.iv_remove);
        tv_num = view.findViewById(R.id.tv_num);
        iv_add = view.findViewById(R.id.iv_add);
        iv_remove.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        iv_remove.setVisibility(GONE);
        tv_num.setVisibility(GONE);
    }

    public void initAnimalView(ViewGroup parent, View end) {
        this.parent = parent;
        this.end = end;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                if (num >= max) {
                    break;
                }
                if (num == 0) {
                    iv_remove.setVisibility(VISIBLE);
                    tv_num.setVisibility(VISIBLE);
                }

                if (null != mOnNumChangedListener) {
                    boolean doAnimal = mOnNumChangedListener.onAddListener(num+1);
                    if(doAnimal){
                        num += 1;
                        tv_num.setText(num + "");
                        if ( null != parent && null != end) {
                            doChartAnimal(getContext(), iv_add, parent, end);
                        }
                    }
                }

                break;

            case R.id.iv_remove:
                if (num <= min) {
                    break;
                }
                if (null != mOnNumChangedListener) {
                   boolean autoRemo =  mOnNumChangedListener.onRemoveListener(num-1);
                   if(autoRemo){
                       num -= 1;
                       if (num == 0) {
                           iv_remove.setVisibility(GONE);
                           tv_num.setVisibility(GONE);
                       }
                       tv_num.setText(num + "");
                   }
                }
                break;
        }
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num > 0) {
            iv_remove.setVisibility(VISIBLE);
            tv_num.setVisibility(VISIBLE);
        } else {
            iv_remove.setVisibility(GONE);
            tv_num.setVisibility(GONE);
        }
        tv_num.setText(num + "");
    }

    public void setOnNumChangedListener(OnNumChangedListener listener) {
        this.mOnNumChangedListener = listener;
    }

    public interface OnNumChangedListener {
        boolean onAddListener(int num);

        boolean onRemoveListener(int num);
    }


    /**
     * 首页金币活动位动画
     *
     * @param context 上下文
     * @param child   设置动画的view
     * @param parent  全局父布局
     * @param end     锚点view
     */
    public static void doChartAnimal(Context context, ImageView child, ViewGroup parent, View end) {
        float[] mCurrentPosition = new float[2];
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到end的位置)
        final ImageView goods = new ImageView(context);
        goods.setImageDrawable(child.getDrawable());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        parent.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        parent.getLocationInWindow(parentLocation);

        //用于计算动画开始的坐标
        int startLoc[] = new int[2];
        child.getLocationInWindow(startLoc);

        //用于计算动画结束后的坐标
        int endLoc[] = new int[2];
        end.getLocationInWindow(endLoc);

        int childWidth = child.getMeasuredWidth() / 2;
        int endWidth = end.getMeasuredWidth() / 2;

//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的起始点：起始点-父布局起始点
        float startX = startLoc[0] - parentLocation[0];
        float startY = startLoc[1] - parentLocation[1];

        //掉落后的终点坐标：
        float toX = endLoc[0] - parentLocation[0] + endWidth - childWidth;
        float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
//        path.quadTo(toX, startY, toX, toY);
        float x1 = ((startX + toX) / 2 + startX) / 2;
        float y = (toY - startY) / 3;
        if(y < DisplayUtil.dip2px(context,100)){
            y = DisplayUtil.dip2px(context,100);
        }
        float y1 = startY - y;
        path.cubicTo(x1, y1, toX, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        PathMeasure mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(animalTime);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // 把移动的图片imageview从父布局里移除
                parent.removeView(goods);

                //设置锚点view抖动动画
                ViewAnimator.animate(end).scaleX(1, 1.25f, 0.75f, 1.15f, 0.85f, 1.10f, 1).scaleY(1, 0.75f, 1.25f, 0.85f, 1.15f, 0.9f, 1).duration(500).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        //设置透明度渐变，缩小动画
//        ViewAnimator.animate(goods).alpha(1f, 0.4f).scaleY(1f, 0.4f).scaleX(1f, 0.4f).duration(animalTime).start();

    }
}
