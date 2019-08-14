package com.even.common.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *
 */
class SpUtils {
    companion object {
        /**
         * 默认Sp名字
         */
        private var spName = "Even"
        private lateinit var sp: SharedPreferences
        private var instance: SpUtils? = null
        fun getInstance(spName: String): SpUtils {
            if (null == instance) {
                synchronized(SpUtils::class.java) {
                    if (null == instance) {
                        instance = SpUtils()
                    }
                }
            }
            sp = ApplicationUtils.getInstance().applicationContext.getSharedPreferences(spName, Context.MODE_PRIVATE)
            return instance!!
        }

        fun getInstance(): SpUtils {
            return getInstance(spName)
        }

        /**
         * 初始化Application中调用，用来设置默认得sp名字
         */
        fun init(spName: String) {
            this.spName = spName
        }
    }


    fun get(key: String, defaultValue: Any): Any {
        return when (defaultValue) {
            is String -> sp.getString(key, defaultValue)
            is Int -> sp.getInt(key, defaultValue)
            is Boolean -> sp.getBoolean(key, defaultValue)
            is Float -> sp.getFloat(key, defaultValue)
            is Long -> sp.getLong(key, defaultValue)
            is Set<*> -> sp.getStringSet(key, defaultValue as MutableSet<String>?)
            else -> defaultValue
        }
    }

    /**
     * 存储
     */
    @SuppressLint("CommitPrefEdits")
    fun put(key: String, value: Any) {
        val edit = sp.edit()
        when (value) {
            is String -> edit.putString(key, value)
            is Boolean -> edit.putBoolean(key, value)
            is Float -> edit.putFloat(key, value)
            is Int -> edit.putInt(key, value)
            is Long -> edit.putLong(key, value)
            is Set<*> -> edit.putStringSet(key, value as MutableSet<String>?)
        }
        edit.apply()
    }

    /**
     * 移除指定key数据
     */
    fun remove(key: String) {
        sp.edit().remove(key).apply()
    }

    /**
     * 清楚指定Sp数据
     */
    fun clear() {
        sp.edit().clear().apply()
    }

}