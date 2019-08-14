package com.even.common.utils

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

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
}