package com.example.shoppinglist.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlin.random.Random

object ShopListRepositoryImpl : ShopListRepository {
    private var shopList = sortedSetOf<ShopItem>({ a1, a2 -> a1.id.compareTo(a2.id) })
    private val shopItemListLD = MutableLiveData<List<ShopItem>>()
    private var index = 0

    init {
        for (i in 0 until 10) {
            val item = ShopItem("Shop item name $i", 1, Random.nextBoolean())
            addShopItem(item)
        }
    }


    override fun getShopItem(shopItemId: Int): ShopItem {
        Log.e("shopItemId", shopItemId.toString())
        return shopList.find { it.id == shopItemId } ?: throw RuntimeException("kokok")

    }

    override fun getListShopItem(): LiveData<List<ShopItem>> {
        return shopItemListLD
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        shopList.remove(oldItem)
        val newItem = shopItem.copy(id = oldItem.id)
        addShopItem(newItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.DEFAULT_ID) {
            shopItem.id = index++
        }
        shopList.add(shopItem)
        updateList()
    }

    private fun updateList() {
        shopItemListLD.value = shopList.toList()
    }
}