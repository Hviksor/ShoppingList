package com.example.shoppinglist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {
    var shopList = listOf<ShopItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            val diffCallback = DiffUtilShopItem(shopList, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }
    var onShopItemLongClick: ((ShopItem) -> Unit)? = null
    var onShopItemClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val layout = when (viewType) {
            LAYOUT_ENABLED -> R.layout.item_shop_enabled
            LAYOUT_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Неизвестный ViewType: $viewType")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val item = shopList[position]
        holder.name.text = item.name
        holder.count.text = item.count.toString()
        holder.itemView.setOnLongClickListener {
            onShopItemLongClick?.invoke(item)
            true
        }
        holder.itemView.setOnClickListener {
            onShopItemClick?.invoke(item)
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

    class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val count = itemView.findViewById<TextView>(R.id.tv_count)
    }

    companion object {
        const val LAYOUT_ENABLED = 1
        const val LAYOUT_DISABLED = 0
    }


}