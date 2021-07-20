/*
 * Copyright (c) 2021 The sky Authors.
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

package com.sky.android.core.component;

import android.content.Context;

import androidx.annotation.NonNull;

import com.sky.android.core.interfaces.IComponent;
import com.sky.android.core.interfaces.IComponentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sky on 2021-07-20.
 */
final class ComponentManager implements IComponentManager {

    private final Map<Class<? extends IComponent>, IComponent> mComponentMap = new HashMap<>();
    private final List<IComponentManager.ComponentListener> mListeners = new ArrayList<>();

    private final Context mContext;
    private final IComponentManager.Factory mFactory;

    private ComponentManager(Builder builder) {
        mContext = builder.mContext;
        mFactory = builder.mFactory;
    }

    @Override
    public <T extends IComponent> T get(@NonNull Class<T> name) {

        IComponent component = mComponentMap.get(name);

        if (component == null) {
            synchronized (this) {
                component = mComponentMap.get(name);
                if (component == null) {
                    // 创建组件
                    component = mFactory.create(mContext, name);

                    if (component != null) {
                        // 添加并通知
                        component.initialize();
                        mComponentMap.put(name, component);
                        callCreateListener(component);
                    }
                }
            }
        }
        return name.cast(component);
    }

    @Override
    public List<IComponent> getAllComponent() {
        return new ArrayList<>(mComponentMap.values());
    }

    @Override
    public void remove(@NonNull Class<? extends IComponent> name) {

        IComponent component = mComponentMap.get(name);

        if (component != null) {
            // 先释放再通知
            component.release();
            callReleaseListener(component);
        }
    }

    @Override
    public boolean contains(@NonNull Class<? extends IComponent> name) {
        return mComponentMap.containsKey(name);
    }

    @Override
    public void release() {

        for (IComponent component : getAllComponent()) {
            // 先释放再通知
            component.release();
            callReleaseListener(component);
        }
        mComponentMap.clear();
    }

    @Override
    public void addListener(@NonNull ComponentListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void removeListener(@NonNull ComponentListener listener) {
        mListeners.remove(listener);
    }

    private void callCreateListener(IComponent component) {
        for (ComponentListener listener : mListeners) {
            listener.onCreate(component.getClass());
        }
    }

    private void callReleaseListener(IComponent component) {
        for (ComponentListener listener : mListeners) {
            listener.onRelease(component.getClass());
        }
    }

    public static class Builder {

        private final Context mContext;
        private IComponentManager.Factory mFactory;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setFactory(Factory factory) {
            mFactory = factory;
            return this;
        }

        public IComponentManager build() {
            return new ComponentManager(this);
        }
    }
}
