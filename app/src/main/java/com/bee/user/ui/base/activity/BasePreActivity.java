package com.bee.user.ui.base.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bee.user.R;
import com.bee.user.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.OnKeyboardListener;

import butterknife.ButterKnife;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/21  16：13
 * 描述：基基类
 */
public class BasePreActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();

    protected LinearLayout base_pre_content;
    protected View status_bar;

    //该属性能防止屏幕被截图和录制
    private boolean mCanScreenshort= true;;
    private boolean mAdjustView = false;
    private ViewGroup.LayoutParams mParams =  new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    protected void setCanScreenshort(boolean mCanScreenshort) {
        this.mCanScreenshort = mCanScreenshort;
    }
    public void setmAdjustView(boolean mAdjustView) {
        this.mAdjustView = mAdjustView;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(mCanScreenshort){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
        }else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        }

        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base_pre);
        LogUtil.d("classname",this.getClass().getName()+"");
        base_pre_content = (LinearLayout) findViewById(R.id.base_pre_content);
        status_bar =  findViewById(R.id.status_bar);

        //初始化沉浸式状态栏
        initImmersionBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }


    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (base_pre_content == null) return;
        if (null != params) mParams = params;

        setContentView(view);
    }
    @Override
    public void setContentView(View view) {
        if (base_pre_content == null) return;
        base_pre_content.addView(view,mParams);

        ButterKnife.bind(this);
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar  mImmersionBar = ImmersionBar.with(this);
        if (mAdjustView) {
            //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            mImmersionBar.keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        } else {
            mImmersionBar.keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
        mImmersionBar.setOnKeyboardListener(new OnKeyboardListener() {    //软键盘监听回调，keyboardEnable为true才会回调此方法
            @Override
            public void onKeyboardChange(boolean isPopup, int keyboardHeight) {
                LogUtil.e(isPopup+"");  //isPopup为true，软键盘弹出，为false，软键盘关闭
            }
        });
        mImmersionBar.statusBarView(status_bar);
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.init();
    }


}
