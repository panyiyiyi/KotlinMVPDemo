package com.even.common.base


/**
 * @author  Created by Even on 2019/8/1
 *  Email: emailtopan@163.com
 *
 */
open class BasePresenter<V : BaseView> {
    private var mView: V? = null
    lateinit var RxTag: String
    fun attachView(mvpView: V) {
        this.mView = mvpView
    }

    fun detachView() {
        this.mView = null
    }

    fun getView(): V? {
        return mView
    }

    fun hideLoading() {
        mView?.hideLoading()
    }

    fun isViewBind(): Boolean {
        return mView != null
    }


}