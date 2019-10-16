package com.even.common.router

import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author  Created by Even on 2019/8/15
 *  Email: emailtopan@163.com
 *  路由provider管理工具类
 */
object ARouterManagerUtils {
    private var mProviderMap = mutableMapOf<String, IProvider>()

    fun <T : IProvider> getProvider(path: String): T? {
        var iProvider = mProviderMap[path]
        if (null == iProvider) {
            iProvider = ARouter.getInstance().build(path).navigation() as IProvider
            mProviderMap[path] = iProvider
        }
        return iProvider as T
    }
}