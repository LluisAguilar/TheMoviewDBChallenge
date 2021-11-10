package com.lyft.android.themoviedbtest.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (val fragmentList:List<Fragment>, fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()

        when(position){
            0-> {
                fragment =  fragmentList.get(0)
            }

            1-> {
                fragment =  fragmentList.get(1)
            }
        }

        return fragment
    }



}