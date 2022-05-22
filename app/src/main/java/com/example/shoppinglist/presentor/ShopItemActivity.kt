package com.example.shoppinglist.presentor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private var screenMode = UNKNOWN_MODE
    private lateinit var viewModel: ShipItemViewModel
    private var shopItemId = ShopItem.ID_DEFAULT
    private lateinit var binding: ActivityShopItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parsIntent()
        viewModel = ViewModelProvider(this)[ShipItemViewModel::class.java]
        selectScreenMode()
        setTextChangeListener()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.errorCount.observe(this) {
            if (it) {
                binding.iLCount.error = "Error"
            }
        }
        viewModel.errorName.observe(this) {
            if (it) {
                binding.iLName.error = "Error"
            }
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun setTextChangeListener() {
        binding.eTName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iLName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.eTCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.iLCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun selectScreenMode() {
        when (screenMode) {
            ADD_MODE -> launchAddMode()
            EDIT_MODE -> launchEditMode()
            else -> throw RuntimeException("Screen mode unknown")
        }
    }

    private fun launchEditMode() = with(binding) {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this@ShopItemActivity) {
            eTName.setText(it.name)
            eTCount.setText(it.count.toString())
        }
        saveItem.setOnClickListener {
            val name = eTName.text.toString()
            val count = eTCount.text.toString()
            viewModel.editShopItem(name, count)
        }
    }

    private fun launchAddMode() = with(binding) {
        saveItem.setOnClickListener {
            val name = eTName.text.toString()
            val count = eTCount.text.toString()
            viewModel.addShopItem(name, count)
        }

    }

    private fun parsIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Screen mode unknown")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("ShopItemId is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.ID_DEFAULT)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
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
}