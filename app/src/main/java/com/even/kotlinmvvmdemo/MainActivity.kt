package com.even.kotlinmvvmdemo

import com.even.common.base.BaseActivity
import com.even.common.request.observer.BaseStringObserver
import com.even.common.request.observer.Transformer
import com.even.common.request.utils.RxHttpUtils
import com.even.common.utils.EventBusUtils
import com.even.common.utils.LogUtils
import com.even.common.utils.SpUtils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun getTitleBarView(): Int = R.layout.activity_main

    override fun getContentView(): Int = R.layout.activity_main

    override fun initView() {
        btn.setOnClickListener {
            //            DialogUtils.showMsgDialog(this, DialogBean("测试", "", true), object : OnDialogConfirmClick {
//                override fun onConfirmClick(dialog: Dialog, inputText: String) {
//
//                }
//            })

//            val showLoadingDialog = DialogUtils.showLoadingDialog(this, "测试中...")
//            showLoadingDialog.show()

//            requestPermissions(
//                mutableListOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
//                object : OnPermissionCallBacks {
//                    override fun onFailResult(permissionDenieds: Array<String>) {
//                        permissionDenieds.forEach {
//                            if (Manifest.permission.CAMERA == it) {
//                                Toast.makeText(this@MainActivity, "请到权限管理开启摄像机权限", Toast.LENGTH_LONG).show()
//                                return
//                            }
//                            if (Manifest.permission.READ_EXTERNAL_STORAGE == it) {
//                                Toast.makeText(this@MainActivity, "请到权限管理开启读取SD卡权限", Toast.LENGTH_LONG).show()
//                                return
//                            }
//                        }
//                    }
//
//                    override fun onSuccessResult() {
//                        Toast.makeText(this@MainActivity, "申请成功", Toast.LENGTH_LONG).show()
//                    }
//                })

            RxHttpUtils.createApi(ApiService::class.java)
                .getImageVerification()
                .compose(Transformer.switchSchedulers())
                .subscribe(object : BaseStringObserver(mRxTag) {
                    override fun doSubscriber(disposable: Disposable) {
                    }

                    override fun doFail(errorMsg: String) {
                        LogUtils.e(errorMsg)
                    }

                    override fun doNext(json: String) {
                        LogUtils.e(json)
                    }

                    override fun doCompleted() {
                        LogUtils.e("")
                    }
                })
        }
    }

    override fun getLogicClazz(): Class<*>? = null


}
