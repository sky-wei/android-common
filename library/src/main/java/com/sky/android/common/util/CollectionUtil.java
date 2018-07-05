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

package com.sky.android.common.util;

import java.util.Collection;

/**
 * Created by sky on 2016/11/25. <br/>
 *
 * 其他集合工具类：{@link java.util.Arrays}, {@link java.util.Collections}
 */

public class CollectionUtil {

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
