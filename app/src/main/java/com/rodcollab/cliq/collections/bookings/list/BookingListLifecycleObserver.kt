package com.rodcollab.cliq.collections.bookings.list

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class BookingListLifecycleObserver(private val viewModel: BookingListViewModel) :
    DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }

}
