package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.domain.model.ShopItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_item WHERE id=:shopItemId LIMIT 1")
    fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("SELECT * FROM shop_item")
    fun getShopList(): LiveData<List<ShopItemDbModel>>

    @Query("DELETE FROM shop_item WHERE id=:shopItemId")
    fun deleteShopItem(shopItemId: Int)
}