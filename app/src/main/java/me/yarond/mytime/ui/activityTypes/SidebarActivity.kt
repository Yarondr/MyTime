package me.yarond.mytime.ui.activityTypes

import android.widget.ImageButton
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.R
import me.yarond.mytime.ui.Sidebar

open class SidebarActivity(): DefaultActivity(), Sidebar {
    override lateinit var drawerLayout: DrawerLayout
    override lateinit var navigationView: NavigationView
    override lateinit var sidebarButton: ImageButton
}