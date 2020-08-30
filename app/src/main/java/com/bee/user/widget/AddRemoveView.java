package com.bee.user.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

     interface OnNumChangedListener{
        void onNumChangedListener(int num);
    }
}
