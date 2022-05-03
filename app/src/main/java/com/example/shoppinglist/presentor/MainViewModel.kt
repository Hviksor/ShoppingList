package com.example.shoppinglist.presentor

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class MainViewModel : ViewModel() {
    private val repo = ShopListRepositoryImpl
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repo)
    private val getShopItemListUseCase = GetShopListUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)
    val shopList = getShopItemListUseCase.getShopList()


    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeShopItemState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }


}