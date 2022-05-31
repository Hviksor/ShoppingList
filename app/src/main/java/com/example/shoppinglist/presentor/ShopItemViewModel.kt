package com.example.shoppinglist.presentor

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import java.sql.RowId

class ShopItemViewModel : ViewModel() {

    private val repo = ShopListRepositoryImpl
    private val addShopItemUseCase = AddShopItemUseCase(repo)
    private val editShopItemUseCase = EditShopItemUseCase(repo)
    private val getShopItemUseCase = GetShopItemUseCase(repo)


    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _errorName = MutableLiveData<Boolean>()
    val errorName: LiveData<Boolean>
        get() = _errorName

    private val _errorCount = MutableLiveData<Boolean>()
    val errorCount: LiveData<Boolean>
        get() = _errorCount

    private val _finishWork = MutableLiveData<Unit>()
    val finishWork: LiveData<Unit>
        get() = _finishWork


    fun getShopItem(shopItemId: Int) {
        _shopItem.value = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem(name: String?, count: String?) {
        val parsName = parseName(name)
        val parsCount = parseCount(count)
        val validField = validator(parsName, parsCount)
        if (validField) {
            val item = ShopItem(parsName, parsCount, true)
            addShopItemUseCase.addShopItem(item)
            shouldCloseScreen()
        }
    }

    fun editShopItem(name: String?, count: String?) {
        val parsName = parseName(name)
        val parsCount = parseCount(count)
        val validField = validator(parsName, parsCount)
        if (validField) {
            val item = ShopItem(parsName, parsCount, true)
            editShopItemUseCase.editShopItem(item)
        }
        shouldCloseScreen()
    }

    private fun validator(parsName: String, parsCount: Int): Boolean {
        var result = true
        if (parsName.isEmpty()) {
            result = false
            _errorName.value = true
        }
        if (parsCount <= 0) {
            result = false
            _errorCount.value = true
        }
        return result
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }


    fun resetErrorName() {
        _errorName.value = false
    }

    fun resetErrorCount() {
        _errorCount.value = false
    }

    fun shouldCloseScreen() {
        _finishWork.value = Unit
    }


}
