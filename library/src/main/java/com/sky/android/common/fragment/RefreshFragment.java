package com.sky.android.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sky.android.common.R;
import com.sky.android.common.base.BaseFragment;
import com.sky.android.common.helper.PromptHelper;

/**
 * Created by starrysky on 16-8-2.
 */
public abstract class RefreshFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected PromptHelper mPromptHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_fragment_refresh, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 提示
        View incudePrompt = view.findViewById(R.id.incude_prompt);
        mPromptHelper = new PromptHelper(incudePrompt, R.id.tv_prompt_content);

        // RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_content);
        mRecyclerView.setHasFixedSize(true);

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources();
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    protected void showLoading(int text) {
        mPromptHelper.show(text);
    }

    protected void hideLoading() {
        mPromptHelper.hide();
    }

    protected void forceRefreshing() {

        if (mSwipeRefreshLayout.isRefreshing()) return ;

        // 显示加载进度
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
        mSwipeRefreshLayout.setRefreshing(true);
    }

    protected void cancelRefreshing() {

        if (!mSwipeRefreshLayout.isRefreshing()) return ;

        mSwipeRefreshLayout.setRefreshing(false);
    }
}
