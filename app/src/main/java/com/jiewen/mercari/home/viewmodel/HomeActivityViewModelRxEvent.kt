package com.jiewen.mercari.home.viewmodel

import com.jiewen.mercari.helper.RxBus.RxEvent

class HomeActivityViewModelRxEvent<T>(k: String, v: T) : RxEvent<String, T>(k, v) {
    companion object {
        const val GET_CATEGORY_DATA_SUCCESS = "GET_CATEGORY_DATA_SUCCESS"
    }
}