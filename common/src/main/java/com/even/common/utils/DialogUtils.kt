package com.even.common.utils

import android.app.Activity
import android.app.Dialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.even.common.BR
import com.even.common.R
import com.even.common.beans.DialogBean
import com.even.common.impl.OnDialogConfirmClick
import com.even.commonrv.utils.DisplayUtil

/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
object DialogUtils {
    //弹窗与屏幕宽占比
    private const val WIDTH_RATIO = 0.85
    //加载提示框与屏宽占比
    private const val LOADING_WIDTH_RATIO = 0.5
    //宽高比例
    private const val WIDTH_HEIGHT_RATIO = 3


    fun showLoadingDialog(activity: Activity, remind: String?): Dialog {
        val view = LayoutInflater.from(ApplicationUtils.getInstance().applicationContext)
            .inflate(R.layout.view_loading_dialog, null)
        if (!TextUtils.isEmpty(remind)) {
            view.findViewById<TextView>(R.id.tvRemind).text = remind
        }
        val dialog = Dialog(activity, R.style.AlertDialogStyle)
        dialog.setContentView(view)
        val width = DisplayUtil.getWindowWidth(activity) * LOADING_WIDTH_RATIO
        val height = width / WIDTH_HEIGHT_RATIO
        view.layoutParams = FrameLayout.LayoutParams(width.toInt(), height.toInt())
        return dialog
    }


    /**
     * Dialog弹窗显示通用方法
     */
    fun showMsgDialog(
        activity: Activity,
        dialogBean: DialogBean,
        click: OnDialogConfirmClick
    ) {
        var mBindView = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(ApplicationUtils.getInstance()?.applicationContext),
            R.layout.view_dialog, null, false
        )
        mBindView.setVariable(BR.data, dialogBean)

        val dialog = Dialog(activity, R.style.AlertDialogStyle)
        dialog.setContentView(mBindView.root)
        mBindView.root.layoutParams =
            FrameLayout.LayoutParams(
                (DisplayUtil.getWindowWidth(activity) * WIDTH_RATIO).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        dialog.setCanceledOnTouchOutside(dialogBean.canCancel)
        dialog.setCancelable(dialogBean.canCancel)
        dialog.show()
        mBindView.root.findViewById<Button>(R.id.btnCancel).setOnClickListener { dialog.cancel() }
        mBindView.root.findViewById<Button>(R.id.btnConfirm).setOnClickListener { click.onConfirmClick(dialog, "") }
    }

    /**
     * 关闭指定dialog
     */
    fun closeDialog(dialog: Dialog) {
        dialog.cancel()
    }
}