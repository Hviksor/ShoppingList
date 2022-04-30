package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addShopItem(shopItemId: Int): ShopItem {
       return shopListRepository.addShopItem(shopItemId)
    }
}