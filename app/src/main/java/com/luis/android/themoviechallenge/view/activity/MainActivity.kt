package com.luis.android.themoviechallenge.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.luis.android.themoviechallenge.R
import com.lyft.android.themoviedbtest.view.adapter.ViewPagerAdapter
import com.lyft.android.themoviedbtest.view.fragment.LocationFragment
import com.lyft.android.themoviedbtest.view.fragment.MoviesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewPagerView()
        initBottomAppBar()
    }

    private fun initViewPagerView() {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(MoviesFragment.getInstance(), getString(R.string.movies))
        pagerAdapter.addFragment(LocationFragment.getInstance(), getString(R.string.location))
        menu_viewPager.adapter = pagerAdapter

        menu_viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled( position: Int,
                                         positionOffset: Float,
                                         positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottomNavigationView.selectedItemId = R.id.bottom_app_movies
                    1 -> bottomNavigationView.selectedItemId = R.id.bottom_app_location
                    else -> bottomNavigationView.selectedItemId = R.id.bottom_app_movies
                }
            }
        })

    }

    private fun initBottomAppBar() {
        fabMenu.setOnClickListener {
            Toast.makeText(this,"Add photo",Toast.LENGTH_SHORT).show()
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.bottom_app_movies -> {
                    menu_viewPager.currentItem = 0
                    true
                }
                R.id.bottom_app_location -> {
                    menu_viewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }

    }

}