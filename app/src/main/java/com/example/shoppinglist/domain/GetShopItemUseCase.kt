package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItem(shopId: Int): ShopItem {
        return shopListRepository.getShopItemUseCase(shopId)
    }
}