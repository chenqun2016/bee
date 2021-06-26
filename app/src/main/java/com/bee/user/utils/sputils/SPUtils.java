package com.bee.user.utils.sputils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.amap.api.location.AMapLocation;
import com.bee.user.BeeApplication;
import com.bee.user.Constants;
import com.bee.user.bean.UserBean;
import com.google.gson.Gson;

import java.util.Map;
import java.util.Set;


/**
 * 创建时间：2020/8/19
 * 编写人： 进京赶考
 * 功能描述：SharedPreference工具类
 */

public class SPUtils {

    private static volatile SPUtils sharedPreferencesUtils;

    private SharedPreferences share;
    private AMapLocation mLocation;


    public SPUtils(Context context) {
        init(context);
    }

    public static SPUtils geTinstance() {
        SPUtils utils = sharedPreferencesUtils;
        if (utils == null) {
            synchronized (SPUtils.class) {
                utils = sharedPreferencesUtils;
                if (null == utils) {
                    utils = new SPUtils(BeeApplication.getInstance());
                    sharedPreferencesUtils = utils;
                }
            }
        }
        return utils;
    }

    private void init(Context context) {
        if (null != context)
            share = new SecuritySharedPreference(context, "security_prefs");

//            share = context.getSharedPreferences("config", 0);
    }

    public void put(String key, String value) {
        share.edit().putString(key, value).apply();
    }

    public void put(String key, boolean value) {
        share.edit().putBoolean(key, value).apply();
    }

    public void put(String key, float value) {
        share.edit().putFloat(key, value).apply();
    }

    public void put(String key, int value) {
        share.edit().putInt(key, value).apply();
    }

    public void put(String key, long value) {
        share.edit().putLong(key, value).apply();
    }

    @SuppressLint("NewApi")
    public void put(String key, Set<String> value) {
        share.edit().putStringSet(key, value).apply();
    }


    public Map getAll() {
        return share.getAll();
    }

    public String get(String key, String defValue) {
        return share.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        return share.getInt(key, defValue);
    }

    public float get(String key, float defValue) {
        return share.getFloat(key, defValue);
    }

    public long get(String key, long defValue) {
        return share.getLong(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return share.getBoolean(key, defValue);
    }

    @SuppressLint("NewApi")
    public Set<String> get(String key, Set<String> defValues) {
        return share.getStringSet(key, defValues);
    }


    /**********************************************************/

    private String mUid;//用户uid
    private UserBean mUser;

    public boolean isLogin() {

        return !TextUtils.isEmpty(getUid());
    }

    public String getUid() {
        if (TextUtils.isEmpty(mUid)) {
            return get(Constants.UID, "");
        } else {
            return mUid;
        }

    }

    public void setUid(String uid) {
        mUid = uid;
        put(Constants.UID, uid);
    }


    public void setUserBean(UserBean value) {
        mUser = value;
        if (null != value) {
            share.edit().putString(Constants.USER_INFO, new Gson().toJson(value)).apply();
        } else {
            share.edit().putString(Constants.USER_INFO, "").apply();
        }
    }

    public UserBean getUserInfo() {
        if (null == mUser) {
            String json = share.getString(Constants.USER_INFO, "");
            if (!TextUtils.isEmpty(json)) {
                return new Gson().fromJson(json, UserBean.class);
            }
            return null;
        } else {
            return mUser;
        }
    }

    public void setLocation(AMapLocation value) {
        mLocation = value;
        if (null != value) {
            share.edit().putString(Constants.USER_LOCATION, new Gson().toJson(value)).apply();
        } else {
            share.edit().putString(Constants.USER_LOCATION, "").apply();
        }
    }

    public AMapLocation getLocation() {
        try {
            if (null == mLocation) {
                String json = share.getString(Constants.USER_LOCATION, "");
                if (!TextUtils.isEmpty(json)) {
                    return new Gson().fromJson(json, AMapLocation.class);
                }
                return null;
            } else {
                return mLocation;
            }
        }catch (Exception e){
            return null;
        }

    }


    public void setLoginCache(UserBean userBean) {
        setUserBean(userBean);
        if(null != userBean){
            setUid(userBean.id+"");
        }
    }


    private String mToken;//用户uid

    public String getToken() {
        if (TextUtils.isEmpty(mToken)) {
            return get(Constants.TOKEN, "");
        } else {
            return mToken;
        }

    }

    public void setToken(String token) {
        mToken = token;
        put(Constants.TOKEN, token);
    }

    public void setExitlogin(){
        mToken = "";
        mUser = null;
        mUid = "";

        setUserBean(null);
        setToken("");
        setUid("");
    }

}
