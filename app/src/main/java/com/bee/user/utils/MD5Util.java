package com.bee.user.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 - @Description: 
 - @Author:  bixueyun
 - @Time:  2021/6/24 下午4:31
 */
public class MD5Util {

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String md5(String input) throws NoSuchAlgorithmException {
        byte[] bytes = MessageDigest.getInstance("MD5").digest(input.getBytes());
        return printHexBinary(bytes);
    }

    private static String printHexBinary(byte[] data) {

        StringBuffer r = new StringBuffer(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xf]);
            r.append(hexCode[(b & 0xF)]);
        }

        return r.toString();
    }
}
