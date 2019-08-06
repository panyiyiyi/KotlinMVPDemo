package com.even.common.impl

import android.app.Dialog

/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 * 弹框确定按钮点击事件
 */
interface OnDialogConfirmClick {
    /**
     * inputText：有输入框时候使用
     */
    fun onConfirmClick(dialog: Dialog, inputText: String)
}