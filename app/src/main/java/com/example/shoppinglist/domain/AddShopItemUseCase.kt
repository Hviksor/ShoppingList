package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}