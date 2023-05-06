package me.yarond.mytime.ui.schedule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DaySchedulePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private var fragments: ArrayList<Fragment> = ArrayList()
    private var fragmentTitles: ArrayList<String> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        fragmentTitles.add(title)
    }

    fun getFragmentTitle(position: Int): String {
        return fragmentTitles[position]
    }

    fun getFragment(position: Int): Fragment {
        return fragments[position]
    }

}