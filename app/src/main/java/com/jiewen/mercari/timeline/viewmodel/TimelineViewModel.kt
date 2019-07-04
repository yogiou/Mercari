package com.jiewen.mercari.timeline.viewmodel

import com.jiewen.mercari.AbstractViewModel
import com.jiewen.mercari.helper.RxBus.RxBusEvent.SupportedRxBusEventKeys
import com.jiewen.mercari.timeline.model.ProductApi
import com.jiewen.mercari.timeline.model.ProductApiService
import com.jiewen.mercari.utils.BaseErrorHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.io.File

class TimelineViewModel : AbstractViewModel() {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getEventType(): Class<*> {
        return TimelineViewModelRxEvent::class.java
    }

    private fun postEvent(key: String, value: Any) {
        post(TimelineViewModelRxEvent(key, value))
    }

    fun getProducts(url: String, param: String) {
        val productApi: ProductApi = ProductApiService.create(url)
        productApi.getProducts(param)
            .doOnSubscribe {
                post(TimelineViewModelRxEvent(SupportedRxBusEventKeys.SHOW_LOADING, true))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                post(TimelineViewModelRxEvent(SupportedRxBusEventKeys.SHOW_LOADING, false))
            }
            .subscribeBy(
                onNext = { list ->
                    postEvent(TimelineViewModelRxEvent.GET_PRODUCT_SUCCESS, list)
                },
                onError = { error ->
                    post(TimelineViewModelRxEvent(BaseErrorHelper.EVENT_KEY_BASE_ERROR, error))
                }
            ).addTo(compositeDisposable)
    }

    fun extractUrl(url: String): List<String> {
        val result: MutableList<String> = ArrayList()

        val index = url.lastIndexOf("/")

        result.add(url.substring(0, index + 1))
        result.add(url.substring(index + 1))

        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}