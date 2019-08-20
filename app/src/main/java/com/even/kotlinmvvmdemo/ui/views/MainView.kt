package com.even.kotlinmvvmdemo.ui.views

import com.even.common.base.BaseView
import com.even.common.base.model.Implement
import com.even.kotlinmvvmdemo.ui.presenters.MainPresenter

/**
 * @author  Created by Even on 2019/8/20
 *  Email: emailtopan@163.com
 *
 */
@Implement(MainPresenter::class)
interface MainView : BaseView {
    fun getSuccess()
}