package com.rodcollab.cliq.collections.clients.list

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class ClientListLifecycleObserver(
    private val viewModel: ClientListViewModel
) : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}