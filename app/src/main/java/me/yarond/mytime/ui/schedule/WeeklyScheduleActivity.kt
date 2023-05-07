package me.yarond.mytime.ui.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import me.yarond.mytime.R
import me.yarond.mytime.model.Day
import me.yarond.mytime.ui.settings.SettingsActivity
import me.yarond.mytime.model.PendingEvent
import me.yarond.mytime.ui.activityTypes.SidebarActivity
import me.yarond.mytime.ui.events.AddEventActivity
import me.yarond.mytime.ui.fragmentTypes.SidebarFragmentActivity
import me.yarond.mytime.ui.overview.OverviewActivity

class WeeklyScheduleActivity : SidebarFragmentActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var toggleSidebar: ActionBarDrawerToggle
    private lateinit var addEventButton: ImageButton
    private lateinit var presenter: WeeklySchedulePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_schedule)
        presenter = WeeklySchedulePresenter(this)
        setViews()
        setSideBar()
        setButtons()
        setAdapters()
    }

    private fun setViews() {
        viewPager = findViewById(R.id.viewpager_weeklyschedule)
        tabLayout = findViewById(R.id.tablayout_weeklyschedule)
        drawerLayout = findViewById(R.id.drawerlayout_weeklyschedule)
        navigationView = findViewById(R.id.navigationview_weeklyschedule)
        sidebarButton = findViewById(R.id.imagebutton_weeklyschedule_sidebar)
        addEventButton = findViewById(R.id.imagebutton_weeklyschedule_add)
    }

    private fun setButtons() {
        addEventButton.setOnClickListener{ presenter.createNewEvent() }
    }

    private fun setAdapters() {
        val adapter = DaySchedulePagerAdapter(this)

        Day.values().iterator().forEach {
            adapter.addFragment(presenter.getDayScheduleFragment(it), getStringFromResourceId(it.shortName))
        }

        Day.values().iterator().forEach {
            tabLayout.addTab(tabLayout.newTab().setText(getStringFromResourceId(it.shortName)))
        }

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

        sidebarButton.setOnClickListener { presenter.sidebarButtonClicked() }

        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                presenter.onDrawerLayoutSlide(slideOffset)
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

    fun setSidebarButtonImageResource(resourceId: Int) {
        sidebarButton.setImageResource(resourceId)
    }

    fun getArrowBackResourceId(): Int {
        return R.drawable.arrow_back_icon
    }

    fun getMenuIconResourceId(): Int {
        return R.drawable.menu_icon
    }

    fun getAddEventActivityIntent(): Intent {
        return Intent(this, AddEventActivity::class.java)
    }

    private fun getStringFromResourceId(resourceId: Int): String {
        return resources.getString(resourceId)
    }

    fun getStringFromResourceId(resourceId: String): String {
        return getStringFromResourceId(Integer.parseInt(resourceId))
    }
}