package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShopListRepository {
    fun getShopItemListUseCase(): LiveData<List<ShopItem>>
    fun getShopItemUseCase(shopId: Int): ShopItem
    fun addShopItemUseCase(shopItem: ShopItem)
    fun deleteShopItemUseCase(shopItem: ShopItem)
    fun editShopItemUseCase(shopItem: ShopItem)

}