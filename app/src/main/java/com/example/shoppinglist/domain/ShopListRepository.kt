package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.model.ShopItem

interface ShopListRepository {
    fun getShopItemList(): LiveData<List<ShopItem>>
    fun getShopItem(shopId: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
    fun editShopItem(shopItem: ShopItem)

}