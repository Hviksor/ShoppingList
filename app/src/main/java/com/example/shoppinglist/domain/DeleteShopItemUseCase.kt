package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class DeleteShopItemUseCase(private val shopListRepositoryImpl: ShopListRepositoryImpl) {
    fun deleteShopItem(shopItem: ShopItem) {
        shopListRepositoryImpl.deleteShopItems(shopItem)
    }
}