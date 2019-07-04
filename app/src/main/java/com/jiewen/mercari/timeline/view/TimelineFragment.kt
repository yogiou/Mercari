package com.jiewen.mercari.timeline.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.jiewen.mercari.AbstractBaseFragment
import com.jiewen.mercari.AbstractViewModel
import com.jiewen.mercari.R
import com.jiewen.mercari.home.model.CategoryData
import com.jiewen.mercari.home.view.HomeActivity
import com.jiewen.mercari.timeline.model.ProductData
import com.jiewen.mercari.timeline.viewmodel.TimelineViewModel
import com.jiewen.mercari.timeline.viewmodel.TimelineViewModelRxEvent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.android.synthetic.main.fragment_timeline.progress_bar

class TimelineFragment : AbstractBaseFragment() {
    var categoryData: CategoryData? = null
    private var timelineAdapter: TimelineAdapter? = null

    companion object {
        const val NUM_OF_COLUMNS = 2
        private const val TAG = "TimelineFragment"
    }

    interface TimelineFragmentObserver {
        // TODO：if we would like to interact from fragment to activity, we can communicate through this interface by adding new functions
    }

    var viewModel: TimelineViewModel? = null
    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = arguments?.getSerializable(HomeActivity.ARGUMENT_PRODUCT_DATA)

        if (argument is CategoryData) {
            categoryData = argument

            categoryData?.let {
                val result = viewModel?.extractUrl(it.data)

                if (result?.size == 2) {
                    viewModel?.getProducts(result[0], result[1])
                }
            }
        }
    }

    override fun getViewModel(): AbstractViewModel? {
        viewModel = ViewModelProviders.of(this).get(TimelineViewModel::class.java)
        return viewModel
    }

    override fun getFragmentTag(): String {
        return TAG
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_timeline
    }

    override fun init(view: View) {
        timeline_list.layoutManager = GridLayoutManager(context, NUM_OF_COLUMNS)
    }

    override fun childHandleRxEvent(key: String, value: Any?) {
        super.childHandleRxEvent(key, value)

        when (key) {
            TimelineViewModelRxEvent.GET_PRODUCT_SUCCESS -> {
                value?.let {
                    val productList = value as List<ProductData>
                    setUpRecyclerView(productList)
                }
            }
            else -> {

            }
        }
    }

    private fun setUpRecyclerView(productList: List<ProductData>) {
        timelineAdapter = TimelineAdapter(productList, context)

        timelineAdapter?.let {
            timeline_list.adapter = timelineAdapter
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            progress_bar?.visibility = View.VISIBLE
        } else {
            progress_bar?.visibility = View.GONE
        }
    }
}