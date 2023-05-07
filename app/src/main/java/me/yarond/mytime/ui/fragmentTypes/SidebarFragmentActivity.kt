package me.yarond.mytime.ui.fragmentTypes

import android.widget.ImageButton
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.ui.Sidebar

open class SidebarFragmentActivity(): DefaultFragmentActivity(), Sidebar {
    override lateinit var drawerLayout: DrawerLayout
    override lateinit var navigationView: NavigationView
    override lateinit var sidebarButton: ImageButton
}