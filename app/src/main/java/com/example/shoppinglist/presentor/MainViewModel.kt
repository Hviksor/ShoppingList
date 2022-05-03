package com.example.shoppinglist.presentor

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repo = ShopListRepositoryImpl
    val shopList = repo.getShopList()

    fun addShop(shopItem: ShopItem) {
        repo.addShopItems(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        repo.addShopItems(shopItem)
    }

    fun changeShopItem(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        repo.editShopItems(newShopItem)
    }
}