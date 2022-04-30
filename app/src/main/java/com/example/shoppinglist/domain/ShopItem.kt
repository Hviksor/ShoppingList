package com.example.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = INDEFINED_ID
) {
    companion object {
        const val INDEFINED_ID = -1
    }
}
