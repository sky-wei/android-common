/*
 * Copyright (c) 2017 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.common.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sky on 2016/11/25.
 */

public class ConversionUtils {

    private static final String TAG = "ConversionUtils";

    public static final DecimalFormat SIMPLE_DECIMAL_FORMAT = new DecimalFormat("0.00");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String toString(BigDecimal bigDecimal) {
        return toString(bigDecimal, "0");
    }

    public static String toString(BigDecimal bigDecimal, String defaultValue) {
        return bigDecimal == null ? defaultValue : bigDecimal.toString();
    }

    public static String toString(String value, String defaultValue) {
        return TextUtils.isEmpty(value) ? defaultValue : value;
    }

    public static String toString(String value) {
        return toString(value, "");
    }

    public static String toString(Date date) {
        return toString(SIMPLE_DATE_FORMAT, date);
    }

    public static String toString(SimpleDateFormat format, Date date) {

        if (format == null || date == null) return "";

        return format.format(date);
    }

    public static String toDateString(long time) {
        return toString(new Date(time));
    }

    public static String toDecimalString(double value) {
        return SIMPLE_DECIMAL_FORMAT.format(value);
    }

    public static String toDecimalString(long value) {
        return SIMPLE_DECIMAL_FORMAT.format(value);
    }

    public static BigDecimal toBigDecimal(BigDecimal decimal) {
        if (decimal == null) return BigDecimal.ZERO;
        return decimal;
    }

    public static BigDecimal parseBigDecimal(String value) {

        BigDecimal result = BigDecimal.ZERO;

        if (!TextUtils.isEmpty(value)) {
            try {
                result = new BigDecimal(value);
            } catch (NumberFormatException e) {
                Alog.e(TAG, "NumberFormatException", e);
            }
        }
        return result;
    }

    public static byte parseByte(String value) {

        byte result = 0;

        if (!TextUtils.isEmpty(value)) {
            try {
                result = Byte.parseByte(value);
            } catch (NumberFormatException e) {
                Alog.e(TAG, "NumberFormatException", e);
            }
        }
        return result;
    }

    public static int parseInt(String value) {

        int result = 0;

        if (!TextUtils.isEmpty(value)) {
            try {
                result = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                Alog.e(TAG, "NumberFormatException", e);
            }
        }
        return result;
    }


    public static long parseLong(String value) {

        long result = 0L;

        if (!TextUtils.isEmpty(value)) {
            try {
                result = Long.parseLong(value);
            } catch (NumberFormatException e) {
                Alog.e(TAG, "NumberFormatException", e);
            }
        }
        return result;
    }

    public static float parseFloat(String value) {

        float result = 0.0F;

        if (!TextUtils.isEmpty(value)) {
            try {
                result = Float.parseFloat(value);
            } catch (NumberFormatException e) {
                Alog.e(TAG, "NumberFormatException", e);
            }
        }
        return result;
    }

    public static double parseDouble(String value) {

        double result = 0.0D;

        if (!TextUtils.isEmpty(value)) {
            try {
                result = Double.parseDouble(value);
            } catch (NumberFormatException e) {
                Alog.e(TAG, "NumberFormatException", e);
            }
        }
        return result;
    }

    public static boolean booleanValue(Boolean value, boolean defaultValue) {
        return value != null ? value.booleanValue() : defaultValue;
    }

    public static boolean booleanValue(Boolean value) {
        return booleanValue(value, false);
    }

    public static byte byteValue(Byte value, byte defaultValue) {
        return value != null ? value.byteValue() : defaultValue;
    }

    public static byte byteValue(Byte value) {
        return byteValue(value, (byte) 0);
    }

    public static int intValue(Integer value, int defaultValue) {
        return value != null ? value.intValue() : defaultValue;
    }

    public static int intValue(Integer value) {
        return intValue(value, 0);
    }

    public static int intValue(Number value, int defaultValue) {
        return value != null ? value.intValue() : defaultValue;
    }

    public static int intValue(Number value) {
        return intValue(value, 0);
    }

    public static long longValue(Long value, long defaultValue) {
        return value != null ? value.longValue() : defaultValue;
    }

    public static long longValue(Long value) {
        return longValue(value, 0L);
    }

    public static float floatValue(Float value, float defaultValue) {
        return value != null ? value.floatValue() : defaultValue;
    }

    public static float floatValue(Float value) {
        return floatValue(value, 0.0F);
    }

    public static double doubleValue(Double value, double defaultValue) {
        return value != null ? value.doubleValue() : defaultValue;
    }

    public static double doubleValue(Double value) {
        return doubleValue(value, 0.0D);
    }

    public static double doubleValue(Number value, double defaultValue) {
        return value != null ? value.doubleValue() : defaultValue;
    }

    public static double doubleValue(Number value) {
        return doubleValue(value, 0.0D);
    }
}
