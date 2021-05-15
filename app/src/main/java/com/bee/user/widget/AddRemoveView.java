package com.bee.user.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bee.user.R;

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
        iv_remove  = view.findViewById(R.id.iv_remove);
        tv_num  = view.findViewById(R.id.tv_num);
        iv_add  = view.findViewById(R.id.iv_add);

        iv_remove.setOnClickListener(this);
        iv_add.setOnClickListener(this);

        iv_remove.setVisibility(GONE);
        tv_num.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_add:
                if(num >= max){
                    break;
                }
                if(num == 0){
                    iv_remove.setVisibility(VISIBLE);
                    tv_num.setVisibility(VISIBLE);
                }
                num += 1;

                tv_num.setText(num+"");
                if(null != mOnNumChangedListener){
                    mOnNumChangedListener.onNumChangedListener(num);
                }
                break;

            case R.id.iv_remove:
                if(num <= min){
                    break;
                }
                num -= 1;
                if(num == 0){
                    iv_remove.setVisibility(GONE);
                    tv_num.setVisibility(GONE);
                }
                tv_num.setText(num+"");
                if(null != mOnNumChangedListener){
                    mOnNumChangedListener.onNumChangedListener(num);
                }
                break;
        }
    }

    public int getNum() {
        return num;
    }
    public void setNum(int num) {
        this.num = num;
        if(num > 0){
            iv_remove.setVisibility(VISIBLE);
            tv_num.setVisibility(VISIBLE);
        }else{
            iv_remove.setVisibility(GONE);
            tv_num.setVisibility(GONE);
        }
        tv_num.setText(num+"");
    }

    public void setOnNumChangedListener(OnNumChangedListener listener){
        this.mOnNumChangedListener = listener;
    }

    public  interface OnNumChangedListener{
        void onNumChangedListener(int num);
    }
//
//    private PathMeasure  mPathMeasure;
//    private void addCart( ImageView iv) {
////      一、创造出执行动画的主题---imageview
//        //代码new一个imageview，图片资源是上面的imageview的图片
//        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
//        final ImageView goods = new ImageView(getContext());
//        goods.setImageDrawable(iv.getDrawable());
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
//        rl.addView(goods, params);
//
////        二、计算动画开始/结束点的坐标的准备工作
//        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
//        int[] parentLocation = new int[2];
//        rl.getLocationInWindow(parentLocation);
//
//        //得到商品图片的坐标（用于计算动画开始的坐标）
//        int startLoc[] = new int[2];
//        iv.getLocationInWindow(startLoc);
//
//        //得到购物车图片的坐标(用于计算动画结束后的坐标)
//        int endLoc[] = new int[2];
//        cart.getLocationInWindow(endLoc);
//
//
////        三、正式开始计算动画开始/结束的坐标
//        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
//        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
//        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;
//
//        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
//        float toX = endLoc[0] - parentLocation[0] + cart.getWidth() / 5;
//        float toY = endLoc[1] - parentLocation[1];
//
////        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
//        //开始绘制贝塞尔曲线
//        Path path = new Path();
//        //移动到起始点（贝塞尔曲线的起点）
//        path.moveTo(startX, startY);
//        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
//        path.quadTo((startX + toX) / 2, startY, toX, toY);
//        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
//        // 如果是true，path会形成一个闭环
//        mPathMeasure = new PathMeasure(path, false);
//
//        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
//        valueAnimator.setDuration(1000);
//        // 匀速线性插值器
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                // 当插值计算进行时，获取中间的每个值，
//                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
//                float value = (Float) animation.getAnimatedValue();
//                // ★★★★★获取当前点坐标封装到mCurrentPosition
//                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
//                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
//                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
//                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
//                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
//                goods.setTranslationX(mCurrentPosition[0]);
//                goods.setTranslationY(mCurrentPosition[1]);
//            }
//        });
////      五、 开始执行动画
//        valueAnimator.start();
//
////      六、动画结束后的处理
//        valueAnimator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            //当动画结束后：
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                // 购物车的数量加1
//                i++;
//                count.setText(String.valueOf(i));
//                // 把移动的图片imageview从父布局里移除
//                rl.removeView(goods);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//    }

}
