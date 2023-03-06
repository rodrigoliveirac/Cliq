package com.rodcollab.cliq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodcollab.cliq.ClientsAdapter.MyViewHolder

data class ClientItem(
    val id: String,
    val name: String,
    val reference: String,
    val address: String
)

class ClientsAdapter(private val dataSet: Array<ClientItem>) :
    RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_clients, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvClientName.text = dataSet[position].name
        holder.tvReference.text = dataSet[position].reference
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvClientName: TextView
        val tvReference: TextView

        init {
            tvClientName = view.findViewById(R.id.clientName)
            tvReference = view.findViewById(R.id.clientReference)
        }
    }
}
