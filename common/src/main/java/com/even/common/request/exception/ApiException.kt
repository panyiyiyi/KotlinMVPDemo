package com.even.common.request.exception

import com.even.common.R
import com.even.common.utils.ApplicationUtils
import org.json.JSONException
import java.io.NotSerializableException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException


/**
 * @author  Created by Even on 2019/8/7
 *  Email: emailtopan@163.com
 * 统一异常处理
 */
class ApiException : Exception {
    var mCode: Int
    override var message: String

    constructor(throwable: Throwable, errorCode: Int) {
        mCode = errorCode
        this.message = throwable.message!!
    }

    companion object {
        fun handleException(throwable: Throwable): ApiException {
            val apiEx: ApiException
            if (throwable is SocketTimeoutException) {
                //请求超时
                apiEx = ApiException(throwable, ErrorCode.TIMEOUT_ERROR)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_timeout_error)
            } else if (throwable is ConnectException) {
                //连接异常
                apiEx = ApiException(throwable, ErrorCode.TIMEOUT_ERROR)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_connect_error)
            } else if (throwable is UnknownHostException) {
                //服务器异常
                apiEx = ApiException(throwable, ErrorCode.TIMEOUT_ERROR)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_unknown_host_error)
            } else if (throwable is NullPointerException) {
                //空指针
                apiEx = ApiException(throwable, ErrorCode.NULL_POINTER_ERROR)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_null_pointer_error)
            } else if (throwable is SSLHandshakeException) {
                //证书校验失败
                apiEx = ApiException(throwable, ErrorCode.SSL_ERROR)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_ssl_error)
            } else if (throwable is JSONException
                || throwable is NotSerializableException
                || throwable is ParseException
            ) {
                //解析错误
                apiEx = ApiException(throwable, ErrorCode.PARSE_ERROR)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_parse_error)
            } else if (throwable is IllegalAccessException) {
                //非法数据
                //解析错误
                apiEx = ApiException(throwable, ErrorCode.PARSE_ERROR)
                throwable.message?.let { apiEx.message = throwable.message!! }
            } else {
                //未知异常
                apiEx = ApiException(throwable, ErrorCode.UNKNOWN)
                apiEx.message = ApplicationUtils.getInstance().applicationContext.getString(R.string.common_unknown_error)
            }
            return apiEx
        }
    }

    override fun getLocalizedMessage(): String {
        return message
    }

}