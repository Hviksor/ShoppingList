package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editShopItem(shopItem: ShopItem) {
        shopListRepository.editShopItem(shopItem)
    }
}