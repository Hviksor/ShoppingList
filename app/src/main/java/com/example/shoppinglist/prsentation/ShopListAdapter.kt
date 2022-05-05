package com.example.shoppinglist.prsentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            LAYOUT_ENABLED -> R.layout.item_shop_enabled
            LAYOUT_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Неизвестный ViewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        val shopItem = shopList[position]
        holder.tvName.text = "${shopItem.name} "
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnClickListener {
            true
        }
    }
    override fun getItemCount(): Int {
        return shopList.size
    }
    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enabled) {
            LAYOUT_ENABLED
        } else {
            LAYOUT_DISABLED
        }
    }

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val tvCount = itemView.findViewById<TextView>(R.id.tv_count)
    }

    companion object {
        const val LAYOUT_ENABLED = 1
        const val LAYOUT_DISABLED = 0

    }
}