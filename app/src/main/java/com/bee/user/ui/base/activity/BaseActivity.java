package com.bee.user.ui.base.activity;

import android.animation.Animator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BasePreActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/20  21：31
 * 描述：基类
 */
public abstract class BaseActivity extends BasePreActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        initViewsPre();
        initViews();
    }

    protected void initViewsPre() {
        View iv_back = findViewById(R.id.iv_back);
        if(null != iv_back){
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public abstract int getLayoutId() ;
    public abstract void initViews();



    private Dialog dialog;
    private View dialogView;
    //加载中 弹窗 不能回退
    public void showLoadingDialog() {
        initLoadingDialog();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
        if (null != dialog) {
            dialog.show();
        }
    }

    private void initLoadingDialog() {
        if (null == dialogView) {
            dialogView = View.inflate(this, R.layout.loading_large, null);
        }
        if (null == dialog) {
            dialog = new Dialog(dialogView.getContext(), R.style.loadingDialogTheme);
            dialog.setContentView(dialogView);
        }
    }

    public void closeLoadingDialog() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
