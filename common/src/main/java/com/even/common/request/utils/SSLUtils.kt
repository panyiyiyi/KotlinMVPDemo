package com.even.common.request.utils

import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * @author  Created by Even on 2019/8/8
 *  Email: emailtopan@163.com
 *  https证书认证工具类
 */
object SSLUtils {
    class SSLParams {
        var mSSLSocketFactory: SSLSocketFactory? = null
        var trustManager: X509TrustManager? = null
    }

    /**
     * 不做证书认证
     */
    fun getSSLSocketFactory(): SSLParams {
        return getSSLSocketFactoryDes(null, null, null)
    }

    /**
     * https单向认证
     * 按照CA 证书去验证，如果不是CA信任的证书，则验证不通过
     */
    fun getSSLSocketFactory(trustManager: X509TrustManager): SSLParams {
        return getSSLSocketFactoryDes(trustManager, null, null)
    }

    /**
     * https单向认证
     * 用含有服务器公钥的证书验证服务器证书
     */
    fun getSSLSocketFactory(vararg certificates: InputStream): SSLParams {
        return getSSLSocketFactoryDes(null, null, null, *certificates)
    }

    /**
     * https双向认证
     * isFile ：客户端使用的bks证书校验服务器证书
     * certificates：含有服务器公钥的证书校验服务端证书
     */
    fun getSSLSocketFactory(
        isFile: InputStream,
        password: String,
        vararg certificates: InputStream
    ): SSLParams {
        return getSSLSocketFactoryDes(null, isFile, password, *certificates)
    }

    /**
     * https双向认证
     * trustManager：如果需要自己校验，如果不需要直接传入空
     * isFile：客户端使用bks证书校验服务器证书
     */
    fun getSSLSocketFactory(trustManager: X509TrustManager?, isFile: InputStream, password: String): SSLParams {
        return getSSLSocketFactoryDes(trustManager, isFile, password)
    }

    /**
     * https证书认证实现
     * @param trustManager 自定义trustManager
     * @param isFile       客户端使用的校验证书
     * @param password     校验密码
     * @param certificates 含有服务器公钥的证书
     */
    private fun getSSLSocketFactoryDes(
        trustManager: X509TrustManager?,
        isFile: InputStream?,
        password: String?,
        vararg certificates: InputStream
    ): SSLParams {
        val sslParams = SSLParams()
        val prepareKeyManager = prepareKeyManager(isFile, password)
        val trustManagers = prepareTrustManager(*certificates)
        var manager: X509TrustManager
        if (null != trustManager) {
            manager = trustManager
        } else if (trustManagers != null) {
            manager = chooseTrustManager(trustManagers)!!
        } else {
            manager = unSafeTrustManager
        }
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(prepareKeyManager, arrayOf<TrustManager>(manager), null)
        //获取SSLSocketFactory
        sslParams.mSSLSocketFactory = sslContext.socketFactory
        sslParams.trustManager = manager
        return sslParams

    }

    /**
     * 获取KeyManager
     */
    private fun prepareKeyManager(isFile: InputStream?, password: String?): Array<out KeyManager>? {
        if (null == isFile || null == password) {
            return null
        }
        val bks = KeyStore.getInstance("BKS")
        bks.load(isFile, password.toCharArray())
        val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmf.init(bks, password.toCharArray())
        return kmf.keyManagers
    }

    /**
     * 获取证书
     */
    private fun prepareTrustManager(vararg certificates: InputStream): Array<out TrustManager> {
        val certificateFactory = CertificateFactory.getInstance("X.509")
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
        keyStore.load(null)

        certificates.forEachIndexed { index, inputStream ->
            val certificate = certificateFactory.generateCertificate(inputStream)
            keyStore.setCertificateEntry(index.toString(), certificate)
            inputStream.close()
        }

        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(keyStore)
        return trustManagerFactory.trustManagers
    }

    private var unSafeTrustManager = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }
    }

    /**
     * 获取信任证书
     */
    private fun chooseTrustManager(trustManagers: Array<out TrustManager>): X509TrustManager? {
        trustManagers.forEach {
            if (it is X509TrustManager) {
                return it
            }
        }
        return null
    }

}