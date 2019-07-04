package com.jiewen.mercari

import androidx.lifecycle.ViewModel
import com.jiewen.mercari.helper.RxBus.RxBus
import com.jiewen.mercari.helper.RxBus.RxEvent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class AbstractViewModel : ViewModel() {
    protected abstract val compositeDisposable: CompositeDisposable
    private var rxBus: RxBus = RxBus.getInstance()

    internal abstract fun getEventType(): Class<*>

    private fun getHashCode(): Int {
        return hashCode()
    }

    internal fun getObservable(): Observable<RxEvent<*, *>>? {
        return rxBus.getObservable(getEventType()).filter{ rxEvent -> getHashCode() == rxEvent.viewModeHashCode}
    }

    internal fun post(event: RxEvent<*, *>) {
        event.viewModeHashCode = hashCode()
        rxBus.send(event)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}