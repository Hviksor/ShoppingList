package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import java.lang.RuntimeException
import kotlin.random.Random

class ShopListRepositoryImpl(application: Application) : ShopListRepository {
    private val dao = AppDatabase.getInstance(application).getShopListDao()
    private val mapper = ShopItemMapper()

    override suspend fun addShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        dao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        dao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override fun getShopItemList(): LiveData<List<ShopItem>> {
        return MediatorLiveData<List<ShopItem>>().apply {
            addSource(dao.getShopItemList()) {
                value = mapper.maoListDbModelToListEntity(it)
            }
        }
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        return mapper.mapDbModelToEntity(dao.getShopItem(shopItemId))
    }

}