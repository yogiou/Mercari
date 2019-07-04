package com.jiewen.mercari.timeline.viewmodel

import com.jiewen.mercari.helper.RxBus.RxEvent

class TimelineViewModelRxEvent<T>(k: String, v: T) : RxEvent<String, T>(k, v) {
    companion object {
        const val GET_PRODUCT_SUCCESS = "GET_PRODUCT_SUCCESS"
    }
}