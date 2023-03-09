package com.rodcollab.cliq.collections.clients.form

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.collections.bookings.form.BookingFormViewModel
import com.rodcollab.cliq.collections.clients.model.ClientItem
import com.rodcollab.cliq.databinding.ItemSingleLineBinding

class SearchClientListAdapter(private var viewModel: BookingFormViewModel) :
    RecyclerView.Adapter<SearchClientListAdapter.SearchClientListViewHolder>() {


    private val asyncListDif: AsyncListDiffer<ClientItem> = AsyncListDiffer(this, DiffCallback)

    fun submitList(list: List<ClientItem>) {
        asyncListDif.submitList(list)
    }

    class SearchClientListViewHolder(
        private val viewModel: BookingFormViewModel,
        private val binding: ItemSingleLineBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ClientItem) = binding.apply {
            itemText.text = item.name

            root.setOnClickListener {
                viewModel.onItemClicked(item.name)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchClientListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = ItemSingleLineBinding.inflate(layoutInflater)
        return SearchClientListViewHolder(viewModel, view)
    }

    override fun getItemCount() = asyncListDif.currentList.size

    override fun onBindViewHolder(holder: SearchClientListViewHolder, position: Int) {
        val item = asyncListDif.currentList[position]
        holder.bind(item)
    }

    object DiffCallback : DiffUtil.ItemCallback<ClientItem>() {
        override fun areItemsTheSame(oldItem: ClientItem, newItem: ClientItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ClientItem, newItem: ClientItem): Boolean {
            return oldItem.name == newItem.name
        }
    }
}