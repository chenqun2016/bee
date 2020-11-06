package com.bee.user.ui.giftcard;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/06  16：08
 * 描述：
 */
public class GetGiftcardActivity extends BaseActivity {
    @OnClick(R.id.tv_2)
    public void onClick(){

            Dialog  dialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(this, R.layout.dialog_get_giftcard, null);
            inflate.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != dialog) {
                        dialog.dismiss();
                    }

                }
            });

        inflate.findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != dialog) {
                    dialog.dismiss();
                }

            }
        });
        dialog.setContentView(inflate);

        dialog.show();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_get_giftcard;
    }

    @Override
    public void initViews() {

    }
}
