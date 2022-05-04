package com.example.shoppinglist.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {
    var shopList = listOf<ShopItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopListViewHolder, position: Int) {
        val shopItem = shopList[position]

        if (shopItem.enabled) {

            holder.count.text = shopItem.count.toString()
            holder.name.text = shopItem.name.toString()
            holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_dark))
        }

    }

    override fun onViewRecycled(holder: ShopListViewHolder) {
        super.onViewRecycled(holder)
        holder.count.text = ""
        holder.name.text = ""
        holder.name.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class ShopListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val count = itemView.findViewById<TextView>(R.id.tv_count)

    }
}