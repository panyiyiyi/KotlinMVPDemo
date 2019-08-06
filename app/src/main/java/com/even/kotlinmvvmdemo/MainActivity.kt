package com.even.kotlinmvvmdemo

import android.Manifest
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.even.common.base.BaseActivity
import com.even.common.beans.DialogBean
import com.even.common.impl.OnDialogConfirmClick
import com.even.common.impl.OnPermissionCallBacks
import com.even.common.utils.DialogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
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

            requestPermissions(
                mutableListOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                object : OnPermissionCallBacks {
                    override fun onFailResult(permissionDenieds: Array<String>) {
                        permissionDenieds.forEach {
                            if (Manifest.permission.CAMERA == it) {
                                Toast.makeText(this@MainActivity, "请到权限管理开启摄像机权限", Toast.LENGTH_LONG).show()
                                return
                            }
                            if (Manifest.permission.READ_EXTERNAL_STORAGE == it) {
                                Toast.makeText(this@MainActivity, "请到权限管理开启读取SD卡权限", Toast.LENGTH_LONG).show()
                                return
                            }
                        }
                    }

                    override fun onSuccessResult() {
                        Toast.makeText(this@MainActivity, "申请成功", Toast.LENGTH_LONG).show()
                    }
                })

        }
    }

    override fun getLogicClazz(): Class<*>? = null

}
