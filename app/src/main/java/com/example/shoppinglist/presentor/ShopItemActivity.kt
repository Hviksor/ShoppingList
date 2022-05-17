package com.example.shoppinglist.presentor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R

class ShopItemActivity : AppCompatActivity() {
    lateinit var viewModel: ShopItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        viewModel.errorInputCount.observe(this) {

        }
        viewModel.errorInputCount.observe(this) {

        }
    }
}