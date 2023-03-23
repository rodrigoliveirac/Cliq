package com.rodcollab.form.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.features.bookings.form.databinding.ItemSingleLineBinding
import com.rodcollab.form.model.ClientItem
import com.rodcollab.form.ui.SearchClientViewModel

class SearchClientListAdapter(private var viewModel: SearchClientViewModel) :
    RecyclerView.Adapter<SearchClientListAdapter.SearchClientListViewHolder>() {


    private val asyncListDif: AsyncListDiffer<ClientItem> = AsyncListDiffer(this, DiffCallback)

    fun submitList(list: List<ClientItem>) {
        asyncListDif.submitList(list)
    }

    class SearchClientListViewHolder(
        private val viewModel: SearchClientViewModel,
        private val binding: ItemSingleLineBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ClientItem) = binding.apply {
            itemText.text = item.name

            root.setOnClickListener {
                viewModel.onItemClicked(item.id)
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