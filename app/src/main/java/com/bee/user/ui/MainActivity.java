package com.bee.user.ui;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bee.user.R;
import com.bee.user.bean.UserBean;
import com.bee.user.event.MainEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.chart.ChartFragment;
import com.bee.user.ui.home.HomeFragment;
import com.bee.user.ui.home.MiaoshaFragment;
import com.bee.user.ui.nearby.NearbyFragment;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.huaxiafinance.lc.bottomindicator.IOnTab3Click;
import com.huaxiafinance.lc.bottomindicator.IconTabPageIndicator;
import com.huaxiafinance.lc.bottomindicator.viewpager.CustomViewPager;
import com.mobile.auth.gatewayauth.AuthRegisterViewConfig;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.CustomInterface;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.PreLoginResultListener;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：app主页
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, IOnTab3Click {

    private int mOldScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;



    @BindView(R.id.tab_indicator)
    IconTabPageIndicator mIndicator;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    private ArrayList<Fragment> fragments;

//    @Override
//    protected void initImmersionBar() {
//         ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
//    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

        EventBus.getDefault().register(this);


        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance(0));
        fragments.add(NearbyFragment.newInstance());
        fragments.add(new ChartFragment());
        fragments.add(MiaoshaFragment.newInstance(0));

        MainAdapter myAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setCanScroll(false);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnTab3ClickListener(this);

        onPageSelected(0);


        initLogins();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mOldScreenOrientation != getRequestedOrientation()) {
            setRequestedOrientation(mOldScreenOrientation);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onStart() {
        super.onStart();

    }
    @Override
    public void onStop() {
        super.onStop();

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainEvent event) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
                break;
            case 1:
            case 2:
            case 3:
                status_bar.setBackgroundResource(R.color.white);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onTab3Click() {
        if(!SPUtils.geTinstance().isLogin()){

            configLoginTokenPort();

            mAlicomAuthHelper.getLoginToken(MainActivity.this, 5000);
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        }
        return false;

    }

    private static final int SERVICE_TYPE_AUTH = 1;//号码认证
    private static final int SERVICE_TYPE_LOGIN = 2;//一键登录
    private String token;
    private boolean canOneKeyLogin = true;
    private PhoneNumberAuthHelper mAlicomAuthHelper;
    private TokenResultListener mTokenListener;
    private void initLogins() {
        /*
         *   1.init get token callback Listener
         */
        mTokenListener = new TokenResultListener() {
            @Override
            public void onTokenSuccess(final String ret) {
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        LogUtil.e("xxxxxx", "onTokenSuccess:" + ret);


                        TokenRet tokenRet = null;
                        try {
                            tokenRet = JSON.parseObject(ret, TokenRet.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (tokenRet != null && ("600024").equals(tokenRet.getCode())) {
                            LogUtil.e("终端自检成功:\n" + ret);
                            canOneKeyLogin = true;
                        }

                        if (tokenRet != null && ("600001").equals(tokenRet.getCode())) {
                            LogUtil.e("唤起授权页成功:\n" + ret);
                        }

                        if (tokenRet != null && ("600000").equals(tokenRet.getCode())) {
                            token = tokenRet.getToken();
                            mAlicomAuthHelper.quitLoginPage();
                            LogUtil.e("获取token成功:\n" + ret);

//                            调用后台登录接口

                        }
                    }
                });
            }

            @Override
            public void onTokenFailed(final String ret) {
                Log.e("xxxxxx", "onTokenFailed:" + ret);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*
                         *  setText just show the result for get token
                         *  do something when getToken failed, such as use sms verify code.
                         */
                        mAlicomAuthHelper.hideLoginLoading();
                        LogUtil.e("失败:\n" + ret);
//                        mAlicomAuthHelper.quitLoginPage();
                    }
                });
            }
        };

        /*
         *   2.init AlicomAuthHelper with tokenListener
         */
        mAlicomAuthHelper = PhoneNumberAuthHelper.getInstance(this, mTokenListener);
        mAlicomAuthHelper.setAuthListener(mTokenListener);
        /*
         *   3.set debugMode when app is in debug mode, sdk will print log in debug mode
         */
        mAlicomAuthHelper.getReporter().setLoggerEnable(true);
        mAlicomAuthHelper.setAuthSDKInfo("z9EOYIEXa2vSfFNnCn7KTPnT1zsKpCPftX/GN3bcx2+vjKPv80mevxDEfMNCudnvcgmPp3c3gt0tc8egJItztzqPMAOZx3l64+zc505IrEsgwNTcDDgP2taWVEZDhURamxSyyXklayk7EjnQY/2CkGfQA5gvE53jXVNZnRBTMVwGVei7xDzDVDPf4zOP+YAfN+PURurWMuZvJHek/EQt3iJMmjz5PJjWS+CtC75EjzlspwlqQbN9Rf4anzNkYknNzZGdqsHd2TJVDZ/ky2FArQ==");
        /*
         *   使用一键登录传入 SERVICE_TYPE_LOGIN
         *   使用号码校验传入 SERVICE_TYPE_AUTH
         */
        mAlicomAuthHelper.checkEnvAvailable(SERVICE_TYPE_LOGIN);



        /**
         * 控件点击事件回调
         */
        mAlicomAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, JSONObject jsonObj) {
                Log.e("authSDK", "OnUIControlClick:code=" + code + ", jsonObj=" + (jsonObj == null ? "" : jsonObj.toJSONString()));
//                一键登录
                if("700002".equals(code)){

                    Api.getClient().login(token).subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSubscriber<UserBean>() {
                                @Override
                                public void onSuccess(UserBean userBean) {

                                }
                            });

                }else if("700001".equals(code)){
//                其它手机号登录


                }

            }
        });

