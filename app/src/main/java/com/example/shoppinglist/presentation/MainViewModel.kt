package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)
    private val getShopItemListUseCase = GetShopItemListUseCase(repository)
    private val deleteShopItemListUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemListUseCase = EditShopItemUseCase(repository)
    val shopList = getShopItemListUseCase.getShopItemList()


    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemListUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
            editShopItemListUseCase.editShopItem(newShopItem)
        }
    }
}