package com.example.shoppinglist.presentor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnFinishEditingListener {
    private var shopItemId = ShopItem.DEFAULT_ID
    private var screenMode = UNKNOWN_MODE

    lateinit var binding: ActivityShopItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parsIntent()
        selectScreenMode()
    }

    private fun selectScreenMode() {
        val fragment = when (screenMode) {
            ADD_MODE -> ShopItemFragment.getAddInstance()
            EDIT_MODE -> ShopItemFragment.getEditInstance(shopItemId)
            else -> throw RuntimeException("Unknown screen mode: $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun parsIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent!")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Item id is absent!")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.DEFAULT_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "screen_mode"
        private const val EXTRA_SHOP_ITEM_ID = "shop_item_id"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNKNOWN_MODE = "unknown_mode"

        fun getAddIntent(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun getEditIntent(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }


    }

    override fun finishEditing() {
        finish()
    }
}