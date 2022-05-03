package com.example.shoppinglist.domain

data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = ID_DEFAULT

) {
    companion object {
        const val ID_DEFAULT = -1
    }
}