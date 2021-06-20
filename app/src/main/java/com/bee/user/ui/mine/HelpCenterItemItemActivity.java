package com.bee.user.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import com.bee.user.R;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CommonUtil;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    @BindView(R.id.tv_help_title)
    TextView tv_help_title;
    @BindView(R.id.tv_help_answer)
    TextView tv_help_answer;

    private int helpType = -1;
    private int contentId = -1;
    private int id = -1;

    @OnClick({R.id.tv_left,R.id.tv_right,R.id.tv_phone})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_left:
                helpType = 1;
                toApraise();
                break;
            case R.id.tv_right:
                helpType = 0;
                toApraise();
                break;
            case R.id.tv_phone:
                CommonUtil.callPhone(this,"10086");
                break;
        }

    }

    /**
     * 去评价
     */
    private void toApraise() {
        Map<String, Integer> map = new HashMap<>();
        map.put("appraise", helpType);
        map.put("contentId", contentId);
        map.put("id", id);
        Api.getClient(HttpRequest.baseUrl_sys).helpApraise(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                        changeType();
                    }
                });
    }

    private void changeType() {
        if(helpType == 1){
            Drawable drawable = getResources().getDrawable(R.drawable.bang_hong);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_left.setCompoundDrawables(drawable, null, null, null);

            drawable = getResources().getDrawable(R.drawable.cha_huise);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tv_right.setCompoundDrawables(drawable, null, null, null);
        }else if(helpType == 0){
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
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        contentId = intent.getIntExtra("contentId", 0);
        id = intent.getIntExtra("id", 0);
        tv_help_title.setText(title);
        tv_help_answer.setText(content);

    }
}
