package com.even.common.base.model

import kotlin.reflect.KClass


/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Implement(val value: KClass<*>)