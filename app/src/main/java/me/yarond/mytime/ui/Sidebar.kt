package me.yarond.mytime.ui

import android.widget.ImageButton
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import me.yarond.mytime.R

interface Sidebar {

    var drawerLayout: DrawerLayout
    var navigationView: NavigationView
    var sidebarButton: ImageButton

    fun isDrawerLayoutOpen(): Boolean {
        return drawerLayout.isDrawerOpen(navigationView)
    }

    fun closeDrawerLayout() {
        drawerLayout.closeDrawer(navigationView)
    }

    fun openDrawerLayout() {
        drawerLayout.openDrawer(navigationView)
    }

    fun closeSidebarArrowImage() {
        sidebarButton.setImageResource(R.drawable.arrow_back_icon)
    }

    fun openSidebarMenuImage() {
        sidebarButton.setImageResource(R.drawable.menu_icon)
    }
}