package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShopItemListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopItemListUseCase(): LiveData<List<ShopItem>> {
        return shopListRepository.getShopItemListUseCase()
    }
}