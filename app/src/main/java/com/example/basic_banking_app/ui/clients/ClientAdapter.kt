package com.example.basic_banking_app.ui.clients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.basic_banking_app.data.model.Client
import com.example.basic_banking_app.databinding.ItemClientListBinding

class ClientAdapter(private val clickListener: ItemClickListener) :
    ListAdapter<Client, ClientAdapter.ClientViewHolder>(DiffCallback()) {

    class ClientViewHolder(private val binding: ItemClientListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(client: Client, clickListener: ItemClickListener) {
            binding.client = client
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemClientListBinding.inflate(layoutInflater, parent, false)
        return ClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class DiffCallback : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem.client_id == newItem.client_id
        }

        override fun areContentsTheSame(oldItem:Client , newItem: Client): Boolean {
            return oldItem == newItem
        }
    }

    class ItemClickListener(val clickListener: (client: Client) -> Unit) {
        fun onClick(client: Client) = clickListener(client)
    }
}