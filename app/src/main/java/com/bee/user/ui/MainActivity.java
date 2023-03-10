package com.bee.user.ui;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bee.user.R;
import com.bee.user.bean.AppUpdateInfoBean;
import com.bee.user.bean.UserBean;
import com.bee.user.event.LocationChangedEvent;
import com.bee.user.event.MainEvent;
import com.bee.user.event.ReflushEvent;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.service.CheckUpdateService;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.chart.ChartFragment;
import com.bee.user.ui.home.HomeFragment;
import com.bee.user.ui.login.CodeLoginActivity;
import com.bee.user.ui.mine.MineFragment;
import com.bee.user.ui.nearby.NearbyFragment;
import com.bee.user.utils.DeviceUtils;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LogUtil;
import com.bee.user.utils.ToastUtil;
import com.bee.user.utils.sputils.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.huaxiafinance.lc.bottomindicator.IOnTab3Click;
import com.huaxiafinance.lc.bottomindicator.IconTabPageIndicator;
import com.huaxiafinance.lc.bottomindicator.viewpager.CustomViewPager;
import com.mobile.auth.gatewayauth.AuthRegisterViewConfig;
import com.mobile.auth.gatewayauth.AuthRegisterXmlConfig;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.CustomInterface;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.PreLoginResultListener;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.mobile.auth.gatewayauth.ui.AbstractPnsViewDelegate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * ???????????????2020/8/19
 * ???????????? ????????????
 * ???????????????app??????
 */
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, IOnTab3Click {

    private int mOldScreenOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;

    private MineFragment mineFragment;

    @BindView(R.id.tab_indicator)
    IconTabPageIndicator mIndicator;
    @BindView(R.id.view_pager)
    CustomViewPager mViewPager;

    @BindView(R.id.fl_content)
    public FrameLayout fl_content;

    private ArrayList<Fragment> fragments;
    private boolean mCanOneKeyLogin = true;

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

        EventBus.getDefault().register(this);

        mineFragment = new MineFragment();
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance(0));
        fragments.add(NearbyFragment.newInstance());
        fragments.add(new ChartFragment());
        fragments.add(mineFragment);

        MainAdapter myAdapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setCanScroll(false);
        mViewPager.setAdapter(myAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size() - 1);
        mIndicator.setOnPageChangeListener(this);
        mIndicator.setViewPager(mViewPager);
        mIndicator.setOnTab3ClickListener(this);

        onPageSelected(0);


//        if(!SPUtils.geTinstance().isLogin()){
            initLogins();
