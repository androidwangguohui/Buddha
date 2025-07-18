package com.example.buddha.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.buddha.fragment.FunctionFragment
import com.example.buddha.fragment.HomeFragment
import com.example.buddha.fragment.MyFragment

class FragmentViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance("首页内容")
            1 -> FunctionFragment.newInstance("发现内容")
            2 -> MyFragment.newInstance("我的内容")
            else -> HomeFragment.newInstance("默认内容")
        }
    }
}