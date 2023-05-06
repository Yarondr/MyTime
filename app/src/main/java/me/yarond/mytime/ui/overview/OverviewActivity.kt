package me.yarond.mytime.ui.overview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.R
import me.yarond.mytime.ui.events.AddEventActivity
import me.yarond.mytime.ui.settings.SettingsActivity
import me.yarond.mytime.ui.schedule.WeeklyScheduleActivity
import me.yarond.mytime.model.PendingEvent

class OverviewActivity : AppCompatActivity() {

    private lateinit var todayEventsLayoutManager: RecyclerView.LayoutManager
    private lateinit var todayEventsAdapter: RecyclerView.Adapter<PendingEventAdapter.ViewHolder>
    private lateinit var todayEventsRecyclerView: RecyclerView
    private lateinit var addImageButton: ImageButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggleSidebar: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView
    private lateinit var sidebarButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        setViews()
        setSideBar()
        setAdapters()
        setListeners()
    }

    private fun setViews() {
        todayEventsRecyclerView = findViewById<RecyclerView>(R.id.recyclerview_overview_today)
        addImageButton = findViewById<ImageButton>(R.id.imagebutton_overview_add)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout_overview)
        navigationView = findViewById<NavigationView>(R.id.navigationview_overview)
        sidebarButton = findViewById<ImageButton>(R.id.imagebutton_overview_sidebar)
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
                    drawerLayout.closeDrawer(navigationView)
                }
                R.id.menu_weekly_schedule -> {
                    val intent = Intent(this, WeeklyScheduleActivity::class.java)
                    startActivity(intent)
                    finish()
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


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setAdapters() {
        todayEventsLayoutManager = LinearLayoutManager(this)
        todayEventsRecyclerView.layoutManager = todayEventsLayoutManager

        val event1 = PendingEvent("Event 1", "10:00", "1.")
        val event2 = PendingEvent("Event 2", "11:00", "2.")

        todayEventsAdapter = PendingEventAdapter(arrayListOf(event1, event2))
        todayEventsRecyclerView.adapter = todayEventsAdapter
    }

    private fun setListeners() {
        addImageButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }
}