package com.example.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val isPressed: Boolean,
    var id: Int = DEFAULT_ID

) {
    companion object {
        const val DEFAULT_ID = -1
    }
}