package com.mubarak.insight.adapters

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mubarak.insight.fragments.Profile
import com.mubarak.insight.fragments.ProfileFavorite
import com.mubarak.insight.fragments.ProfileImages
import java.util.ArrayList

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    //
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment  {
        return when (position) {
            0 -> {
                ProfileImages()
            }
            1 -> {
                ProfileFavorite()
            }
            else -> {
                Fragment()
            }
        }
    }
}