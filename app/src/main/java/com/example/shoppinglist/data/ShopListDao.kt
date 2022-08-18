package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItemDbModel: ShopItemDbModel)

    @Query("SELECT * FROM shop_item_table")
    fun getShopItemList(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_item_table WHERE id=:shopItemId")
    fun getShopItem(shopItemId: Int): ShopItemDbModel

    @Query("DELETE FROM shop_item_table WHERE id=:shopItemId")
    fun deleteShopItem(shopItemId: Int)

}