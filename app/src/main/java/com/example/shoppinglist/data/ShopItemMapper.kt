package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShopItem

class ShopItemMapper {
    fun mapEntityToDbModel(shopItem: ShopItem) =
        ShopItemDbModel(
            id = shopItem.id,
            name = shopItem.name,
            enabled = shopItem.enabled,
            count = shopItem.count
        )


    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) =
        ShopItem(
            id = shopItemDbModel.id,
            name = shopItemDbModel.name,
            enabled = shopItemDbModel.enabled,
            count = shopItemDbModel.count
        )

    fun maoListDbModelToListEntity(list: List<ShopItemDbModel>) =
        list.map { mapDbModelToEntity(it) }

}