package com.rodcollab.cliq.collections

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.collections.ClientsAdapter.MyViewHolder
import com.rodcollab.cliq.databinding.ItemClientsBinding

class ClientsAdapter :
    RecyclerView.Adapter<MyViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<ClientItem> = AsyncListDiffer(this, DiffCallback)

    fun submitList(list: List<ClientItem>) {
        asyncListDiffer.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemClientsBinding.inflate(layoutInflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.bind(clientItem = item)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class MyViewHolder(private val binding: ItemClientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clientItem: ClientItem) {
            binding.clientName.text = clientItem.name
            binding.clientReference.text = clientItem.reference
        }

    }

    object DiffCallback : DiffUtil.ItemCallback<ClientItem>() {

        override fun areItemsTheSame(oldItem: ClientItem, newItem: ClientItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ClientItem, newItem: ClientItem): Boolean {
            return oldItem == newItem
        }
    }

}
