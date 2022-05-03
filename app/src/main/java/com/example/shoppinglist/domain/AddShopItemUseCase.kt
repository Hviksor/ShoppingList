package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class AddShopItemUseCase(private val shopListRepositoryImpl: ShopListRepositoryImpl) {
    fun addShopItems(shopItem: ShopItem) {
        shopListRepositoryImpl.addShopItems(shopItem)
    }
}