package com.even.kotlinmvvmdemo

import com.alibaba.android.arouter.facade.annotation.Route
import com.even.common.base.BaseActivity

/**
 * @author  Created by Even on 2019/8/16
 *  Email: emailtopan@163.com
 *
 */
@Route(path = "/Test/two")
class TestActivity1 : BaseActivity() {
    override fun getContentView(): Int = R.layout.activity_test

    override fun initView() {

    }

    override fun getLogicClazz(): Class<*>? = null

    override fun getTitleBarView(): Int = R.layout.activity_test
}