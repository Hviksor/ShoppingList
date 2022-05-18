package com.example.shoppinglist.prsentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.core.text.set
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var buttonSave: Button
    lateinit var viewModel: ShopItemViewModel
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.DEFAULT_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        parseIntent()
        initViews()
        tilName = binding.tillName
        tilCount = binding.tillCount
        etName = binding.edName
        etCount = binding.edCount
        buttonSave = binding.button
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode(0)
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        etName.setText(viewModel.shopItem.value?.name)
        etCount.setText(viewModel.shopItem.value?.count.toString())
        launchAddMode(1)

        val shopItem = viewModel.shopItem.observe(this) {


        }
    }

    private fun launchAddMode( edit:Int?) {
        etName.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    tilName.error = null
                    viewModel.resetErrorInputName()
                }

            })
        etCount.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    tilCount.error = null
                    viewModel.resetErrorInputCount()
                }
            })


        buttonSave.setOnClickListener {
            val name = etName.text.toString()
            val count = etCount.text.toString()
            if (edit == 0) {
                viewModel.addShopItem(name, count)
            } else {
                viewModel.editShopItem(name, count)
            }

            if (viewModel.errorInputName.value == true) {
                tilName.error = "Error"
            } else if (viewModel.errorInputCount.value == true) {
                tilCount.error = "Error"
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param itemId mode is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }

    }

    private fun initViews() {

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }

    }
}