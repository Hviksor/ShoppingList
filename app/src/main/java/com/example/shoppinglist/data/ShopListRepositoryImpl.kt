package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopItem.Companion.ID_DEFAULT
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {
    private val listShop = mutableListOf<ShopItem>()
    private val listShopLD = MutableLiveData<List<ShopItem>>()
    private var autoincrementID = 0

    init {
        for (i in 0 until 10) {
            addShopItems(ShopItem("Name $i", 1, true))

        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return listShopLD
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return listShop.find { it.id == shopItemId } ?: throw RuntimeException("qwdqwd")
    }

    override fun addShopItems(shopItem: ShopItem) {
        if (shopItem.id == ID_DEFAULT) {
            shopItem.id = autoincrementID++
        }
        listShop.add(shopItem)
        updateListLD()
    }

    override fun deleteShopItems(shopItem: ShopItem) {
        listShop.remove(shopItem)
        updateListLD()
    }

    override fun editShopItems(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        deleteShopItems(oldShopItem)
        addShopItems(shopItem)
        updateListLD()
    }

    private fun updateListLD() {
        listShopLD.value = listShop.toList()
    }
}