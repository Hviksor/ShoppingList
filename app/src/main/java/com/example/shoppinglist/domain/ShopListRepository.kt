package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopItem(shopItemId: Int): ShopItem
    fun getListShopItem(): LiveData<List<ShopItem>>
    fun editShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun addShopItem(shopItem: ShopItem)

}