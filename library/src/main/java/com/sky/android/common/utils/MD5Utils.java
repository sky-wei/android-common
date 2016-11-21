package com.sky.android.common.utils;

import android.text.TextUtils;

import java.security.MessageDigest;

/**
 * Created by starrysky on 16-8-2.
 */
public class MD5Utils {

    private static final String TAG = "MD5Utils";

    /**
     * Convert byte[] to hex string
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){

        if (src == null || src.length <= 0) return null;

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {

        if (TextUtils.isEmpty(hexString)) return null;

        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return bytes;
    }

    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String md5sum(String value) {
        return md5sum(value, "UTF-8");
    }

    public static String md5sum(String value, String charsetName) {

        if (TextUtils.isEmpty(value)) return null;

        try {
            return md5sum(value.getBytes(charsetName));
        } catch (Exception e) {
            Alog.e(TAG, "字符串编码异常", e);
        }
        return null;
    }

    public static String md5sum(byte[] value) {

        if (value == null) return null;

        try {
            // 计算MD5值信息
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(value);

            byte[] bytes = messageDigest.digest();

            return bytesToHexString(bytes);
        } catch (Exception e) {
            Alog.e(TAG, "处理MD5异常", e);
        }
        return null;
    }
}
