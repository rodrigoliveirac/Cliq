package com.rodcollab.cliq.core

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.rodcollab.cliq.R

class Utils(private val context:Context) {

    fun addingDividerDecoration() : MaterialDividerItemDecoration {
        // Adding Line between items with MaterialDividerItemDecoration
        val divider = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)

        // Removing the line at the end of the list
        divider.isLastItemDecorated = false

        val resources = context.resources

        // Adding start spacing
        divider.dividerInsetStart = resources.getDimensionPixelSize(R.dimen.horizontal_margin)

        // Defining size of the line
        divider.dividerThickness = resources.getDimensionPixelSize(R.dimen.divider_height)
        divider.dividerColor = ContextCompat.getColor(context, R.color.primary_200)

        return divider

    }
}