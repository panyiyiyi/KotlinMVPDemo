package com.even.common.impl

/**
 * @author  Created by Even on 2019/8/6
 *  Email: emailtopan@163.com
 * 权限请求回调
 */
interface OnPermissionCallBack {
    fun onResult(permissionGranted: Boolean)
}