package com.example.shoppinglist.prsentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopItemAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopItemAdapter.shopList = it
        }
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        shopItemAdapter = ShopListAdapter()
        rvShopList.adapter = shopItemAdapter
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.LAYOUT_ENABLED,
            ShopListAdapter.MAX_RECYCLER_ITEM
        )
        rvShopList.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.LAYOUT_DISABLED,
            ShopListAdapter.MAX_RECYCLER_ITEM
        )

        shopItemAdapter.onShopItemOnLongClick = {
            viewModel.changeEnableState(it)
        }

    }
}