package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class ShopItemViewModel : ViewModel() {
    val repo = ShopListRepositoryImpl
    val getShopItemUseCase = GetShopItemUseCase(repo)
    val addShopItemUseCase = AddShopItemUseCase(repo)
    val editShopItemUseCase = EditShopItemUseCase(repo)

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _errorName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorName

    private val _errorCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorCount

    private val _isCloseScreen = MutableLiveData<Unit>()
    val isCloseScreen: LiveData<Unit>
        get() = _isCloseScreen


    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }

    fun addShopItem(itemName: String?, itemCount: String?) {
        val name = parsName(itemName)
        val count = parsCount(itemCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem(itemName: String?, itemCount: String?) {
        val name = parsName(itemName)
        val count = parsCount(itemCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
            }
        }
    }

    private fun parsName(itemName: String?): String {
        return itemName?.trim() ?: ""
    }

    private fun parsCount(itemCount: String?): Int {
        return try {
            itemCount?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(itemName: String, itemCount: Int): Boolean {
        var result = true
        if (itemName.isBlank()) {
            result = false
            _errorName.value = true
        }
        if (itemCount == 0) {
            result = false
            _errorCount.value = true
        }
        return result
    }

    fun resetErrorName() {
        _errorName.value = false
    }

    fun resetErrorCount() {
        _errorCount.value = false
    }
    fun finishWork() {
        _isCloseScreen.value = Unit
    }

}