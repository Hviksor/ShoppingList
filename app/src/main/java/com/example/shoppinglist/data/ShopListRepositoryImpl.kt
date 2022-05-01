package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException

class ShopListRepositoryImpl : ShopListRepository {
    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementId = 0


    override fun getShopItemListUseCase(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItemUseCase(shopId: Int): ShopItem {
        return shopList.find { it.id == shopId } ?: throw RuntimeException("kokoko")

    }

    override fun addShopItemUseCase(shopItem: ShopItem) {
        if (autoIncrementId == ShopItem.DEFAULT_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
    }

    override fun deleteShopItemUseCase(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItemUseCase(shopItem: ShopItem) {
        val oldShopItem = getShopItemUseCase(shopItem.id)
        deleteShopItemUseCase(oldShopItem)
        addShopItemUseCase(shopItem)
    }
}