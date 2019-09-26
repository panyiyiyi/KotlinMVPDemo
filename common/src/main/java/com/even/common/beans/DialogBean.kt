package com.even.common.beans

import androidx.databinding.BaseObservable
import com.even.common.R
import com.even.common.utils.ApplicationUtils

/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 * 构建Dialog显示对象
 */
data class DialogBean(
    var title: String,//标题
    var msg: String,//消息
    var cancel: String,//取消文案
    var confirm: String,//确定文案
    var canCancel: Boolean//点击边框能否取消
) : BaseObservable() {
    /**
     * 只能设置title和msg对象
     */
    constructor(title: String, msg: String) : this(
        title,
        msg,
        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_cancel),
        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_confirm),
        false
    )

    /**
     * 设置能否点击外面取消弹窗
     */
    constructor(title: String, msg: String, canCancel: Boolean) : this(
        title,
        msg,
        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_cancel),
        ApplicationUtils.getInstance().applicationContext.getString(R.string.common_confirm),
        canCancel
    )

    /**
     * 只能确定对象
     */
    constructor(title: String, msg: String, confirm: String) : this(title, msg, "", confirm, false)

}