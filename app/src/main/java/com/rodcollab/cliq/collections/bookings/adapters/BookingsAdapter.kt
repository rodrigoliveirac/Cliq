package com.rodcollab.cliq.collections.bookings.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rodcollab.cliq.collections.bookings.model.BookingItem
import com.rodcollab.cliq.databinding.ItemBookingBinding

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
            binding.bookedTime.text = "Horário: ${bookingItem.bookedTime}"
            binding.bookedClientName.text = "Nome do cliente: ${bookingItem.clientName}"
            binding.bookedDate.text = bookingItem.bookedDate
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