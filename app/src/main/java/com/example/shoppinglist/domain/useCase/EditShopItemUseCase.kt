package com.example.shoppinglist.domain.useCase

import com.example.shoppinglist.domain.model.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}