package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class EditShopItemUseCase(private val shopListRepositoryImpl: ShopListRepositoryImpl) {
    fun editShopItems(shopItem: ShopItem) {
        shopListRepositoryImpl.deleteShopItems(shopItem)
    }
}