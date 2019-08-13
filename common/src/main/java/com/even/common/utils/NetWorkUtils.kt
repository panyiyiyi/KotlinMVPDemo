package com.even.common.utils

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.provider.Settings

/**
 * @author  Created by Even on 2019/8/13
 *  Email: emailtopan@163.com
 *  往后相关工具类
 */
object NetWorkUtils {
    /**
     * 开启网络设置界面
     */
    fun openWirelessSettings() {
        ApplicationUtils.getInstance()
            .startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    /**
     * 是否连接到网络
     */
    fun isConnect(): Boolean {
        val netWorkInfo = getNetWorkInfo()
        return null != netWorkInfo && netWorkInfo.isConnected
    }

    /**
     * 是否是移动数据连接
     */
    fun isMobileNet(): Boolean {
        val netWorkInfo = getNetWorkInfo()
        return null != netWorkInfo && netWorkInfo.isConnected && netWorkInfo.type == ConnectivityManager.TYPE_MOBILE
    }


    /**
     * 判断WiFi是否可用
     */
    fun isWiFiEnable(): Boolean {
        val wifiManager =
            ApplicationUtils.getInstance().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.isWifiEnabled
    }

    /**
     * 是否使WiFi连接
     */
    fun isWiFiConnect(): Boolean {
        val netWorkInfo = getNetWorkInfo()
        return null != netWorkInfo && netWorkInfo.isConnected && netWorkInfo.type == ConnectivityManager.TYPE_WIFI
    }

    /**
     * 获取当前连接得网络
     */
    private fun getNetWorkInfo(): NetworkInfo? {
        val conManager =
            ApplicationUtils.getInstance().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return conManager.activeNetworkInfo
    }

}