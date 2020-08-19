package com.even.common.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 *  @author  Created by Even on 2020/8/19
 *  Email: emailtopan@163.com
 *  键盘工具类
 */
object KeyBoardUtils {

    fun showKeyBoard(context: Activity) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.RESULT_SHOWN, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyBoard(context: Activity, view: View) {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }
}