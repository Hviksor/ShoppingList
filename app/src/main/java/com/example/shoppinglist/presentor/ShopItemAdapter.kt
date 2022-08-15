package com.example.shoppinglist.presentor

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBindingImpl
import com.example.shoppinglist.domain.ShopItem
import java.lang.RuntimeException

class ShopItemAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(DiffUtillll()) {

    var shopItemLongClick: ((ShopItem) -> Unit)? = null
    var shopItemClick: ((ShopItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("kokoko")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
//        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        when (binding) {
            is ItemShopEnabledBinding -> {
                binding.tvCount.text = shopItem.count.toString()
                binding.tvName.text = shopItem.name
            }
            is ItemShopDisabledBinding -> {
                binding.tvCount.text = shopItem.count.toString()
                binding.tvName.text = shopItem.name
            }
        }

        binding.root.setOnLongClickListener {
            shopItemLongClick?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            shopItemClick?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0

    }
}