//        }
        initLocations();
        getAppUpdateInfo();
        onLogin();
    }

    /**
     * ????????????????????????
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
                        if (appUpdateInfoBean != null) {
                            Integer isForceUpdate = appUpdateInfoBean.getIsForceUpdate();//??????????????????(0:???,1:???)
                            // TODO: 2021/6/22 ?????????????????????????????????????????????UI
                            startUpdate(appUpdateInfoBean.getUrl());
                        }
                    }
                });
    }

    /**
     * ????????????
     *
     * @param url
     */
    private void startUpdate(String url) {
        Intent intent = new Intent(this, CheckUpdateService.class);
        intent.putExtra("url", url);
        CheckUpdateService.enqueueWork(this, intent);
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


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
//                status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
                break;
            case 1:
            case 2:
//                status_bar.setBackgroundResource(R.color.white);
                break;
            case 3:
//                status_bar.setBackgroundResource(R.drawable.btn_gradient_yellow);
                break;
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onTab3Click() {

        return false;
    }

    private static final int SERVICE_TYPE_AUTH = 1;//????????????
    private static final int SERVICE_TYPE_LOGIN = 2;//????????????
    private String token;
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
                        if (null != tokenRet) {
                            String code = tokenRet.getCode();
                            if (("600024").equals(code)) {
                                LogUtil.e("??????????????????:\n" + ret);
                            } else if (("600001").equals(code)) {
                                LogUtil.e("?????????????????????:\n" + ret);
                            } else if (("600000").equals(code)) {
                                token = tokenRet.getToken();
                                mAlicomAuthHelper.quitLoginPage();
                                LogUtil.e("??????token??????:\n" + ret);

//                            ????????????????????????
                                Map<String, String> map = new HashMap<>();
                                map.put("accessToken", token);
                                map.put("osType", "Android");

                                Api.getClient(HttpRequest.baseUrl_user).login(Api.getRequestBody(map))
                                        .subscribeOn(Schedulers.io())//???????????? ???????????????io??????
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new BaseSubscriber<String>() {
                                            @Override
                                            public void onSuccess(String token) {
                                                SPUtils.geTinstance().setLoginCache(null);
                                                SPUtils.geTinstance().setToken(token);
                                                onLogin();//TODO
                                            }
                                        });


                            }
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
                        LogUtil.e("??????:\n" + ret);
//                        mAlicomAuthHelper.quitLoginPage();
                    }
                });
                TokenRet tokenRet = null;
                try {
                    tokenRet = JSON.parseObject(ret, TokenRet.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (null != tokenRet) {
                    String code = tokenRet.getCode();
                    if ("600008".equals(code) ||
                            "600002".equals(code) ||
                            "600005".equals(code) ||
                            "600007".equals(code) ||
                            "600011".equals(code) ||
                            "600015".equals(code) ||
                            "600021".equals(code)) {
//                                ??????????????????
                        mCanOneKeyLogin = false;

                    }
//
//                    try {
//                        if ("600008".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "???????????????????????????");
//                        } else if ("600002".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "?????????????????????,???????????????????????????");
//                        } else if ("600005".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "?????????????????????,???????????????????????????");
//                        } else if ("600007".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "????????????sim???,???????????????????????????");
//                        } else if ("600011".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "??????token??????,???????????????????????????");
//                        } else if ("600015".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "????????????,???????????????????????????");
//                        } else if ("600021".equals(code)) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code + "??????????????????????????????????????????,???????????????????????????");
//                        } else if (code.startsWith("6")) {
//                            ToastUtil.ToastShort(MainActivity.this, "code==" + code);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }


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
         *   ???????????????????????? SERVICE_TYPE_LOGIN
         *   ???????????????????????? SERVICE_TYPE_AUTH
         */
        mAlicomAuthHelper.checkEnvAvailable(SERVICE_TYPE_LOGIN);


        /**
         * ????????????????????????
         */
        mAlicomAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, JSONObject jsonObj) {
                Log.e("authSDK", "OnUIControlClick:code=" + code + ", jsonObj=" + (jsonObj == null ? "" : jsonObj.toJSONString()));
//                ????????????
                if ("700002".equals(code)) {


                } else if ("700001".equals(code)) {
//                ?????????????????????


                }

            }
        });

