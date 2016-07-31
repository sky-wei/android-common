package com.sky.android.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by starrysky on 16-7-31.
 *
 * 反射的工具类
 */
public class ReflectUtils {

    public final static String TAG = ReflectUtils.class.getSimpleName();

    public static Field findField(Class classz, String name) {

        if (classz == null || name == null) return null;

        try {
            Field field = classz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            Alog.e(TAG, "NoSuchFieldException", e);
        }
        return findField(classz.getSuperclass(), name);
    }

    public static Object getFieldValue(Object object, String name) {
        return ReflectUtils.getValueQuietly(ReflectUtils.findField(object.getClass(), name), object);
    }

    public static void setFieldValue(Object object, String name, Object value) {
        ReflectUtils.setValueQuietly(ReflectUtils.findField(object.getClass(), name), object, value);
    }

    public static Object getValueQuietly(Field field, Object object) {

        if (field == null) return null;

        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            Alog.e(TAG, "IllegalAccessException", e);
        }
        return null;
    }

    public static void setValueQuietly(Field field, Object object, Object value) {

        if (field == null) return ;

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            Alog.e(TAG, "IllegalAccessException", e);
        }
    }

    public static Method findMethod(Class classz, String name, Class<?>... parameterTypes) {

        if (classz == null || name == null) return null;

        try {
            Method method = classz.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException e) {
            Alog.e(TAG, "NoSuchMethodException", e);
        }
        return findMethod(classz.getSuperclass(), name, parameterTypes);
    }

    public static Object invoke(Object receiver, Class classz, String name, Class[] parameterTypes, Object[] args) throws InvocationTargetException, IllegalAccessException {

        if (classz == null || name == null) return null;

        Method method = findMethod(classz, name, parameterTypes);

        return method == null ? null : method.invoke(receiver, args);
    }

    public static Object invokeQuietly(Object receiver, Class classz, String name, Class[] parameterTypes, Object[] args) {

        try {
            return invoke(receiver, classz, name, parameterTypes, args);
        } catch (Exception e) {
            Alog.e(TAG, "Exception", e);
        }
        return null;
    }

    public static Object invokeQuietly(Object receiver, String name, Class[] parameterTypes, Object[] args) {

        if (receiver == null) return null;

        return invokeQuietly(receiver, receiver.getClass(), name, parameterTypes, args);
    }

    public static Object invokeQuietly(Class classz, String name, Class[] parameterTypes, Object[] args) {
        return invokeQuietly(null, classz, name, parameterTypes, args);
    }

    public static Object invokeQuietly(Class classz, String name) {
        return invokeQuietly(null, classz, name, null, null);
    }

    public static Object invokeQuietly(Object receiver, String name) {
        return invokeQuietly(receiver, name, null, null);
    }

    public static Object invokeQuietly(Object receiver, Class classz, String name) {
        return invokeQuietly(receiver, classz, name, null, null);
    }

    public static Object invokeQuietly(Method method, Object receiver, Object[] args) {

        if (method == null) return null;

        try {
            return method.invoke(receiver, args);
        } catch (Exception e) {
            Alog.e(TAG, "Exception", e);
        }
        return null;
    }

    public static Class loadClassQuietly(String className) {

        if (className == null) return null;

        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (Exception e) {
            Alog.e(TAG, "Exception", e);
        }
        return null;
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
