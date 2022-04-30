package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var idAutoincrement = 0

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.DEFAULT_ID) {
            shopItem.id = idAutoincrement++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        deleteShopItem(oldShopItem)
        addShopItem(shopItem)
    }

    override fun getShopItemList(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with id $shopItemId not found")

    }
}