package com.example.shoppinglist.domain

class GetShopItemListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItemListUseCase(): List<ShopItem> {
        return shopListRepository.getShopItemListUseCase()
    }
}