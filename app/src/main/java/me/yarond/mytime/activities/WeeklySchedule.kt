package me.yarond.mytime.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import me.yarond.mytime.R
import me.yarond.mytime.adapters.DaySchedulePagerAdapter
import me.yarond.mytime.adapters.PendingEvent
import me.yarond.mytime.fragments.DayScheduleFragment

class WeeklySchedule : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_schedule)
        setViews()
    }

    private fun setViews() {
        viewPager = findViewById<ViewPager2>(R.id.viewpager_weeklyschedule)
        tabLayout = findViewById<TabLayout>(R.id.tablayout_weeklyschedule)

        val adapter = DaySchedulePagerAdapter(this)
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 1", "10:00", "1"),
            PendingEvent("Event 2", "11:00", "2")
        ), "Sunday"), R.string.sunday_short.toString())
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 3", "10:00", "1"),
            PendingEvent("Event 4", "11:00", "2")
        ), "Monday"), R.string.monday_short.toString())
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 5", "10:00", "1"),
            PendingEvent("Event 6", "11:00", "2")
        ), "Tuesday"), R.string.tuesday_short.toString())
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 7", "10:00", "1"),
            PendingEvent("Event 8", "11:00", "2")
        ), "Wednesday"), R.string.wednesday_short.toString())
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 9", "10:00", "1"),
            PendingEvent("Event 10", "11:00", "2")
        ), "Thursday"), R.string.thursday_short.toString())
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 11", "10:00", "1"),
            PendingEvent("Event 12", "11:00", "2")
        ), "Friday"), R.string.friday_short.toString())
        adapter.addFragment(DayScheduleFragment(arrayListOf(
            PendingEvent("Event 13", "10:00", "1"),
            PendingEvent("Event 14", "11:00", "2")
        ), "Saturday"), R.string.saturday_short.toString())
        tabLayout.addTab(tabLayout.newTab().setText(R.string.sunday_short))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.monday_short))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tuesday_short))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.wednesday_short))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.thursday_short))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.friday_short))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.saturday_short))

        viewPager.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Do nothing
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Do nothing
            }
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}