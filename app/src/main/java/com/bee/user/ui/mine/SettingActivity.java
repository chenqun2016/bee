package com.bee.user.ui.mine;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.AppUpdateInfoBean;
import com.bee.user.event.ExitloginEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.service.CheckUpdateService;
import com.bee.user.ui.CommonWebActivity;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.utils.CacheDataManager;
import com.bee.user.utils.DeviceUtils;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.blankj.utilcode.util.ObjectUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static androidx.core.os.HandlerCompat.postDelayed;

/**
 * 创建人：进京赶考
 * 创建时间：2020/10/14  19：29
 * 描述：
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.tv_quit)
    TextView tv_quit;

    @BindView(R.id.tv_version_text)
    TextView tv_version_text;

    @BindView(R.id.tv_huanchun_text)
    TextView tv_huanchun_text;

    @OnClick({R.id.tv_safe,R.id.tv_quit,R.id.tv_about,R.id.tv_yijian,R.id.tv_yingsi,R.id.tv_xieyi,R.id.tv_version_text,R.id.tv_huanchun_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_safe:
                if(!ObjectUtils.isEmpty(SPUtils.geTinstance().getUserInfo())) {
                    startActivity(new Intent(this,AcountSafeActivity.class));
                }else {
                    ToastUtil.ToastShort(this, "您还未登陆，请先登录");
                }

                break;
            case R.id.tv_about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.tv_quit:
                showExitDialog();
                break;
            case R.id.tv_yijian:
                startActivity(new Intent(this,FeedbackActivity.class));
                break;
            case R.id.tv_yingsi:
                Intent intent2 = new Intent(this, CommonWebActivity.class);
                intent2.putExtra("url", HttpRequest.xieyi_yinsi);
                startActivity(intent2);
                break;
            case R.id.tv_xieyi:
                Intent intent1 = new Intent(this, CommonWebActivity.class);
                intent1.putExtra("url", HttpRequest.xieyi_regist);
                startActivity(intent1);
                break;
            case R.id.tv_version_text:
                getAppUpdateInfo();
                break;
            case R.id.tv_huanchun_text:
                showLoadingDialog();
                CacheDataManager.clearAllCache(this);
                new Handler().postDelayed(() -> {
                    // 重新获取应用缓存大小
                    tv_huanchun_text.setText(CacheDataManager.getTotalCacheSize(this));
                    closeLoadingDialog();
                },500);
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_set;
    }

    @Override
    public void initViews() {
        if(!ObjectUtils.isEmpty(SPUtils.geTinstance().getUserInfo())) {
            tv_quit.setVisibility(View.VISIBLE);
        }else {
            tv_quit.setVisibility(View.GONE);
        }
        tv_version_text.setText("当前版本V"+ DeviceUtils.getAppVersionName());
        tv_huanchun_text.setText(CacheDataManager.getTotalCacheSize(this));
    }

    private Dialog systemDialog;
    private void showExitDialog() {
        if (null == systemDialog) {
            systemDialog = new Dialog(this, R.style.loadingDialogTheme);
            View inflate = View.inflate(SettingActivity.this, R.layout.dialog_hint3, null);
            TextView tv_des = (TextView) inflate.findViewById(R.id.tv_des);
            tv_des.setText("确定退出登录?");
            TextView tv_quxiao = (TextView) inflate.findViewById(R.id.btn_cancel);
            TextView tv_queding = (TextView) inflate.findViewById(R.id.btn_sure);

            tv_quxiao.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }

                }
            });
            tv_queding.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SPUtils.geTinstance().setExitlogin();
                    EventBus.getDefault().post(new ExitloginEvent());
                    if (null != systemDialog) {
                        systemDialog.dismiss();
                    }
                    SettingActivity.this.finish();
                }
            });
            systemDialog.setContentView(inflate);
        }
        systemDialog.show();
    }

    /**
     * 获取版本更新信息
     */
    private void getAppUpdateInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("versionCode", DeviceUtils.getAppVersionName());
        map.put("versionName", "Android-USER");
        Api.getClient(HttpRequest.baseUrl_sys).appUpdateInfo(Api.getRequestBody(map))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<AppUpdateInfoBean>() {
                    @Override
                    public void onSuccess(AppUpdateInfoBean appUpdateInfoBean) {
                        if(appUpdateInfoBean!=null) {
                            Integer isForceUpdate = appUpdateInfoBean.getIsForceUpdate();//是否强制更新(0:否,1:是)
                            // TODO: 2021/6/22 根据是否强制更新展示最新的弹框UI
                            startUpdate(appUpdateInfoBean.getUrl());
                        }
                    }
                });
    }

    /**
     * 下载更新
     * @param url
     */
    private void startUpdate(String url) {
        Intent intent = new Intent(this, CheckUpdateService.class);
        intent.putExtra("url",url);
        CheckUpdateService.enqueueWork(this,intent);
    }
}
