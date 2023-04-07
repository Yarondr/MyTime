package me.yarond.mytime.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import me.yarond.mytime.R
import me.yarond.mytime.adapters.DaySchedulePagerAdapter
import me.yarond.mytime.adapters.PendingEvent
import me.yarond.mytime.fragments.DayScheduleFragment

class WeeklyScheduleActivity : FragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggleSidebar: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var sidebarButton: ImageButton
    private lateinit var addEventButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_schedule)
        setViews()
        setSideBar()
        setButtons()
        setAdapters()
    }

    private fun setViews() {
        viewPager = findViewById<ViewPager2>(R.id.viewpager_weeklyschedule)
        tabLayout = findViewById<TabLayout>(R.id.tablayout_weeklyschedule)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawerlayout_weeklyschedule)
        navigationView = findViewById<NavigationView>(R.id.navigationview_weeklyschedule)
        sidebarButton = findViewById<ImageButton>(R.id.imagebutton_weeklyschedule_sidebar)
        addEventButton = findViewById<ImageButton>(R.id.imagebutton_weeklyschedule_add)
    }

    private fun setButtons() {
        addEventButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        })
    }

    private fun setAdapters() {
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

    private fun setSideBar() {
        toggleSidebar = ActionBarDrawerToggle(this, drawerLayout, R.string.sunday, R.string.friday)
        drawerLayout.addDrawerListener(toggleSidebar)
        toggleSidebar.syncState()

        sidebarButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView)
            } else {
                drawerLayout.openDrawer(navigationView)
            }
        }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                if (slideOffset > 0.3) {
                    sidebarButton.setImageResource(R.drawable.arrow_back_icon)
                } else {
                    sidebarButton.setImageResource(R.drawable.menu_icon)
                }
            }

            override fun onDrawerStateChanged(newState: Int) {}

            override fun onDrawerClosed(drawerView: View) {}

            override fun onDrawerOpened(drawerView: View) {}
        })

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_overview -> {
                    val intent = Intent(this, OverviewActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.menu_weekly_schedule -> {
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.menu_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            true
        }

        onCreateOptionsMenu(navigationView.menu)


        this.actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}