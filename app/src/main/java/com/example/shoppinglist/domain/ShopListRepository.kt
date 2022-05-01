package com.example.shoppinglist.domain

interface ShopListRepository {
    fun getShopItemListUseCase(): List<ShopItem>
    fun getShopItemUseCase(shopId: Int): ShopItem
    fun addShopItemUseCase(shopItem: ShopItem)
    fun deleteShopItemUseCase(shopItem: ShopItem)
    fun editShopItemUseCase(shopItem: ShopItem)

}