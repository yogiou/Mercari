package com.jiewen.mercari.home.viewmodel

import com.jiewen.mercari.AbstractViewModel
import com.jiewen.mercari.helper.RxBus.RxBusEvent.SupportedRxBusEventKeys
import com.jiewen.mercari.timeline.model.CategoryApi
import com.jiewen.mercari.timeline.model.CategoryApiService
import com.jiewen.mercari.utils.BaseErrorHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeActivityViewModel : AbstractViewModel() {
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val categoryApi: CategoryApi = CategoryApiService().create()

    override fun getEventType(): Class<*> {
        return HomeActivityViewModelRxEvent::class.java
    }

    private fun postEvent(key: String, value: Any) {
        post(HomeActivityViewModelRxEvent(key, value))
    }

    fun getCategories() {
        categoryApi.getProducts()
            .doOnSubscribe {
                post(HomeActivityViewModelRxEvent(SupportedRxBusEventKeys.SHOW_LOADING, true))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                post(HomeActivityViewModelRxEvent(SupportedRxBusEventKeys.SHOW_LOADING, false))
            }
            .subscribeBy(
                onNext = { categoryData ->
                    postEvent(HomeActivityViewModelRxEvent.GET_CATEGORY_DATA_SUCCESS, categoryData)
                },
                onError = { error ->
                    post(HomeActivityViewModelRxEvent(BaseErrorHelper.EVENT_KEY_BASE_ERROR, error))
                }
            ).addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}