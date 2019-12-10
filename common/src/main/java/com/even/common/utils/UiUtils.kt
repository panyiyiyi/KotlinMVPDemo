package com.even.common.utils

import android.app.Activity
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.even.common.R

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *
 */
object UiUtils {
    /**
     * 获取color
     */
    fun getColor(resId: Int): Int {
        return ContextCompat.getColor(ApplicationUtils.getInstance().applicationContext, resId)
    }

    /**
     * 获取String
     */
    fun getString(resId: Int): String {
        return ApplicationUtils.getInstance().applicationContext.getString(resId)
    }

    /**
     *  获取格式化之后的文字
     */
    fun getStringFormat(resId: Int, vararg value: Any): String {
        return String.format(getString(resId), *value)
    }

    /**
     * 获取StringArray
     */
    fun getStringArray(resId: Int): Array<String> {
        return ApplicationUtils.getInstance().applicationContext.resources.getStringArray(resId)
    }

    /**
     * 获取TextSize
     */
    fun getTextSize(resId: Int): Float {
        return ApplicationUtils.getInstance().resources.getDimension(resId)
    }

    /**
     * 获取Drawable
     */
    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(ApplicationUtils.getInstance().applicationContext, resId)
    }

    /**
     * 设置window透明度
     */
    fun setWindowBgAlpha(activity: Activity, bgAlpha: Float) {
        val window = activity.window
        val attributes = window.attributes
        attributes.alpha = bgAlpha
        activity.window.attributes = attributes
    }

    /**
     * 设置TextView部分颜色
     */
    fun setTextPartColor(textValue: String, colorId: Int, startPosition: Int, endPosition: Int): SpannableString {
        val spannableString = SpannableString(textValue)
        val colorSpan = ForegroundColorSpan(getColor(colorId))
        spannableString.setSpan(colorSpan, startPosition, endPosition, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return spannableString
    }

}