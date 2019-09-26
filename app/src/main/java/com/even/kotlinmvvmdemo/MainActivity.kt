package com.even.kotlinmvvmdemo

import android.app.Dialog
import com.even.common.base.BaseActivity
import com.even.common.beans.DialogBean
import com.even.common.impl.OnDialogConfirmClick
import com.even.common.utils.DialogUtils
import com.even.kotlinmvvmdemo.ui.presenters.MainPresenter
import com.even.kotlinmvvmdemo.ui.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {
    override fun getSuccess() {

    }

    override fun getTitleBarView(): Int = R.layout.item_title
    override fun getContentView(): Int = R.layout.activity_main

    override fun initView() {
//        GlideUtil.loadNet(iv, "https://wanandroid.com/blogimgs/60462c4c-0d82-41aa-b76d-0406c80fce31.png")
        btn.setOnClickListener {
//            (mPresenter as MainPresenter).getData2()


//            val navigation = ARouter.getInstance().build("/App/test").navigation()
//            LogUtils.e(navigation.toString())


            DialogUtils.showMsgDialog(this, DialogBean("测试", "", true), object : OnDialogConfirmClick {
                override fun onConfirmClick(dialog: Dialog, inputText: String) {

                }
            })

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

//            RxHttpUtils.createApi(ApiService::class.java)
//                .getImageVerification()
//                .compose(Transformer.switchSchedulers())
//                .subscribe(object : BaseStringObserver(mRxTag) {
//                    override fun doSubscriber(disposable: Disposable) {
//                    }
//
//                    override fun doFail(errorMsg: String) {
//                        LogUtils.e(errorMsg)
//                    }
//
//                    override fun doNext(json: String) {
//                        LogUtils.e(json)
//                    }
//
//                    override fun doCompleted() {
//                        LogUtils.e("")
//                    }
//                })


        }
    }

    override fun getLogicClazz(): Class<*> = MainView::class.java
    override fun initData() {
        super.initData()
        (mPresenter as MainPresenter).getData()
    }
}
