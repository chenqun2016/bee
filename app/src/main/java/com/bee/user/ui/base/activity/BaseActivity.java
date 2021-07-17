package com.bee.user.ui.base.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.R;

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
        if (null != iv_back) {
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public abstract int getLayoutId();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != commonDialog && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private Dialog commonDialog;
    public void showCommonDialog(String content,String left,String right,DialogClickListener listener) {
        commonDialog = new Dialog(this, R.style.loadingDialogTheme);
        View inflate = View.inflate(this, R.layout.dialog_hint3, null);
        TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
        tv_des.setText(content);
        TextView tv_quxiao = (TextView) inflate.findViewById(R.id.btn_cancel);
        TextView tv_queding = (TextView) inflate.findViewById(R.id.btn_sure);
        tv_quxiao.setText(left);
        tv_queding.setText(right);

        tv_quxiao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(null != listener){
                    listener.onDialogCancle();
                }
                if (null != commonDialog && commonDialog.isShowing()) {
                    commonDialog.dismiss();
                }

            }
        });
        tv_queding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(null != listener){
                    listener.onDialogSure();
                }
                if (null != commonDialog && commonDialog.isShowing()) {
                    commonDialog.dismiss();
                }
            }
        });
        commonDialog.setContentView(inflate);
        commonDialog.show();
    }

    public interface DialogClickListener {
        void onDialogCancle();

        void onDialogSure();
    }
}
