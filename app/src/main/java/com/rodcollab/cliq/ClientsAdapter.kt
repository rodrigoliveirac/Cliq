package com.rodcollab.cliq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.ClientsAdapter.MyViewHolder
import com.rodcollab.cliq.databinding.ItemClientsBinding

data class ClientItem(
    val id: String,
    val name: String,
    val reference: String,
    val address: String
)

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
        holder.tvClientName.text = item.name
        holder.tvReference.text = item.reference
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class MyViewHolder(binding: ItemClientsBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvClientName: TextView
        val tvReference: TextView

        init {
            tvClientName = binding.clientName
            tvReference = binding.clientReference
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
