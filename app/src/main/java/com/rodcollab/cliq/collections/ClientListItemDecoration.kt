package com.rodcollab.cliq.collections

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.R

class ClientListItemDecoration(context : Context) : RecyclerView.ItemDecoration() {
    private val space = context.resources.getDimensionPixelSize(R.dimen.simple_margin)

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space
        outRect.bottom = space
        outRect.top = space
    }
}