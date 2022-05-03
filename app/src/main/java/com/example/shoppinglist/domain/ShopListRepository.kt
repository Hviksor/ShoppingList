package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopList(): LiveData<List<ShopItem>>
    fun getShopItem(shopItemId: Int): ShopItem
    fun addShopItems(shopItem: ShopItem)
    fun deleteShopItems(shopItem: ShopItem)
    fun editShopItems(shopItem: ShopItem)
}