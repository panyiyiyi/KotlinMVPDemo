package com.even.common.base

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.even.common.base.model.LogicProxy
import com.even.common.impl.OnPermissionCallBack
import com.even.common.impl.OnPermissionCallBacks
import com.even.common.request.utils.RxJavaManagerUtils
import com.even.common.utils.ActivityManagerUtils
import com.even.common.utils.DialogUtils

/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    companion object {
        //权限请求码
        const val PERMISSION_RECODE = 0x123
    }

    /**
     * 用来存放网络请求的Key
     */
    lateinit var mRxTag: String
    var mPresenter: BasePresenter<BaseView>? = null
    lateinit var activity: BaseActivity

    private var dialog: Dialog? = null
    /**
     * 权限回调
     */
    private var permissionCallBacks: OnPermissionCallBacks? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (useDefaultTitleBar()) {
            val linearLayout = LinearLayout(this)
            val titleBar = layoutInflater.inflate(getTitleBarView(), null)
            linearLayout.addView(
                titleBar, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(
                layoutInflater.inflate(getContentView(), null), LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            )
            setContentView(linearLayout)
        } else {
            setContentView(getContentView())
        }

        activity = this
        mRxTag = packageName.plus(".").plus(javaClass.simpleName)

        ActivityManagerUtils.addActivity(this)
        ARouter.getInstance().inject(this)
        this.initPresenter()
        this.initView()
        this.initData()

    }

    private fun initPresenter() {
        if (getLogicClazz() != null) {
            mPresenter = getLogicImpl()
            mPresenter?.setRxTag(mRxTag)
        }
    }

    override fun showLoading() {
        if (null != dialog && dialog!!.isShowing) {
            return
        }
        dialog = DialogUtils.showLoadingDialog(this, "")
        dialog?.show()
    }

    override fun showLoading(title: String) {
        if (null != dialog && dialog!!.isShowing) {
            return
        }
        dialog = DialogUtils.showLoadingDialog(this, title)
        dialog?.show()
    }

    override fun hideLoading() {
        if (null != dialog) {
            dialog?.dismiss()
        }
    }

    /**
     * 是否使用默认标题
     */
    open fun useDefaultTitleBar(): Boolean {
        return true
    }

    /**
     * 单个权限申请
     */
    @Synchronized
    open fun requestPermission(permission: String, callBack: OnPermissionCallBack) {
        requestPermissions(mutableListOf(permission), object : OnPermissionCallBacks {
            override fun onFailResult(permissionDenieds: Array<String>) {
                callBack.onResult(false)
            }

            override fun onSuccessResult() {
                callBack.onResult(true)
            }
        })

    }

    /**
     * 多权限申请
     */
    @Synchronized
    open fun requestPermissions(permissions: MutableList<String>, callBacks: OnPermissionCallBacks) {
        this.permissionCallBacks = callBacks
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //6.0以下
            permissionCallBacks?.onSuccessResult()
            return
        }
        var isRequest = false
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED) {
                //有一个没有通过就发起请求
                isRequest = true
            } else {
                permissions.remove(it)
            }
        }
        if (isRequest) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), PERMISSION_RECODE)
        } else {
            permissionCallBacks?.onSuccessResult()
        }

    }

    open fun startActivity(clz: Class<BaseActivity>) {
        startActivity(Intent(this, clz))
    }


    private fun <T : BasePresenter<BaseView>> getLogicImpl(): T {
        //执行到这里一定不为空
        return LogicProxy.bind(getLogicClazz()!!, this)
    }

    protected abstract fun getContentView(): Int

    protected abstract fun initView()

    protected abstract fun getLogicClazz(): Class<*>?

    protected fun initData() {}

    protected abstract fun getTitleBarView(): Int

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (PERMISSION_RECODE == requestCode) {
            var deniedLists = mutableListOf<String>()

            for (index in grantResults.indices) {
                if (PackageManager.PERMISSION_DENIED == grantResults[index]) {
                    deniedLists.add(permissions[index])
                }
            }
            if (deniedLists.size > 0) {
                permissionCallBacks?.onFailResult(deniedLists.toTypedArray())
            } else {
                permissionCallBacks?.onSuccessResult()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //防止结束当前界面立马进入本界面请求被取消
        RxJavaManagerUtils.cancelDisposable(mRxTag)

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManagerUtils.closeActivity(this)
        dialog?.let { DialogUtils.closeDialog(it) }
        mPresenter?.detachView()
    }

}