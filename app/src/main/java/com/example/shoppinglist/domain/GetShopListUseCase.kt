package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.ShopListRepositoryImpl

class GetShopListUseCase(private val shopListRepositoryImpl: ShopListRepositoryImpl) {
    fun getShopList(): LiveData<List<ShopItem>> {
        return shopListRepositoryImpl.getShopList()
    }
}