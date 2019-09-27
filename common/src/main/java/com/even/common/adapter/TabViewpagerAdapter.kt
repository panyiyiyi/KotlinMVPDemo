package com.even.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *  Fragment和ViewPager适配器
 */
class TabViewpagerAdapter<T : Fragment>(
    fm: FragmentManager,
    var fgLists: List<T>,
    var titleNames: List<String>,
    private var behavior: Int?
) : FragmentPagerAdapter(fm, behavior ?: BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return fgLists[position]
    }

    override fun getCount(): Int = fgLists.size
    override fun getPageTitle(position: Int): CharSequence = titleNames[position]
}