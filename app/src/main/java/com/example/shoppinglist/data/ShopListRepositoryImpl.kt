package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.model.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private val shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private var autoIncrementId = 0

    init {
        for (i in 0 until 8) {
            addShopItem(ShopItem("Name $i", 1, Random.nextBoolean()))


        }
    }


    override fun getShopItemList(): LiveData<List<ShopItem>> {

        return shopListLD
    }

    override fun getShopItem(shopId: Int): ShopItem {
        return shopList.find { it.id == shopId } ?: throw RuntimeException("kokoko")

    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.DEFAULT_ID) {
            shopItem.id = autoIncrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItem(shopItem.id)
        deleteShopItem(oldShopItem)
        addShopItem(shopItem)
        updateList()
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()

    }
}