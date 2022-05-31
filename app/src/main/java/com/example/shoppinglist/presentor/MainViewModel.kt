package com.example.shoppinglist.presentor

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetListShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repo = ShopListRepositoryImpl
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    val listShopItem = GetListShopItemUseCase(repo).getListShopItem()


    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeStateShopItem(shopItem: ShopItem) {
        val newItem = shopItem.copy(isPressed = !shopItem.isPressed)
        editShopItemUseCase.editShopItem(newItem)
    }
}