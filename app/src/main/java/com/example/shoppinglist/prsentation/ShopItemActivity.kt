package com.example.shoppinglist.prsentation

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
        viewModel.errorInputName.observe(this) {
            TODO()
        }
        viewModel.errorInputCount.observe(this){
            TODO()
        }
    }
}