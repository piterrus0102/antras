package ru.piterrus.antras

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

class FirstEnterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var containerForSteps: ViewPager
    private lateinit var backButton: Button
    private lateinit var forwardButton: Button
    private lateinit var stepsSeekBar: SeekBar

    private lateinit var containerAdapter: MyAdapter

    private lateinit var myPageListener: MyPageListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_enter)
        supportActionBar?.hide()

        containerForSteps = findViewById(R.id.containerForSteps)
        backButton = findViewById(R.id.backButton)
        forwardButton = findViewById(R.id.forwardButton)
        stepsSeekBar = findViewById(R.id.stepsSeekBar)

        forwardButton.setOnClickListener(this)
        backButton.setOnClickListener(this)

        containerAdapter = MyAdapter(supportFragmentManager)
        containerForSteps.adapter = containerAdapter
        containerForSteps.currentItem = 0
        myPageListener = MyPageListener()
        containerForSteps.addOnPageChangeListener(myPageListener)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.forwardButton -> {
                if(containerForSteps.currentItem == 0){
                    containerForSteps.currentItem = 1
                    backButton.visibility = View.VISIBLE
                    stepsSeekBar.progress = 1
                    return
                }
                if(containerForSteps.currentItem == 1){
                    containerForSteps.currentItem = 2
                    stepsSeekBar.progress = 2
                    forwardButton.text = "ГОТОВО"
                    return
                }
                if(forwardButton.text.equals("ГОТОВО")){
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            R.id.backButton -> {
                if(containerForSteps.currentItem == 2){
                    containerForSteps.currentItem = 1
                    stepsSeekBar.progress = 1
                    forwardButton.text = "ДАЛЕЕ"
                    return
                }
                if(containerForSteps.currentItem == 1){
                    containerForSteps.currentItem = 0
                    stepsSeekBar.progress = 0
                    backButton.visibility = View.GONE
                    return
                }
            }
        }
    }

    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> {
                    FirstMoveFragment()
                }
                1 -> {
                    SecondMoveFragment()
                }
                2 -> {
                    ThirdMoveFragment()
                }
                else -> {
                    FirstMoveFragment()
                }
            }
        }

        override fun getCount(): Int {
            return 3
        }

    }

    inner class MyPageListener: ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            if(position == 0){
                backButton.visibility = View.GONE
                stepsSeekBar.progress = 0
            }
            if(position == 1){
                backButton.visibility = View.VISIBLE
                stepsSeekBar.progress = 1
                forwardButton.text = "ДАЛЕЕ"
            }
            if(position == 2){
                stepsSeekBar.progress = 2
                forwardButton.text = "ГОТОВО"
            }
        }

    }
}
