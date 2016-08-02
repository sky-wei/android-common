package com.sky.android.common.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class BaseFragment extends Fragment {

    public Context getContext() {
        return getActivity();
    }
}
