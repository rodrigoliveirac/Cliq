package com.rodcollab.cliq.features.bookings.collections.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rodcollab.cliq.features.bookings.collections.databinding.ItemBookingBinding
import com.rodcollab.cliq.features.bookings.collections.model.BookingItem
import java.util.*

class BookingsAdapter : RecyclerView.Adapter<BookingsAdapter.BookingViewHolder>() {

    private var asyncListDiffer: AsyncListDiffer<BookingItem> = AsyncListDiffer(this, DiffCallback)


    fun submitList(list: List<BookingItem>) {
        asyncListDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewBinding = ItemBookingBinding.inflate(layoutInflater)
        return BookingViewHolder(viewBinding)
    }

    override fun getItemCount() =
        asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.bind(item)
    }

    class BookingViewHolder(private val binding: ItemBookingBinding) : ViewHolder(binding.root) {

        fun bind(bookingItem: BookingItem) {
            binding.bookedClientName.text = bookingItem.bookedClientName
            binding.bookedAddressForm.text =  bookingItem.bookedClientAddress
            binding.bookedTimeForm.text = formatTextTime(bookingItem.bookedTime)
        }
        private fun formatTextTime(ms: Long): String {

            val hour = (ms / 1000) / 3600
            val minute = (ms / 1000 / 60) % 60

            return java.lang.String.format(
                Locale.getDefault(),
                "%02d:%02d",
                hour,
                minute,
            )
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<BookingItem>() {

        override fun areItemsTheSame(oldItem: BookingItem, newItem: BookingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookingItem, newItem: BookingItem): Boolean {
            return oldItem == newItem
        }
    }
}