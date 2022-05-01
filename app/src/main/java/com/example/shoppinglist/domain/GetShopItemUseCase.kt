package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItemUseCase(shopId: Int): ShopItem {
        return shopListRepository.getShopItemUseCase(shopId)
    }
}