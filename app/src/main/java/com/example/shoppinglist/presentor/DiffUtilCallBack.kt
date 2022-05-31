package com.example.shoppinglist.presentor

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShopItem

class DiffUtilCallBack : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem): Boolean {
        return oldItem == newItem
    }
}