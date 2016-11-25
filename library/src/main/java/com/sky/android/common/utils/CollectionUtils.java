package com.sky.android.common.utils;

import java.util.Collection;

/**
 * Created by sky on 2016/11/25. <br/>
 *
 * 其他集合工具类：{@link java.util.Arrays}, {@link java.util.Collections}
 */

public class CollectionUtils {

    public static boolean isEmpty(Collection collection) {
        return collection == null ? true : collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return !isEmpty(collection);
    }

    public static int size(Collection collection) {
        return collection == null ? 0 : collection.size();
    }
}
