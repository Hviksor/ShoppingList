package com.example.shoppinglist.presentor

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name = itemView.findViewById<TextView>(R.id.tv_name)
    val count = itemView.findViewById<TextView>(R.id.tv_count)
}