package com.even.kotlinmvvmdemo.ui.presenters

import com.even.common.base.BasePresenter
import com.even.common.request.observer.BaseStringObserver
import com.even.common.request.observer.Transformer
import com.even.common.request.utils.RxHttpUtils
import com.even.common.utils.LogUtils
import com.even.kotlinmvvmdemo.ApiService
import com.even.kotlinmvvmdemo.ui.views.MainView
import io.reactivex.disposables.Disposable

/**
 * @author  Created by Even on 2019/8/20
 *  Email: emailtopan@163.com
 *
 */
class MainPresenter : BasePresenter<MainView>() {

    fun getData() {
        RxHttpUtils.createApi(ApiService::class.java)
            .getImageVerification()
            .compose(Transformer.switchSchedulers())
            .subscribe(object : BaseStringObserver(RxTag) {
                override fun doSubscriber(disposable: Disposable) {
                }

                override fun doFail(errorMsg: String) {
//                    LogUtils.e(errorMsg)
                }

                override fun doNext(json: String) {
//                    LogUtils.e(json)
                }

                override fun doCompleted() {
//                    LogUtils.e("")
                }
            })
    }

    fun getData2() {
        RxHttpUtils.createApi(ApiService::class.java)
            .getArticleLists("409", 1)
            .compose(Transformer.switchSchedulers())
            .subscribe(object : BaseStringObserver(RxTag) {
                override fun doSubscriber(disposable: Disposable) {
                }

                override fun doFail(errorMsg: String) {
                    LogUtils.e(errorMsg)
                }

                override fun doNext(json: String) {
                    LogUtils.e(json)
                }

                override fun doCompleted() {
                    LogUtils.e("")
                }
            })
    }
}