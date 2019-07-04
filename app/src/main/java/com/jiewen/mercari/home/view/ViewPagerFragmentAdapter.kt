package com.jiewen.mercari.home.view

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jiewen.mercari.AbstractBaseFragment
import com.jiewen.mercari.timeline.view.TimelineFragment

class ViewPagerFragmentAdapter @JvmOverloads constructor(fm: FragmentManager, fragmentList: List<TimelineFragment>): FragmentPagerAdapter(fm) {
    private val fragmentList: List<TimelineFragment> = fragmentList

    override fun getItem(position: Int): AbstractBaseFragment? {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        fragmentList[position].categoryData
        return fragmentList[position].categoryData?.name
    }
}