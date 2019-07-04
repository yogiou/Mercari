package com.jiewen.mercari.timeline.viewmodel

import com.jiewen.mercari.helper.RxBus.RxBus
import com.jiewen.mercari.timeline.model.ProductApi
import com.jiewen.mercari.timeline.model.ProductData
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TimelineViewModelTest {
    @Mock
    private lateinit var rxBus: RxBus

    @Mock
    private lateinit var productApi: ProductApi

    @InjectMocks
    private lateinit var timelineViewModel: TimelineViewModel


    @Before
    fun before() {
        RxJavaPlugins.setIoSchedulerHandler { _ -> Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ _ -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testGetProductDataSuccess() {
        val list: List<ProductData> = ArrayList()
        Mockito.`when`(productApi.getProducts("https://abc.com/test.json")).thenReturn(Observable.just(list))
        timelineViewModel.getProducts("https://abc.com", "test.json")

        verify(rxBus, times(3)).send(any())
    }

    @Test
    fun testGetProductDataError() {
        val error = Exception()
        Mockito.`when`(productApi.getProducts("https://abc.com/test.json")).thenReturn(Observable.error(error))
        timelineViewModel.getProducts("https://abc.com", "test.json")

        verify(rxBus, times(3)).send(any())
    }

    @Test
    fun testExtractUrl() {
        Assert.assertEquals("http://aasdja.com/asdasd/asdasd/", timelineViewModel.extractUrl("http://aasdja.com/asdasd/asdasd/asdas")[0])
        Assert.assertEquals("asdas", timelineViewModel.extractUrl("http://aasdja.com/asdasd/asdasd/asdas")[1])
        Assert.assertEquals("", timelineViewModel.extractUrl("")[1])
        Assert.assertEquals("", timelineViewModel.extractUrl("")[0])
    }
}