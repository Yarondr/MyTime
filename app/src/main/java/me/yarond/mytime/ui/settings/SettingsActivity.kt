package me.yarond.mytime.ui.settings

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.ui.activityTypes.SidebarActivity
import me.yarond.mytime.R
import me.yarond.mytime.ui.UtilPresenter
import me.yarond.mytime.ui.overview.OverviewActivity
import me.yarond.mytime.ui.schedule.WeeklyScheduleActivity

class SettingsActivity : SidebarActivity() {

    private lateinit var themeSwitch: SwitchCompat
    private lateinit var toggleSidebar: ActionBarDrawerToggle
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        presenter = SettingsPresenter(this)
        setViews()
        setSideBar()
        setListeners()
        presenter.setInitialThemeSwitchStatus()
    }

    private fun setViews() {
        themeSwitch = findViewById(R.id.switch_settings_theme)
        drawerLayout = findViewById(R.id.drawerlayout_settings)
        navigationView = findViewById(R.id.navigationview_settings)
        sidebarButton = findViewById(R.id.imagebutton_settings_sidebar)
        toggleSidebar = ActionBarDrawerToggle(this, drawerLayout, R.string.sunday, R.string.friday)
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
                    val intent = Intent(this, WeeklyScheduleActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.menu_settings -> {
                    drawerLayout.closeDrawer(navigationView)
                }
            }
            true
        }

        onCreateOptionsMenu(navigationView.menu)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setListeners() {
        themeSwitch.setOnCheckedChangeListener { _, isChecked -> presenter.themeSwitchToggle(isChecked) }
    }

    fun setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun isDarkThemeOnStart(): Boolean {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    }

    fun setThemeSwitchStatus(checked: Boolean) {
        themeSwitch.isChecked = checked
    }
}