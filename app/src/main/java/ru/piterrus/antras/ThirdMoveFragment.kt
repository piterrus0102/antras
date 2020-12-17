package ru.piterrus.antras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class ThirdMoveFragment: Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_third_move, null)
        tabLayout = v.findViewById(R.id.thirdMoveTabLayout)
        viewPager = v.findViewById(R.id.thirdMoveViewPager)
        val adapter = MyAdapter(childFragmentManager)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(myOnTabSelectedListener)
        return v
    }

    val myOnTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab) {

        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {

        }

        override fun onTabSelected(p0: TabLayout.Tab) {
            viewPager.currentItem = p0.position
        }

    }

    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    StepOneFragment()
                }
                1 -> {
                    StepTwoFragment()
                }
                2 -> {
                    StepThreeFragment()
                }
                else -> {
                    StepOneFragment()
                }
            }
        }

        override fun getCount(): Int {
            return 3
        }

    }
}