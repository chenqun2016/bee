package com.bee.user;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * Created by chenqun on 2017/6/12.
 * EditText 输入框 内容限制类 限制只能输入 数字和小数点
 */

public class TextNumRangeInputFilter implements InputFilter {

    // 只允许输入数字和小数点
    private static final String REGEX = "([0-9]|\\.)*";
    // 输入的最大金额
    private final float MAX_VALUE;
    // 小数点后的位数
    private final int POINTER_LENGTH;

    private static final String POINTER = ".";

    private static final String ZERO_ZERO = "00";

    public TextNumRangeInputFilter() {
        MAX_VALUE = 999999999.99f;
        POINTER_LENGTH = 2;
    }

    public TextNumRangeInputFilter(int maxValue, int pointLength) {
        MAX_VALUE = maxValue;
        POINTER_LENGTH = pointLength;
    }

    /**
     * @param source 新输入的字符串
     * @param start  新输入的字符串起始下标，一般为0
     * @param end    新输入的字符串终点下标，一般为source长度-1
     * @param dest   输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend   原内容终点坐标，一般为dest长度-1
     * @return 输入内容
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String sourceText = source.toString();
        String destText = dest.toString();

        // 新输入的字符串为空（删除剪切等）
        if (TextUtils.isEmpty(sourceText)) {
            return "";
        }

        // 拼成字符串
        String temp = destText.substring(0, dstart)
                + sourceText.substring(start, end)
                + destText.substring(dend, dest.length());

        // 纯数字加小数点
        if (!temp.matches(REGEX)) {
            return dest.subSequence(dstart, dend);
        }

        //最多12位数
        if (temp.length() >= 13) {
            return dest.subSequence(dstart, dend);
        }

        // 小数点的情况
        if (temp.contains(POINTER)) {
            // 第一位就是小数点
            if (temp.startsWith(POINTER)) {
                return dest.subSequence(dstart, dend);
            }
            // 不止一个小数点
            if (temp.indexOf(POINTER) != temp.lastIndexOf(POINTER)) {
                return dest.subSequence(dstart, dend);
            }
        }

        double sumText = Double.parseDouble(temp);
        if (sumText > MAX_VALUE) {
            // 超出最大值
            return dest.subSequence(dstart, dend);
        }

        // 有小数点的情况下
        if (temp.contains(POINTER)) {
            //验证小数点精度，保证小数点后只能输入两位
            if (!temp.endsWith(POINTER) && temp.split("\\.")[1].length() > POINTER_LENGTH) {
                return dest.subSequence(dstart, dend);
            }
        } else if (temp.startsWith(POINTER) || temp.startsWith(ZERO_ZERO)) {
            // 首位只能有一个0
            return dest.subSequence(dstart, dend);
        }


        return source;
    }
}