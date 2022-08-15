package com.example.shoppinglist.domain.useCase

import com.example.shoppinglist.domain.model.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}