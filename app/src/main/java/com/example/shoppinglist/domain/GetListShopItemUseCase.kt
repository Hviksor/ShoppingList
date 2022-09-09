package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.ShopListRepositoryImpl

class GetListShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getListShopItem(): LiveData<List<ShopItem>> {
        return shopListRepository.getListShopItem()
    }
}