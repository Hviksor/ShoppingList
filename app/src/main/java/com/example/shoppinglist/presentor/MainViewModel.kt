package com.example.shoppinglist.presentor

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    val repo = ShopListRepositoryImpl
    val shopList = repo.getShopItemListUseCase()

    fun deleteShopItem(shopItem: ShopItem) {
        repo.deleteShopItemUseCase(shopItem)
    }

    fun changeEnabled(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        repo.editShopItemUseCase(newItem)
    }


}