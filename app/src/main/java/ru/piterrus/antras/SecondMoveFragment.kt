package ru.piterrus.antras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout


class SecondMoveFragment: Fragment() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var imageView2: ImageView
    lateinit var gradientLayout2: ConstraintLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_second_move, null)
        tabLayout = v.findViewById(R.id.secondMoveTabLayout)
        viewPager = v.findViewById(R.id.secondMoveViewPager)
        imageView2 = v.findViewById(R.id.imageView2)
        gradientLayout2 = v.findViewById(R.id.gradientLayout2)
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
            if(p0.position == 0){
                imageView2.setImageResource(R.drawable.main)
            }
            if(p0.position == 1){
                imageView2.setImageResource(R.drawable.main2)
            }
        }

    }

    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    PolicyTabFragment()
                }
                1 -> {
                    CashTabFragment()
                }
                else -> {
                    PolicyTabFragment()
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }
}