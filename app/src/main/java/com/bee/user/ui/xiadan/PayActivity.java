package com.bee.user.ui.xiadan;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/13  19：33
 * 描述：
 */
public class PayActivity extends BaseActivity {

    @OnClick({R.id.tv_pay})
    public void onClick(View view){
        showPayDialog();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public void initViews() {

    }


    private void showPayDialog(){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.dialog_pay);
        bottomSheetDialog.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null != bottomSheetDialog && bottomSheetDialog.isShowing()){
                    bottomSheetDialog.dismiss();
                }
            }
        });
        TextView tv_pay = bottomSheetDialog.findViewById(R.id.tv_pay);
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayActivity.this,PayStatusActivity.class));
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

}
