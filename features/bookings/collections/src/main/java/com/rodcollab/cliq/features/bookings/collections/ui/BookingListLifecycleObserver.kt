package com.rodcollab.cliq.features.bookings.collections.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class BookingListLifecycleObserver(private val viewModel: BookingListViewModel) :
    DefaultLifecycleObserver {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }

}
