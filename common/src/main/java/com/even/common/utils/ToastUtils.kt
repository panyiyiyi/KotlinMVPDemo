package com.even.common.utils

import android.widget.Toast

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *
 */
object ToastUtils {

    private var mToast: Toast? = null
    /**
     * 显示短时间Toast
     */
    fun showShort(msg: CharSequence) {
        if (null != mToast) {
            cancelToast()
        }

        mToast = Toast.makeText(ApplicationUtils.getInstance().applicationContext, msg, Toast.LENGTH_SHORT)
        mToast!!.show()
    }

    /**
     * 显示长时间Toast
     */
    fun showLong(msg: CharSequence) {
        if (null != mToast) {
            cancelToast()
        }
        mToast = Toast.makeText(ApplicationUtils.getInstance().applicationContext, msg, Toast.LENGTH_LONG)
        mToast!!.show()
    }

    private fun cancelToast() {
        if (null != mToast) {
            mToast!!.cancel()
            mToast = null
        }
    }
}