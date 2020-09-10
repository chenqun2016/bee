package com.bee.user.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/09  15：29
 * 描述：
 */
public class CommonUtil {

    //校验手机号码
    public static final boolean isMobileNoAll(CharSequence mobiles) {
//        Pattern p = Pattern.compile("^(([0-9]))\\d{10}$");
//        Matcher m = p.matcher(mobiles);
//        return m.matches();
        return !TextUtils.isEmpty(mobiles) && mobiles.length() == 11;
    }



    public static void showKeyBoard(Activity a) {
        View view = a.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
        }
    }
}
