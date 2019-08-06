package com.even.common.base.model

import com.even.common.base.BasePresenter
import com.even.common.base.BaseView

/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
object LogicProxy {
    /**
     * 获取Presenter
     */
    private fun getPresenter(vararg clazz: Class<*>): Any? {
        for (cls in clazz) {
            if (cls.isAnnotationPresent(Implement::class.java)) {
                for (ann in cls.declaredAnnotations) {
                    if (ann is Implement) {
                        return ann.value.objectInstance
                    }
                }
            }
        }
        return null
    }

    /**
     * 初始化Presenter
     */
    fun <T : BasePresenter<BaseView>> bind(clazz: Class<*>, mView: BaseView): T {
        val presenter = getPresenter(clazz) as BasePresenter<BaseView>
        if (mView != presenter.getView()) {
            if (presenter.getView() != null) {
                presenter.detachView()
            }
            presenter.attachView(mView)
        }
        return presenter as T
    }

}

