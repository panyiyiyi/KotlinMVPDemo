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

    /**
     * 获取视频数据
     * page:从0开始
     */
    fun getVideoList(pageNo: Int, type: String) {
        var map = mutableMapOf<String, Any>()
        map["type"] = type
        map["tag"] = "热门"
        map["page_limit"] = 20
        map["page_start"] = pageNo * 20
        map["sort"] = "recommend"

        RxHttpUtils.createApi(ApiService::class.java)
            .getHotVideo(map)
            .compose(Transformer.switchSchedulers())
            .subscribe(object : BaseStringObserver(RxTag) {
                override fun doSubscriber(disposable: Disposable) {
                    LogUtils.e("hh0")
                }

                override fun doFail(errorMsg: String) {
                    LogUtils.e("hh0")
                }

                override fun doNext(json: String) {
                    LogUtils.e("hh0")
                }

                override fun doCompleted() {
                    LogUtils.e("hh0")
                }
            })
    }
}