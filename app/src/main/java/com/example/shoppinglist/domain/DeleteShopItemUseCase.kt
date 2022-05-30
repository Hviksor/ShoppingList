package com.example.shoppinglist.domain

import com.example.shoppinglist.data.ShopListRepositoryImpl

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun deleteShopItem(shopItem: ShopItem) {
        shopListRepository.deleteShopItem(shopItem)
    }
}