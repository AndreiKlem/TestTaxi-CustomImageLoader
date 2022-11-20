package ru.aklem.customimageloader.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.aklem.customimageloader.databinding.ItemOrderBinding
import ru.aklem.customimageloader.domain.model.Order

class OrdersAdapter(
    private val listener: OrderItemActionListener
) : PagingDataAdapter<Order, OrdersAdapter.OrdersViewHolder>(ItemCallback()), View.OnClickListener {

    override fun onClick(v: View) {
        val order = v.tag as Order
        listener.onOrderClick(order.id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemOrderBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val order = getItem(position) ?: return
        holder.itemView.tag = order
        with(holder.binding) {
            startAddressTv.text = order.startAddress.place
            endAddressTv.text = order.endAddress.place
            dateTv.text = order.date
            totalTv.text = order.price
        }
    }

    class OrdersViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)
}

class ItemCallback : DiffUtil.ItemCallback<Order>() {

    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }

}