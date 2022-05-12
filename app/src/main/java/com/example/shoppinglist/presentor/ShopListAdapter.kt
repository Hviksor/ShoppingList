package com.example.shoppinglist.presentor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffUtil()) {

    val onShopItemClick: ((ShopItem) -> Unit)? = null
    val onShopItemLongClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            ENABLED_VIEW -> R.layout.item_shop_enabled
            DISABLED_VIEW -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Какая то фигня")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }


    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.count.text = item.count.toString()

        holder.itemView.setOnClickListener {
            onShopItemClick?.invoke(item)
        }

        holder.itemView.setOnLongClickListener {
            onShopItemLongClick?.invoke(item)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            return ENABLED_VIEW
        } else {
            DISABLED_VIEW
        }
    }

    companion object {
        const val ENABLED_VIEW = 1
        const val DISABLED_VIEW = 0
    }
}