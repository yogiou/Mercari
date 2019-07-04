package com.jiewen.mercari.home.viewmodel

import com.jiewen.mercari.helper.RxBus.RxBus
import com.jiewen.mercari.helper.RxBus.RxBusEvent.SupportedRxBusEventKeys
import com.jiewen.mercari.home.model.CategoryData
import com.jiewen.mercari.home.viewmodel.HomeActivityViewModelRxEvent.Companion.GET_CATEGORY_DATA_SUCCESS
import com.jiewen.mercari.timeline.model.CategoryApi
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.*

class HomeActivityViewModelTest {
    @Mock
    private lateinit var rxBus: RxBus

    @Mock
    private lateinit var categoryApi: CategoryApi

    @InjectMocks
    private lateinit var homeActivityViewModel: HomeActivityViewModel

    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ _ -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetCategoriesSuccess() {
        val list: List<CategoryData> = ArrayList()
        Mockito.`when`(categoryApi.getProducts()).thenReturn(Observable.just(list))
        homeActivityViewModel.getCategories()

        verify(rxBus, times(3)).send(any())
    }

    @Test
    fun testGetCategoriesError() {
        val error = Exception()
        Mockito.`when`(categoryApi.getProducts()).thenReturn(Observable.error(error))
        homeActivityViewModel.getCategories()

        verify(rxBus, times(3)).send(any())
    }
}