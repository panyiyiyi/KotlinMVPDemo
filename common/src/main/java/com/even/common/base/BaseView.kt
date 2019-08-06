package com.even.common.base
/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
interface BaseView {
    fun showLoading()

    fun showLoading(title: String)

    fun hideLoading()
}