package com.sky.android.core.dialog

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sky.android.common.util.ToastUtil

/**
 * Created by sky on 2020-12-29.
 */
abstract class BaseDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(inflater, container)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化View
        initView(view, savedInstanceState, arguments)
    }

    /**
     * 创建View
     * @param inflater
     * @param container
     * @return
     */
    protected open fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View {
        return inflater.inflate(layoutId, container, false)
    }

    /**
     * 初始化view
     * @param view
     * @param savedInstanceState
     * @param args
     */
    protected open fun initView(
        view: View,
        savedInstanceState: Bundle?,
        args: Bundle?
    ) {
        // 初始化View
        initView(view, args)
    }

    /**
     * 获取布局id
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 初始化View
     * @param view
     * @param args
     */
    protected abstract fun initView(view: View, args: Bundle?)

    override fun getContext(): Context {
        return activity!!
    }

    val applicationContext: Context
        get() = context.applicationContext

    val application: Application
        get() = activity!!.application

    /**
     * 显示提示消息
     * @param msg
     */
    fun showMessage(msg: String) {
        ToastUtil.show(msg)
    }
}