package me.yarond.mytime.ui

class UtilPresenter {

    companion object {
        fun sidebarButtonClicked(view: Sidebar) {
            if (view.isDrawerLayoutOpen()) {
                view.closeDrawerLayout()
            } else {
                view.openDrawerLayout()
            }
        }

        fun onDrawerLayoutSlide(view: Sidebar, slideOffset: Float) {
            if (slideOffset > 0.3) {
                view.closeSidebarArrowImage()
            } else {
                view.openSidebarMenuImage()
            }
        }
    }
}