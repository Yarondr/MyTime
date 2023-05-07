package me.yarond.mytime.ui.events

class ViewEventPresenter(private var view: ViewEventActivity) {

    fun backClicked() {
        view.finish()
    }
}