package com.bee.user.ui.mine;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/28  19：51
 * 描述：
 */
public class HelpCenterItemItemActivity extends BaseActivity {


    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_left)
    TextView tv_left;
    @BindView(R.id.tv_right)
    TextView tv_right;

    private int helpType = -1;

    @OnClick({R.id.tv_left,R.id.tv_right,R.id.tv_phone})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_left:
                helpType = 0;
                changeType();
                break;
            case R.id.tv_right:
                helpType = 1;
                changeType();
                break;

            case R.id.tv_phone:
                CommonUtil.callPhone(this,"10086");
                break;
        }


    }

    private void changeType() {
        if(helpType == 0){
            Drawable drawable = getResources().getDrawable(R.drawable.bang_hong);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_left.setCompoundDrawables(drawable, null, null, null);

            drawable = getResources().getDrawable(R.drawable.cha_huise);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_right.setCompoundDrawables(drawable, null, null, null);
        }else if(helpType == 1){
            Drawable drawable = getResources().getDrawable(R.drawable.bang_huise);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_left.setCompoundDrawables(drawable, null, null, null);

            drawable = getResources().getDrawable(R.drawable.cha_lan);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_right.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_helpcenter_item_item;
    }

    @Override
    public void initViews() {


        String str = "问题仍未解决？";
        SpannableString msp = new SpannableString(str+"联系人工客服");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_3e7dfb)), str.length(), msp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        tv_phone.setText(msp);

    }
}
