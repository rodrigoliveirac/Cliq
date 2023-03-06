package com.rodcollab.cliq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.ClientsAdapter.MyViewHolder
import com.rodcollab.cliq.dummy.MockClients

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
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clients, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
        holder.tvClientName.text = item.name
        holder.tvReference.text = item.reference
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvClientName: TextView
        val tvReference: TextView

        init {
            tvClientName = view.findViewById(R.id.clientName)
            tvReference = view.findViewById(R.id.clientReference)
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
