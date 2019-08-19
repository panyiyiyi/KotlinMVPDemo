package com.even.kotlinmvvmdemo

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route

/**
 * @author  Created by Even on 2019/8/16
 *  Email: emailtopan@163.com
 *
 */
@Route(path = "/App/test")
class TestProvider : ITestProvider {
    override fun getLog() {
    }

    override fun init(context: Context?) {
    }
}