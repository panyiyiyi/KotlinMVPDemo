package com.even.common.impl

/**
 * @author  Created by Even on 2019/8/6
 *  Email: emailtopan@163.com
 *  多权限请求回调
 */
interface OnPermissionCallBacks {
    /**
     * 拒接接口的回调
     */
    fun onFailResult(permissionDenies: Array<String>)

    /**
     *  通过所有权限的回调
     */
    fun onSuccessResult()
}