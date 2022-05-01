package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemListUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemListUseCase = EditShopItemUseCase(repository)
    val shopList = getShopItemListUseCase.getShopItemList()




    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemListUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemListUseCase.editShopItem(newShopItem)
    }
}