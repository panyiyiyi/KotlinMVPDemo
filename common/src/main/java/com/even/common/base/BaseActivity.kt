package com.even.common.base

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
            mPresenter?.RxTag = mRxTag
        }
    }

    override fun showLoading() {
        if (!this.isFinishing) {
            if (null != dialog && dialog!!.isShowing) {
                return
            }
            dialog = DialogUtils.showLoadingDialog(this, "")
            dialog?.show()
        }
    }

    override fun showLoading(title: String) {
        if (!this.isFinishing) {

            if (null != dialog && dialog!!.isShowing) {
                return
            }
            dialog = DialogUtils.showLoadingDialog(this, title)
            dialog?.show()
        }
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
    open fun requestPermissions(
        permissions: MutableList<String>,
        callBacks: OnPermissionCallBacks
    ) {
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
            ActivityCompat.requestPermissions(
                this,
                permissions.toTypedArray(),
                REQ_PERMISSION_RECODE
            )
        } else {
            permissionCallBacks?.onSuccessResult()
        }
    }

    /**
     * 申请悬浮窗权限
     */
    open fun requestOverlays() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(activity)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.fromParts("package", activity.packageName, null)
                startActivityForResult(intent, REQ_OVERLAY_PERMISSION)
            } else {
                resultOverlaysPermission(true)
            }
        } else {
            resultOverlaysPermission(true)
        }
    }

    /**
     * 申请悬浮窗权限结果
     * @param isAgree 是否同意
     */
    open fun resultOverlaysPermission(isAgree: Boolean) {

    }

    open fun startActivity(clz: Class<BaseActivity>) {
        startActivity(Intent(this, clz))
    }


    private fun <T : BasePresenter<BaseView>> getLogicImpl(): T {
        //执行到这里一定不为空
        return LogicProxy.bind(getLogicClazz()!!, this)
    }

    abstract fun getContentView(): Int

    abstract fun initView()

    abstract fun getLogicClazz(): Class<*>?

    open fun initData() {}

    protected abstract fun getTitleBarView(): Int

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (REQ_PERMISSION_RECODE == requestCode) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(activity)) {
                    resultOverlaysPermission(true)
                } else {
                    resultOverlaysPermission(false)
                }
            } else {
                resultOverlaysPermission(true)
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
        permissionCallBacks = null
    }

    companion object {
        //权限请求码
        const val REQ_PERMISSION_RECODE = 0x123

        //悬浮窗
        const val REQ_OVERLAY_PERMISSION = 0x124
    }

}