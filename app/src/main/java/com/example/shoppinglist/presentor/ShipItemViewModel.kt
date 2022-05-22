package com.example.shoppinglist.presentor

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShipItemViewModel : ViewModel() {
    private val repo = ShopListRepositoryImpl
    private val getShopItemUseCase = GetShopItemUseCase(repo)
    private val addShopItemUseCase = AddShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem
    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName
    private val _errorCount = MutableLiveData<Boolean>()
    val errorCount: LiveData<Boolean>
        get() = _errorCount
    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getShopItem(shopItemId: Int) {
        val shopItem = getShopItemUseCase.getShopItems(shopItemId)
        _shopItem.value = shopItem
    }

    fun addShopItem(itemName: String?, itemCount: String?) {
        val name = parsName(itemName)
        val count = parsCount(itemCount)
        val validation = validateItem(name, count)
        if (validation) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItems(shopItem)
            closeScreen()
        }
    }

    fun editShopItem(itemName: String?, itemCount: String?) {
        val iname = parsName(itemName)
        val icount = parsCount(itemCount)
        val validation = validateItem(iname, icount)
        if (validation) {
            _shopItem.value?.let {
                val newShopItem = it.copy(name = iname, count = icount)
                editShopItemUseCase.editShopItems(newShopItem)
                closeScreen()
            }
        }
    }


    private fun validateItem(name: String, count: Int): Boolean {
        var result = true
        if (name.isEmpty()) {
            _errorName.value = true
            result = false
        }
        if (count <= 0) {
            _errorCount.value = true
            result = false
        }
        return result
    }

    private fun parsName(itemName: String?): String {
        val name = itemName?.trim() ?: ""
        return name
    }

    private fun parsCount(itemCount: String?): Int {
        return try {
            itemCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun closeScreen() {
        _shouldCloseScreen.value = Unit
    }

}