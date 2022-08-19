package com.example.shoppinglist.domain.useCase

import com.example.shoppinglist.domain.model.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItem(shopItemId: Int): ShopItem {
        return shopListRepository.getShopItem(shopItemId)
    }
}