package com.example.shoppinglist.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getShopListDao(): ShopListDao


    companion object {
        private const val DB_NAME = "shop_item_db"
        private val LOC = Any()
        private var INSTANCE: AppDatabase? = null

        fun getInstance(application: Application): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOC) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, AppDatabase::class.java, DB_NAME).build()
                INSTANCE = db
                return db
            }
        }

    }
}