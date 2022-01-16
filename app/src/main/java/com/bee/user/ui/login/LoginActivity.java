package com.bee.user.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/08/21  21：17
 * 描述：一键登录页面
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.tv_xieyi) TextView tv_xieyi;

    @OnClick({R.id.tv_agree})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_agree:
                startActivity(new Intent(LoginActivity.this, CodeLoginActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews() {


        try {
            String str = "未注册手机号码登录将自动生成账号，且代表您已阅读并同意";
            String str1 = "《用户服务协议》";
            String str2 = "和";
            String str3 = "《隐私政策》";
            SpannableString msp = new SpannableString(str+str1 + str2 + str3);
//            msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.text_blue)), 0, msp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            msp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, str.length(), str.length()+str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            msp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {

                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            }, str.length()+str1.length()+str2.length(), msp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            tv_xieyi.setMovementMethod(LinkMovementMethod.getInstance());
            tv_xieyi.setHighlightColor(Color.TRANSPARENT);
            tv_xieyi.setText(msp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
