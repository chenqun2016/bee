package com.bee.user.ui.mine;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.base.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/12  20：43
 * 描述：
 */
public class MineFragment extends BaseFragment {

    Unbinder bind;

    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv_4)
    TextView tv_4;

    @OnClick({R.id.tv_icon,R.id.tv_name,R.id.tv_des})
    public void onClick(){

        EventBus.getDefault().post(new MainEvent());
    }


    @Override
    protected void getDatas() {

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        bind = ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews() {
        String str = "0";
        SpannableString msp = new SpannableString(str + "\n米粒/充值");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_1.setText(msp);

        str = "0";
        msp = new SpannableString(str + "\n卡券包");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_2.setText(msp);

        str = "0";
        msp = new SpannableString(str + "\n礼品卡");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_3.setText(msp);

        str = "0";
        msp = new SpannableString(str + "\n我的积分");
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_FF6200)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        msp.setSpan(new AbsoluteSizeSpan(16, true), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_4.setText(msp);
    }
}