//        预取号
        if(!SPUtils.geTinstance().isLogin()) {
            mAlicomAuthHelper.accelerateLoginPage(5000, new PreLoginResultListener() {
                @Override
                public void onTokenSuccess(final String vendor) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.e(vendor + "预取号成功！");
                        }
                    });
                }

                @Override
                public void onTokenFailed(final String vendor, final String ret) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.e(vendor + "预取号失败:\n" + ret);
                        }
                    });
                }
            });
        }
    }

    private void configLoginTokenPort() {
        initDynamicView();
        mAlicomAuthHelper.removeAuthRegisterXmlConfig();
        mAlicomAuthHelper.removeAuthRegisterViewConfig();
        mAlicomAuthHelper.addAuthRegistViewConfig("switch_acc_tv", new AuthRegisterViewConfig.Builder()
                .setView(switchTV)
                .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(new CustomInterface() {
                    @Override
                    public void onClick(Context context) {
                        mAlicomAuthHelper.quitLoginPage();
                    }
                }).build());
        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }
        mAlicomAuthHelper.setAuthUIConfig(new AuthUIConfig.Builder()
                .setAppPrivacyOne("《自定义隐私协议》", "https://www.baidu.com")
                .setAppPrivacyColor(Color.GRAY, Color.parseColor("#002E00"))
                .setPrivacyState(false)
                .setCheckboxHidden(true)
                .setStatusBarColor(Color.TRANSPARENT)
                .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
                .setLightColor(true)
                .setAuthPageActIn("in_activity", "out_activity")
                .setAuthPageActOut("in_activity", "out_activity")
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setLogoImgPath("logo_login")
                .setLogoScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setLogoHeight(-2)
                .setLogoWidth(-2)
                .setNavHidden(true)
                .setScreenOrientation(authPageOrientation)
                .create());
    }

    private TextView switchTV;
//    初始化一键登录自定义页面
    private void initDynamicView() {

        switchTV = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, (int) DisplayUtil.dp2px(this, 50));
        mLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mLayoutParams2.setMargins(0, (int) DisplayUtil.dp2px(this, 450), 0, 0);
        switchTV.setText("-----  自定义view  -----");
        switchTV.setTextColor(0xff999999);
        switchTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13.0F);
        switchTV.setLayoutParams(mLayoutParams2);
    }
}