//        ?????????
        mAlicomAuthHelper.accelerateLoginPage(5000, new PreLoginResultListener() {
            @Override
            public void onTokenSuccess(final String vendor) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e(vendor + "??????????????????");
                    }
                });
            }

            @Override
            public void onTokenFailed(final String vendor, final String ret) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.e(vendor + "???????????????:\n" + ret);
                    }
                });
            }
        });
    }

    private void configLoginTokenPort() {
        initDynamicView();
        mAlicomAuthHelper.removeAuthRegisterXmlConfig();
        mAlicomAuthHelper.removeAuthRegisterViewConfig();


//        ????????????
        mAlicomAuthHelper.addAuthRegistViewConfig("login_other", new AuthRegisterViewConfig.Builder()
                .setView(otherLogin)
                .setRootViewId(AuthRegisterViewConfig.RootViewId.ROOT_VIEW_ID_BODY)
                .setCustomInterface(new CustomInterface() {
                    @Override
                    public void onClick(Context context) {
                        startActivity(new Intent(MainActivity.this, CodeLoginActivity.class));
                        mAlicomAuthHelper.quitLoginPage();
                    }
                }).build());

//        ??????????????????????????????
        mAlicomAuthHelper.addAuthRegisterXmlConfig(new AuthRegisterXmlConfig.Builder()
                .setLayout(R.layout.item_onekey_login_bottom, new AbstractPnsViewDelegate() {
                    @Override
                    public void onViewCreated(View view) {
                        view.findViewById(R.id.iv_weixin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.ToastShortFromNet("????????????");
                            }
                        });

                        view.findViewById(R.id.iv_weibo).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtil.ToastShortFromNet("????????????");
                            }
                        });
                    }
                })
                .build());

        int authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        if (Build.VERSION.SDK_INT == 26) {
            authPageOrientation = ActivityInfo.SCREEN_ORIENTATION_BEHIND;
        }
        mAlicomAuthHelper.setAuthUIConfig(new AuthUIConfig.Builder()

//                ???????????????
                .setStatusBarColor(Color.WHITE)
//                .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
//                .setStatusBarHidden(false)
                .setLightColor(true)
                .setNavText("")
                .setNavColor(getResources().getColor(R.color.white))
                .setNavReturnImgPath("icon_guanbi")
                .setNavTextSize(18)
                .setNavReturnScaleType(ImageView.ScaleType.CENTER_INSIDE)

//                ???????????????????????????
                .setWebNavColor(getResources().getColor(R.color.white))
                .setWebNavTextColor(getResources().getColor(R.color.color_282525))
                .setWebNavReturnImgPath("icon_back_anse")
                .setWebViewStatusBarColor(getResources().getColor(R.color.color_FF6200))

//                ??????LOGO
                .setLogoOffsetY(30)
                .setLogoImgPath("logo_login")
                .setLogoScaleType(ImageView.ScaleType.CENTER_INSIDE)
                .setLogoHeight(-2)
                .setLogoWidth(-2)

//                LOGO????????????
                .setSloganOffsetY(140)
                .setSloganText("??????????????????????????????")
                .setSloganTextColor(getResources().getColor(R.color.color_7C7877))
                .setSloganTextSize(12)

                //                ???????????????
                .setNumberColor(getResources().getColor(R.color.color_282525))
                .setNumberSize(30)
                .setNumFieldOffsetY(170)

//                ????????????
                .setLogBtnText("?????????????????????")
                .setLogBtnTextColor(getResources().getColor(R.color.white))
                .setLogBtnTextSize(17)
                .setLogBtnBackgroundPath("btn_gradient_yellow_round")
                .setLogBtnHeight(40)
                .setLogBtnMarginLeftAndRight(42)
                .setLogBtnWidth(290)
                .setLogBtnOffsetY(250)

//                ??????????????????????????????
                .setSwitchAccHidden(true)

//                ??????????????????
                .setPrivacyOffsetY(370)
                .setPrivacyMargin(55)
                .setAppPrivacyOne("????????????????????????", HttpRequest.xieyi_regist)
                .setAppPrivacyTwo("??????????????????", HttpRequest.xieyi_yinsi)
                .setAppPrivacyColor(getResources().getColor(R.color.color_7C7877), getResources().getColor(R.color.color_3e7dfb))
                .setPrivacyState(false)
                .setCheckboxHidden(true)
                .setPrivacyBefore("?????????????????????????????????????????????????????????????????????????????????")
                .setProtocolGravity(Gravity.LEFT)
                .setVendorPrivacyPrefix("???")
                .setVendorPrivacySuffix("???")

//                ????????????
                .setAuthPageActIn("next_enter_in", "next_exit_out")
                .setAuthPageActOut("pre_enter_in", "pre_exit_out")

                .setScreenOrientation(authPageOrientation)
                .create());
    }


    private TextView otherLogin;

    //    ????????????????????????????????????
    private void initDynamicView() {

        otherLogin = new TextView(getApplicationContext());
        RelativeLayout.LayoutParams mLayoutParams2 = new RelativeLayout.LayoutParams((int) DisplayUtil.dp2px(this, 290), (int) DisplayUtil.dp2px(this, 40));
        mLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        mLayoutParams2.setMargins((int) DisplayUtil.dp2px(this, 42), (int) DisplayUtil.dp2px(this, 315), (int) DisplayUtil.dp2px(this, 42), 0);
        otherLogin.setText("???????????????????????????");
        otherLogin.setGravity(Gravity.CENTER);
        otherLogin.setTextColor(getResources().getColor(R.color.color_FF6200));
        otherLogin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.0F);
        otherLogin.setLayoutParams(mLayoutParams2);
        otherLogin.setBackgroundResource(R.drawable.btn_stroke_yellow);

    }


    private AMapLocationClient mLocationClient;
    //???????????????????????????
    //????????????????????????
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {

            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //??????????????????amapLocation?????????????????????
                    LogUtil.d("location==" + amapLocation.toStr());
                    SPUtils.geTinstance().setLocation(amapLocation);


                    EventBus.getDefault().post(new LocationChangedEvent());
                    HomeFragment fragment = (HomeFragment) fragments.get(0);
                    fragment.onLocationChanged();

                } else {
                    //???????????????????????????ErrCode????????????????????????????????????????????????errInfo???????????????????????????????????????
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
            closeLoadingDialog();
        }
    };


    private void initLocations() {

        //???????????????
        try {
            mLocationClient = new AMapLocationClient(getApplicationContext());

//????????????????????????
            mLocationClient.setLocationListener(mAMapLocationListener);

            AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
            //?????????????????????AMapLocationMode.Hight_Accuracy?????????????????????
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            //??????????????????,????????????,?????????2000ms?????????1000ms???
//        mLocationOption.setInterval(1000);

            //???????????????????????????
//??????????????????false???
            mLocationOption.setOnceLocation(true);
            //????????????3s???????????????????????????????????????
//??????setOnceLocationLatest(boolean b)?????????true??????????????????SDK???????????????3s?????????????????????????????????????????????????????????true???setOnceLocation(boolean b)????????????????????????true???????????????????????????false???
            mLocationOption.setOnceLocationLatest(true);

            mLocationClient.setLocationOption(mLocationOption);
            //????????????
//        mLocationClient.startLocation();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent event) {

        if (MainEvent.TYPE_login == event.TYPE) {
            if (mCanOneKeyLogin) {
                configLoginTokenPort();

                mAlicomAuthHelper.getLoginToken(MainActivity.this, 5000);
            } else {
                startActivity(new Intent(MainActivity.this, CodeLoginActivity.class));
            }
        } else if (MainEvent.TYPE_set_index == event.TYPE) {
            onPageSelected(event.index);
            mIndicator.setCurrentItem(event.index);

        } else if (MainEvent.TYPE_reLocation == event.TYPE) {

//            showLoadingDialog();
//            mLocationClient.stopLocation();
//                        ????????????
            mLocationClient.startLocation();
        } else if (MainEvent.TYPE_reset_Location == event.TYPE) {
            HomeFragment fragment = (HomeFragment) fragments.get(0);
            fragment.onLocationChanged(event.locationInfo.name);
        } else if(MainEvent.TYPE_reset_Location_from_SelectLocationActivity == event.TYPE){
            HomeFragment fragment = (HomeFragment) fragments.get(0);
            fragment.onLocationChanged();
        }

    }

    private void onLogin() {
        Api.getClient(HttpRequest.baseUrl_member).getUserInfo()
                .subscribeOn(Schedulers.io())//???????????? ???????????????io??????
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<UserBean>() {
                    @Override
                    public void onSuccess(UserBean str) {
                        SPUtils.geTinstance().setLoginCache(str);
                        mineFragment.setUserDatas();
                        mineFragment.getMiLiDatas();
                    }

                    @Override
                    public void onFail(String fail) {
                        super.onFail(fail);
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReflushEvent(ReflushEvent event) {
        switch (event.type) {
            case ReflushEvent.TYPE_REFLUSH_MILI:
                mineFragment.getMiLiDatas();
                break;
            case ReflushEvent.TYPE_REFLUSH_EXIT_LOGIN:
                mineFragment.setUserDatas();
                break;
            case ReflushEvent.TYPE_REFLUSH_LOGIN:
                SPUtils.geTinstance().setToken(event.data);
                onLogin();
                break;
            default:
                break;
        }
    }

    public View getAddChartAnimatorEndView(){
        return mIndicator.mTabLayout.getChildAt(2);
    }
}