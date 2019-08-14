package com.even.common.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.even.common.base.BaseTabFragment

/**
 * @author  Created by Even on 2019/8/14
 *  Email: emailtopan@163.com
 *  Fragment和ViewPager适配器
 */
class TabViewpagerAdapter(
    fm: FragmentManager,
    var fgLists: List<BaseTabFragment>,
    var titleNames: Array<String>
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fgLists[position]
    }

    override fun getCount(): Int = fgLists.size
    override fun getPageTitle(position: Int): CharSequence = titleNames[position]
}