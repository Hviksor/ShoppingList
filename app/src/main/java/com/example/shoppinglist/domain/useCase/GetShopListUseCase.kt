package com.example.shoppinglist.domain.useCase

import androidx.lifecycle.LiveData
import com.example.shoppinglist.domain.model.ShopItem
import com.example.shoppinglist.domain.ShopListRepository

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList(): LiveData<List<ShopItem>> {
       return shopListRepository.getShopItemList()
    }
}