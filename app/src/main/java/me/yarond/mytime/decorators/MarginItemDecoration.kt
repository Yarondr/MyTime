package me.yarond.mytime.decorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val topSize: Int, private val bottomSize: Int, private val leftSize: Int, private val rightSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = topSize
            }
            left = leftSize
            right = rightSize
            bottom = bottomSize
        }
    }
}