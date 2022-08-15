package com.example.shoppinglist.prsentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ItemShopDisabledBinding
import com.example.shoppinglist.databinding.ItemShopEnabledBinding
import com.example.shoppinglist.domain.model.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            LAYOUT_ENABLED -> R.layout.item_shop_enabled
            LAYOUT_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Неизвестный ViewType: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )

        return ShopItemViewHolder(binding)
    }

    var onShopItemOnLongClick: ((ShopItem) -> Unit)? = null
    var onShopClick: ((ShopItem) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        val shopItem = getItem(position)
        val binding = holder.binding
        when (binding) {
            is ItemShopEnabledBinding -> {
                binding.shopItem = shopItem
            }
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
        }

        binding.root.setOnLongClickListener {
            onShopItemOnLongClick?.invoke(shopItem)
            true
        }
        binding.root.setOnClickListener {
            onShopClick?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) {
            LAYOUT_ENABLED
        } else {
            LAYOUT_DISABLED
        }
    }

    companion object {
        const val LAYOUT_ENABLED = 1
        const val LAYOUT_DISABLED = 0
        const val MAX_RECYCLER_ITEM = 15
    }
}