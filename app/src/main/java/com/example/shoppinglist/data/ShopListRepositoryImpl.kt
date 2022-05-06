package com.example.shoppinglist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            addShopItemUseCase(ShopItem("Name $i", 1, Random.nextBoolean()))
        }
    }


    override fun getShopItemListUseCase(): LiveData<List<ShopItem>> {
        return shopListLD
    }

    override fun getShopItemUseCase(shopId: Int): ShopItem {
        return shopList.find { it.id == shopId } ?: throw RuntimeException("kokoko")
    }

    override fun addShopItemUseCase(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.DEFAULT_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItemUseCase(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItemUseCase(shopItem: ShopItem) {
        val oldShopItem = getShopItemUseCase(shopItem.id)
        deleteShopItemUseCase(oldShopItem)
        addShopItemUseCase(shopItem)
        updateList()
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}