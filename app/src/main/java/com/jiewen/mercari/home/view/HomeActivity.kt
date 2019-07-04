package com.jiewen.mercari.home.view

import android.content.res.Configuration
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.jiewen.mercari.AbstractBaseActivity
import com.jiewen.mercari.AbstractViewModel
import com.jiewen.mercari.R
import com.jiewen.mercari.home.model.CategoryData
import com.jiewen.mercari.home.viewmodel.HomeActivityViewModel
import com.jiewen.mercari.home.viewmodel.HomeActivityViewModelRxEvent
import com.jiewen.mercari.timeline.view.TimelineFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AbstractBaseActivity(), View.OnClickListener, TimelineFragment.TimelineFragmentObserver {
    private var viewModel: HomeActivityViewModel? = null
    private var isRotated = false

    companion object {
        private const val TAG = "HomeActivity"
        const val ARGUMENT_PRODUCT_DATA = "ARGUMENT_PRODUCT_DATA"
    }

    override val compositeDisposable: CompositeDisposable
        get() = super.compositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isRotated) {
            viewModel?.getCategories()
        } else {
            isRotated = false
        }
    }

    override fun init() {
        sell_button?.setOnClickListener(this)
    }

    private fun setUpViewPager(categoryUrlList: List<CategoryData>) {
        val fragmentLists: MutableList<TimelineFragment> = ArrayList()

        for (category: CategoryData in categoryUrlList) {
            val tab: TabLayout.Tab = tabLayout.newTab()
            tabLayout.addTab(tab)
            val timelineFragment = TimelineFragment()
            val bundle = Bundle()
            bundle.putSerializable(ARGUMENT_PRODUCT_DATA, category)
            timelineFragment.arguments = bundle
            fragmentLists.add(timelineFragment)
        }

        tabLayout.setupWithViewPager(home_view_pager)
        home_view_pager.offscreenPageLimit = fragmentLists.size
        home_view_pager.adapter = ViewPagerFragmentAdapter(supportFragmentManager, fragmentLists)
    }

    override fun getViewModel(): AbstractViewModel? {
        viewModel = ViewModelProviders.of(this).get(HomeActivityViewModel::class.java)
        return viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun childHandleRxEvent(key: String, value: Any?) {
        super.childHandleRxEvent(key, value)

        when (key) {
            HomeActivityViewModelRxEvent.GET_CATEGORY_DATA_SUCCESS -> {
                value?.let {
                    val categoryUrlList = value as List<CategoryData>
                    categoryUrlList?.let {
                        setUpViewPager(it)
                    }
                }
            }
            else -> {

            }
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            progress_bar?.visibility = View.VISIBLE
        } else {
            progress_bar?.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        // TODO: handle click event
        when (v?.id) {
            R.id.sell_button -> {
                Toast.makeText(this, R.string.fab_click_toast, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        isRotated = true
    }
}
