package com.example.shoppinglist.presentor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListAdapter.ShopListViewHolder>(DiffUtilCallBack()) {
    var shopItemClick: ((ShopItem) -> Unit)? = null
    var shopItemLongClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            ENABLED_VIEW_TYPE -> R.layout.enabled_item_layout
            DISABLED_VIEW_TYPE -> R.layout.disbled_item_layout
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopListViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.count.text = item.count.toString()
        holder.itemView.setOnClickListener {
            shopItemClick?.invoke(item)
        }
        holder.itemView.setOnLongClickListener {
            shopItemLongClick?.invoke(item)
            true
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isPressed) {
            ENABLED_VIEW_TYPE
        } else {
            DISABLED_VIEW_TYPE
        }
    }

    class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val count = itemView.findViewById<TextView>(R.id.tv_count)

    }

    companion object {
        private const val ENABLED_VIEW_TYPE = -1
        private const val DISABLED_VIEW_TYPE = 1
    }
}