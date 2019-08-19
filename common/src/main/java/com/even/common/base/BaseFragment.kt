package com.even.common.base

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.even.common.base.model.LogicProxy
import com.even.common.utils.DialogUtils

/**
 * @author  Created by Even on 2019/8/6
 *  Email: emailtopan@163.com
 *  Fragment基类
 */
abstract class BaseFragment : Fragment(), BaseView {

    lateinit var mView: View
    lateinit var activity: BaseActivity

    var mPresenter: BasePresenter<BaseView>? = null
    private var dialog: Dialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(getContentView(), container, false)
        activity = getActivity() as BaseActivity
        return mView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPresenter()
        initView()
        initData()
    }


    fun startActivity(clz: Class<BaseActivity>) {
        startActivity(Intent(activity, clz))
    }

    override fun showLoading() {
        if (null != dialog && dialog!!.isShowing) {
            return
        }
        dialog = DialogUtils.showLoadingDialog(activity, "")
        dialog?.show()
    }

    override fun showLoading(title: String) {
        if (null != dialog && dialog!!.isShowing) {
            return
        }
        dialog = DialogUtils.showLoadingDialog(activity, title)
        dialog?.show()
    }

    override fun hideLoading() {
        if (null != dialog) {
            dialog?.dismiss()
        }
    }

    private fun initPresenter() {
        if (null != getLogicClazz()) {
            mPresenter = getLogicImpl()
        }
    }

    private fun <T : BasePresenter<BaseView>> getLogicImpl(): T {
        return LogicProxy.bind(getLogicClazz()!!, this)
    }

    abstract fun getContentView(): Int
    abstract fun getLogicClazz(): Class<*>?
    abstract fun initView()
    open fun initData() {}

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}