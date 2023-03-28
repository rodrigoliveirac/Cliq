package com.rodcollab.cliq.features.bookings.collections.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class BookingListLifecycleObserver(private val viewModel: BookingListViewModel) :
    DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }

}
