package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class GetShopItemUseCase(private val shopListRepositoryImpl: ShopListRepositoryImpl) {
    fun getShopItems(shopItemId: Int): ShopItem {
        return shopListRepositoryImpl.getShopItem(shopItemId)
    }
}