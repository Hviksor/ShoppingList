package com.example.shoppinglist.presentor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel
    private var screenMode = UNKNOWN_MODE
    private var shopItemId = ShopItem.DEFAULT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        selectScreenMode()
        addChangeTextListener()
        addViewModelFields()



    }

    private fun addViewModelFields() = with(binding) {
        viewModel.errorInputCount.observe(this@ShopItemActivity) {
            if (it) {
                iLCount.error = "Error"
            }
        }
        viewModel.errorInputName.observe(this@ShopItemActivity) {
            if (it) {
                iLName.error = "Error"
            }
        }
        viewModel.shouldCloseScreen.observe(this@ShopItemActivity) {
            finish()
        }

    }

    private fun addChangeTextListener() = with(binding) {

        eTName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                iLName.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        eTCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                iLCount.error = null
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun selectScreenMode() {
        when (screenMode) {
            ADD_MODE -> launchModeAdd()
            EDIT_MODE -> launchModeEdit()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
    }

    private fun launchModeEdit() = with(binding) {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this@ShopItemActivity) {
            eTName.setText(it.name)
            eTCount.setText(it.count.toString())
        }
        saveButton.setOnClickListener {
            viewModel.editShopItem(eTName.text.toString(), eTCount.text.toString())
        }
    }

    private fun launchModeAdd() = with(binding) {
        saveButton.setOnClickListener {
            viewModel.addShopItem(eTName.text.toString(), eTCount.text.toString())
        }

    }


    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != ADD_MODE && mode != EDIT_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (mode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("SHOP_ITEM_ID mode is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopItem.DEFAULT_ID)
        }

    }


    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
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